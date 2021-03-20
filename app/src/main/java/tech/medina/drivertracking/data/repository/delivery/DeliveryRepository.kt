package tech.medina.drivertracking.data.repository.delivery

import tech.medina.drivertracking.domain.model.Delivery

interface DeliveryRepository {

    fun getDeliveryList(forceUpdate: Boolean = false): List<Delivery>
    fun getDeliveryDetailForId(id: Long): Delivery
    fun activateDelivery(delivery: Delivery): Boolean
    fun deactivateDelivery(delivery: Delivery): Boolean
    fun deactivateAllDeliveries(): Boolean

}