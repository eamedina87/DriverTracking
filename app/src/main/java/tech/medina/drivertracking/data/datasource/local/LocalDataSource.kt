package tech.medina.drivertracking.data.datasource.local

import tech.medina.drivertracking.data.datasource.local.db.entities.DeliveryLocal
import tech.medina.drivertracking.data.datasource.local.db.entities.TrackingLocal

interface LocalDataSource {

    suspend fun saveDeliveryList(vararg delivery: DeliveryLocal): Boolean
    suspend fun updateDelivery(vararg delivery: DeliveryLocal): Boolean
    suspend fun getDeliveryList(): List<DeliveryLocal>
    suspend fun getDeliveryWithId(id: Long): DeliveryLocal

    suspend fun saveTracking(vararg data: TrackingLocal): Boolean
    suspend fun updateTracking(vararg data: TrackingLocal): Boolean
    suspend fun deleteTracking(vararg data: TrackingLocal): Boolean

}