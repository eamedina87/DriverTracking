package tech.medina.drivertracking.ui.delivery.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.medina.drivertracking.databinding.ItemDeliveryBinding
import tech.medina.drivertracking.domain.model.Delivery

class DeliveryAdapter(
    private val listener: (Delivery) -> Unit
) : ListAdapter<Delivery, DeliveryAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemDeliveryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position)) {
            listener.invoke(it)
        }
    }

    class ItemViewHolder(
        private val binding: ItemDeliveryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Delivery, clickListener: (Delivery) -> Unit) = with(binding) {
            customerName.text = item.customerName
            address.text = item.address
            itemView.setOnClickListener {
                clickListener.invoke(item)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Delivery>() {
        override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
            return oldItem == newItem
        }
    }
}