package tech.medina.drivertracking.ui.delivery.list

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import tech.medina.drivertracking.R
import tech.medina.drivertracking.databinding.ActivityItemListBinding
import tech.medina.drivertracking.domain.model.DataState
import tech.medina.drivertracking.domain.model.Delivery
import tech.medina.drivertracking.ui.base.BaseActivity
import tech.medina.drivertracking.ui.delivery.DeliveryViewModel
import tech.medina.drivertracking.ui.delivery.list.adapter.DeliveryAdapter


class DeliveryListActivity : BaseActivity() {

    private var isRequestingDeliveries: Boolean = false
    override val viewModel: DeliveryViewModel by viewModels()
    private lateinit var binding: ActivityItemListBinding

    override fun getBindingRoot(): View {
        binding = ActivityItemListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        initObservers()
        viewModel.getDeliveryList(forceUpdate = true)
        isRequestingDeliveries = true
    }

    override fun onResume() {
        super.onResume()
        if (!isRequestingDeliveries) viewModel.getDeliveryList()
    }

    private fun initObservers() {
        viewModel.deliveryListState.observe(this) {
            it?.let { state ->
                isRequestingDeliveries = false
                when (state) {
                    is DataState.Loading -> showLoader()
                    is DataState.Success -> {
                        hideLoader()
                        onDeliveryListSuccess(state.result)
                    }
                    is DataState.Error -> {
                        hideLoader()
                        onError(state.error)
                    }
                }
            }
        }
    }

    private fun onDeliveryListSuccess(data: List<Delivery>) {
        if (data.isEmpty()) {
            onDeliveryListEmpty()
        } else {
            populateDeliveryList(data)
        }
    }

    private fun onDeliveryListEmpty() {
        binding.list.itemList.visibility = View.GONE
        binding.textMessage.apply {
            visibility = View.VISIBLE
            text = getString(R.string.deliveries_empty)
        }
    }

    private fun populateDeliveryList(data: List<Delivery>) {
        binding.textMessage.visibility = View.GONE
        binding.list.itemList.adapter = DeliveryAdapter {
            onDeliverySelected(it)
        }.apply {
            submitList(data)
        }
    }

    private fun onDeliverySelected(delivery: Delivery) {
        navigator.goToDetail(this, delivery.id, R.id.item_detail_container)
    }

    private fun showLoader() {
        binding.progress.container.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        binding.progress.container.visibility = View.GONE
    }

}