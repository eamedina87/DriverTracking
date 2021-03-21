package tech.medina.drivertracking.ui.delivery.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import tech.medina.drivertracking.databinding.FragmentItemDetailBinding
import tech.medina.drivertracking.domain.model.DataState
import tech.medina.drivertracking.domain.model.Delivery
import tech.medina.drivertracking.ui.base.BaseFragment
import tech.medina.drivertracking.ui.delivery.DeliveryViewModel
import tech.medina.drivertracking.ui.utils.Constants
import tech.medina.drivertracking.ui.utils.getExtra

class DeliveryDetailFragment : BaseFragment() {

    companion object {

        fun createWithExtras(extras: Bundle) : DeliveryDetailFragment {
            return DeliveryDetailFragment().apply {
                arguments = extras
            }
        }

    }

    private lateinit var binding: FragmentItemDetailBinding
    override val viewModel: DeliveryViewModel by viewModels()

    override fun getBindingRoot(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        initObservers()
        arguments.getExtra<Long>(Constants.INTENT_EXTRA_DELIVERY_ID) {
            viewModel.getDeliveryDetailWithId(it)
        }
    }

    private fun initObservers() {
        viewModel.deliveryDetailState.observe(this) {
            it?.let { state ->
                when (state) {
                    is DataState.Loading -> showLoader()
                    is DataState.Success -> {
                        hideLoader()
                        onDeliveryDetailSuccess(state.result)
                    }
                    is DataState.Error -> {
                        hideLoader()
                        onError(state.error)
                    }
                }
            }
        }
    }

    private fun onDeliveryDetailSuccess(delivery: Delivery) {
        showMessage("Delivery Detail Success")
    }

}