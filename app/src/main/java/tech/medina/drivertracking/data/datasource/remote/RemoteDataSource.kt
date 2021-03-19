package tech.medina.drivertracking.data.datasource.remote

import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.TrackingRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.response.TrackingResponse

interface RemoteDataSource {

    suspend fun getDeliveryList(): DeliveryRemote
    suspend fun getDeliveryDetailForId(id: String): DeliveryRemote.Delivery
    suspend fun postTracking(tracking: TrackingRemote): TrackingResponse

}