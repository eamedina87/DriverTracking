package tech.medina.drivertracking.ui.delivery

import android.content.Context
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.medina.drivertracking.R
import tech.medina.drivertracking.domain.model.DataState
import tech.medina.drivertracking.domain.model.Delivery
import tech.medina.drivertracking.domain.model.DeliveryStatus
import tech.medina.drivertracking.domain.usecase.*
import tech.medina.drivertracking.domain.tracking.TrackingManager
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val state: SavedStateHandle,
    @ApplicationContext private val context: Context,
    private val dispatcher: CoroutineDispatcher,
    private val trackingManager: TrackingManager,
    private val getDeliveriesUseCase: GetDeliveriesUseCase,
    private val getDeliveryDetailUseCase: GetDeliveryDetailUseCase,
    private val getActiveDeliveryUseCase: GetActiveDeliveryUseCase,
    private val setActiveDeliveryUseCase: SetActiveDeliveryUseCase,
    private val setCompleteDeliveryUseCase: SetCompleteDeliveryUseCase
) : ViewModel() {

    private val _deliveryListState: MutableLiveData<DataState<List<Delivery>>> = MutableLiveData()
    val deliveryListState: LiveData<DataState<List<Delivery>>> get() = _deliveryListState

    private val _deliveryDetailState: MutableLiveData<DataState<Delivery>> = MutableLiveData()
    val deliveryDetailState: LiveData<DataState<Delivery>> get() = _deliveryDetailState

    private val _deliveryActionState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val deliveryActionState: LiveData<DataState<Boolean>> get() = _deliveryActionState


    fun getDeliveryList(forceUpdate: Boolean = false) {
        viewModelScope.launch {
            _deliveryListState.value = DataState.Loading
            _deliveryListState.value = try {
                withContext(dispatcher) {
                    DataState.Success(getDeliveriesUseCase(forceUpdate = forceUpdate))
                }
            } catch(e: Exception) {
                DataState.Error(e)
            }
        }
    }

    fun getDeliveryDetailWithId(id: Long) {
      viewModelScope.launch {
          _deliveryDetailState.value = DataState.Loading
          _deliveryDetailState.value = try {
              withContext(dispatcher) {
                  DataState.Success(getDeliveryDetailUseCase(id))
              }
          } catch (e: Exception) {
              DataState.Error(e)
          }
      }
    }

    fun performActionOnDeliveryDetail(canCancelActiveDelivery: Boolean = false) {
        viewModelScope.launch {
            _deliveryActionState.value = DataState.Loading
            val detailState = _deliveryDetailState.value
            if (detailState !is DataState.Success) {
                _deliveryActionState.value = DataState.Error(context.getString(R.string.delivery_action_error_incorrect_state))
            }
            detailState as DataState.Success
            val delivery = detailState.result
            val currentActiveDelivery = getActiveDeliveryUseCase()
            //If this delivery can be activated, first we must check if there is another one active
            //If that's the case we prompt the user to finish it
            if (delivery.status == DeliveryStatus.DEFAULT &&
                currentActiveDelivery != null &&
                !canCancelActiveDelivery) {
                _deliveryActionState.value = DataState.Error(currentActiveDelivery)
                return@launch
            }
            when {
                //There is no active tracking, we start the service for this delivery
                delivery.status == DeliveryStatus.DEFAULT && !canCancelActiveDelivery -> {
                    setActiveDeliveryUseCase(delivery)
                    trackingManager.start()
                    _deliveryActionState.value = DataState.Success(true)
                }
                //If there is an active tracking, we must set this an active
                //and update the service keeping it alive
                delivery.status == DeliveryStatus.DEFAULT && canCancelActiveDelivery -> {
                    setActiveDeliveryUseCase(delivery)
                    trackingManager.update()
                    _deliveryActionState.value = DataState.Success(true)
                }
                //This is the active tracking, we must stop the service
                delivery.status == DeliveryStatus.ACTIVE && !canCancelActiveDelivery -> {
                    setCompleteDeliveryUseCase(delivery)
                    trackingManager.stop()
                    _deliveryActionState.value = DataState.Success(true)
                }
                delivery.status == DeliveryStatus.COMPLETED ->
                    _deliveryActionState.value = DataState.Error(context.getString(R.string.delivery_action_error_completed))
            }
        }
    }

}