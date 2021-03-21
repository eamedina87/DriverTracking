package tech.medina.drivertracking.data.datasource.remote

import tech.medina.drivertracking.data.datasource.remote.api.DeliveryService
import tech.medina.drivertracking.data.datasource.remote.api.TrackingService
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveriesRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.Delivery
import tech.medina.drivertracking.data.datasource.remote.api.entities.TrackingRemote
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val deliveryService: DeliveryService,
    private val trackingService: TrackingService
    ): RemoteDataSource {

    override suspend fun getDeliveryList(): DeliveriesRemote =
        deliveryService.getDeliveryList()

    override suspend fun getDeliveryDetailForId(id: Long): Delivery =
        deliveryService.getDeliveryDetailForId(id).delivery.first()

    override suspend fun postTracking(tracking: TrackingRemote) =
        trackingService.postTracking(tracking)

}