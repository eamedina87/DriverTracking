package tech.medina.drivertracking.data.datasource.remote

import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveriesRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.Delivery
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.TrackingRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.response.TrackingResponse

interface RemoteDataSource {

    suspend fun getDeliveryList(): DeliveriesRemote
    suspend fun getDeliveryDetailForId(id: Long): Delivery
    suspend fun postTracking(tracking: TrackingRemote): TrackingResponse

}