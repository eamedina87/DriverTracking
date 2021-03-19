package tech.medina.drivertracking.ui.delivery.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tech.medina.drivertracking.R

class DeliveryDetailFragment : Fragment() {

    companion object {

        fun createWithExtras(extras: Bundle) : DeliveryDetailFragment {
            return DeliveryDetailFragment().apply {
                arguments = extras
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)
        return rootView
    }

}