package tech.medina.drivertracking.ui.delivery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.medina.drivertracking.domain.model.DataState
import tech.medina.drivertracking.domain.model.Delivery
import tech.medina.drivertracking.domain.usecase.GetDeliveriesUseCase
import tech.medina.drivertracking.domain.usecase.GetDeliveryDetailUseCase
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val getDeliveriesUseCase: GetDeliveriesUseCase,
    private val getDeliveryDetailUseCase: GetDeliveryDetailUseCase
) : ViewModel() {

    private val _deliveryListState: MutableLiveData<DataState<List<Delivery>>> = MutableLiveData()
    val deliveryListState: LiveData<DataState<List<Delivery>>> get() = _deliveryListState

    private val _deliveryDetailState: MutableLiveData<DataState<Delivery>> = MutableLiveData()
    val deliveryDetailState: LiveData<DataState<Delivery>> get() = _deliveryDetailState

    fun getDeliveryList() = viewModelScope.launch {
        _deliveryListState.value = try {
            withContext(dispatcher) {
                DataState.Success(getDeliveriesUseCase())
            }
        } catch(e: Exception) {
            DataState.Error(e)
        }
    }

    fun getDeliveryDetailWithId(id: Long) = viewModelScope.launch {
        _deliveryDetailState.value = try {
            withContext(dispatcher) {
                DataState.Success(getDeliveryDetailUseCase(id))
            }
        } catch(e: Exception) {
            DataState.Error(e)
        }
    }

}