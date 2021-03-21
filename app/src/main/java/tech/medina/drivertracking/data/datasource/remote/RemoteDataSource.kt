package tech.medina.drivertracking.data.datasource.remote

import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveriesResponse
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.request.TrackingRequest
import tech.medina.drivertracking.data.datasource.remote.api.entities.response.TrackingResponse

interface RemoteDataSource {

    suspend fun getDeliveryList(): DeliveriesResponse
    suspend fun getDeliveryDetailForId(id: Long): DeliveryRemote
    suspend fun postTracking(tracking: TrackingRequest): TrackingResponse

}