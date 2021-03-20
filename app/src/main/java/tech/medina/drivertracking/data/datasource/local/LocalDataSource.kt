package tech.medina.drivertracking.data.datasource.local

import tech.medina.drivertracking.data.datasource.local.db.entities.DeliveryLocal
import tech.medina.drivertracking.data.datasource.local.db.entities.TrackingLocal

interface LocalDataSource {

    suspend fun saveDelivery(vararg delivery: DeliveryLocal): Boolean
    suspend fun updateDelivery(vararg delivery: DeliveryLocal): Boolean
    suspend fun getDeliveryList(): List<DeliveryLocal>
    suspend fun getDeliveryWithId(id: Long): DeliveryLocal

    suspend fun saveTracking(vararg data: TrackingLocal): Boolean
    suspend fun updateTracking(vararg data: TrackingLocal): Boolean
    suspend fun deleteTracking(vararg data: TrackingLocal): Boolean
    suspend fun getAllTracking(): List<TrackingLocal>
    suspend fun getAllTrackingWithStatusNot(status: Int): List<TrackingLocal>
    fun getDriverId(): Long

}