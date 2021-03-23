package tech.medina.drivertracking.data.repository.delivery

import tech.medina.drivertracking.domain.model.Delivery

interface DeliveryRepository {

    suspend fun getDeliveryList(forceUpdate: Boolean = false): List<Delivery>
    suspend fun getDeliveryDetailForId(id: Long): Delivery
    suspend fun getActiveDelivery(): List<Delivery>
    suspend fun updateDelivery(vararg delivery: Delivery): Boolean

}