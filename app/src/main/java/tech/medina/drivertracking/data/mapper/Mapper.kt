package tech.medina.drivertracking.data.mapper

import tech.medina.drivertracking.data.datasource.local.db.entities.DeliveryLocal
import tech.medina.drivertracking.data.datasource.local.db.entities.TrackingLocal
import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.TrackingRemote
import tech.medina.drivertracking.domain.model.Delivery
import tech.medina.drivertracking.domain.model.Tracking

interface Mapper {

    //Remote to Local
    fun toLocal(entity: DeliveryRemote.Delivery, timestamp: Long): DeliveryLocal
    //Local to Remote
    fun toRemote(entity: TrackingLocal, driverId: Long): TrackingRemote.TrackingData
    //Local to Model
    fun toModel(entity: DeliveryLocal): Delivery
    //Model to Local
    fun toLocal(model: Delivery): DeliveryLocal
    fun toLocal(model: Tracking): TrackingLocal

}