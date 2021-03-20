package tech.medina.drivertracking.data.mapper

import tech.medina.drivertracking.data.datasource.local.db.entities.DeliveryLocal
import tech.medina.drivertracking.data.datasource.local.db.entities.TrackingLocal
import tech.medina.drivertracking.data.datasource.location.entity.Location
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.TrackingRemote
import tech.medina.drivertracking.domain.model.Delivery
import tech.medina.drivertracking.domain.model.Tracking

interface Mapper {

    //Remote to Local
    fun toLocal(entity: DeliveryRemote.Delivery, timestamp: Long): DeliveryLocal
    //Local to Remote
    fun toRemote(entity: TrackingLocal): TrackingRemote.TrackingData
    fun toRemote(list: List<TrackingLocal>, driverId: Long): TrackingRemote
    //Local to Model
    fun toModel(entity: DeliveryLocal): Delivery
    fun toModel(entity: TrackingLocal): Tracking
    //Model to Local
    fun toLocal(model: Delivery): DeliveryLocal
    fun toLocal(model: Tracking): TrackingLocal

    //Android Location to Location
    fun toLocation(frameworkLocation: android.location.Location): Location
}