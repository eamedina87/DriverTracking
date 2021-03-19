package tech.medina.drivertracking.data.utils

import tech.medina.drivertracking.data.datasource.remote.api.entities.DeliveryRemote
import tech.medina.drivertracking.data.datasource.remote.api.entities.TrackingRemote

fun DeliveryRemote.Companion.mock(isFull: Boolean = false): DeliveryRemote =
    DeliveryRemote(arrayOf(
        DeliveryRemote.Delivery(
            id = 123,
            address = "Mocked Address",
            latitude = 42.0,
            longitude = 2.0,
            customerName = "Mocked Customer Name",
            requiresSignature = if (isFull) true else null,
            specialInstructions = if (isFull) "Mocked Special Instructions" else null
        )
    ))

fun TrackingRemote.Companion.mock(): TrackingRemote =
    TrackingRemote(
        driverId = 123,
        list = listOf(
            TrackingRemote.TrackingData(42.0, 2.0, 123, 80, 123456)
        ))