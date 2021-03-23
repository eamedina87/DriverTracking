package tech.medina.drivertracking.ui.delivery.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import tech.medina.drivertracking.R
import tech.medina.drivertracking.databinding.ActivityItemDetailBinding
import tech.medina.drivertracking.domain.model.Delivery
import tech.medina.drivertracking.ui.base.BaseActivity
import tech.medina.drivertracking.ui.delivery.list.DeliveryListActivity

class DeliveryDetailActivity : BaseActivity() {

    override val viewModel: ViewModel? = null
    private lateinit var binding: ActivityItemDetailBinding

    override fun getBindingRoot(): View {
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            addFragment(
                R.id.item_detail_container,
                DeliveryDetailFragment.createWithExtras(intent.extras ?: bundleOf()) {
                    setTitleForDelivery(it)
                },
                "delivery.detail"
            )
        }
    }

    private fun setTitleForDelivery(delivery: Delivery) {
        binding.toolbarLayout.title = delivery.customerName
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, DeliveryListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}