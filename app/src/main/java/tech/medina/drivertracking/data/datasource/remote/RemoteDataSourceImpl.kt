package tech.medina.drivertracking.data.datasource.remote

import tech.medina.drivertracking.data.datasource.remote.api.DeliveryService
import tech.medina.drivertracking.data.datasource.remote.api.TrackingService
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.TrackingRemote

class RemoteDataSourceImpl(
    private val deliveryService: DeliveryService,
    private val trackingService: TrackingService
    ): RemoteDataSource {

    override suspend fun getDeliveryList(): DeliveryRemote =
        deliveryService.getDeliveryList()

    override suspend fun getDeliveryDetailForId(id: String): DeliveryRemote.Delivery =
        deliveryService.getDeliveryDetailForId(id).delivery.first()

    override suspend fun postTracking(tracking: TrackingRemote) =
        trackingService.postTracking(tracking)

}