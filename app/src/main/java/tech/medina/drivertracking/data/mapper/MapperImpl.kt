package tech.medina.drivertracking.data.mapper

import tech.medina.drivertracking.data.datasource.local.db.entities.DeliveryLocal
import tech.medina.drivertracking.data.datasource.local.db.entities.TrackingLocal
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.TrackingRemote
import tech.medina.drivertracking.domain.model.*
import javax.inject.Inject

class MapperImpl @Inject constructor(): Mapper {

    override fun toLocal(entity: DeliveryRemote.Delivery, timestamp: Long): DeliveryLocal =
        DeliveryLocal(
            id = -1,
            address = entity.address,
            latitude = entity.latitude,
            longitude = entity.longitude,
            customerName = entity.customerName,
            status = DeliveryStatus.DEFAULT.ordinal,
            fetchTimestamp = timestamp,
            requiresSignature = entity.requiresSignature ?: false,
            specialInstructions = entity.specialInstructions ?: ""
        )

    override fun toRemote(entity: TrackingLocal): TrackingRemote.TrackingData =
        TrackingRemote.TrackingData(
            latitude = entity.latitude,
            longitude = entity.longitude,
            deliveryId = entity.deliveryId,
            batteryLevel = entity.batteryLevel,
            timestamp = entity.timestamp
        )

    override fun toRemote(list: List<TrackingLocal>, driverId: Long): TrackingRemote {
        val data = list.map {
            toRemote(it)
        }
        return TrackingRemote(driverId, data)
    }

    override fun toModel(entity: DeliveryLocal): Delivery =
        Delivery(
            id = entity.id,
            address = entity.address,
            latitude = entity.latitude,
            longitude = entity.longitude,
            customerName = entity.customerName,
            status = entity.status.toDeliveryStatus(),
            fetchTimestamp = entity.fetchTimestamp,
            specialInstructions = entity.specialInstructions ?: "",
            requiresSignature = entity.requiresSignature ?: false
        )

    override fun toLocal(model: Delivery): DeliveryLocal =
        DeliveryLocal(
            id = model.id,
            address = model.address,
            latitude = model.latitude,
            longitude = model.longitude,
            customerName = model.customerName,
            status = model.status.ordinal,
            fetchTimestamp = model.fetchTimestamp,
            specialInstructions = model.specialInstructions,
            requiresSignature = model.requiresSignature
        )

    override fun toLocal(model: Tracking): TrackingLocal =
        TrackingLocal(
            id = -1,
            latitude = model.latitude,
            longitude = model.longitude,
            deliveryId = model.deliveryId,
            batteryLevel = model.batteryLevel,
            timestamp = model.timestamp,
            status = model.status.ordinal,
            syncTimestamp = null
        )

    override fun toModel(entity: TrackingLocal): Tracking =
        Tracking(
            latitude = entity.latitude,
            longitude = entity.longitude,
            deliveryId = entity.deliveryId,
            batteryLevel = entity.batteryLevel,
            timestamp = entity.timestamp,
            status = entity.status.toTrackingStatus()
        )

}