package tech.medina.drivertracking.data.datasource.remote

import tech.medina.drivertracking.data.datasource.remote.api.DeliveryService
import tech.medina.drivertracking.data.datasource.remote.api.TrackingService
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveriesResponse
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.request.TrackingRequest
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val deliveryService: DeliveryService,
    private val trackingService: TrackingService
    ): RemoteDataSource {

    override suspend fun getDeliveryList(): DeliveriesResponse =
        deliveryService.getDeliveryList()

    override suspend fun getDeliveryDetailForId(id: Long): DeliveryRemote =
        deliveryService.getDeliveryDetailForId(id).delivery.first()

    override suspend fun postTracking(tracking: TrackingRequest) =
        trackingService.postTracking(tracking)

}