package tech.medina.drivertracking.domain.model

import android.content.Context
import tech.medina.drivertracking.R

data class Delivery(
    val id: Long,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val customerName: String,
    var status: DeliveryStatus,
    val fetchTimestamp: Long,
    val specialInstructions: String,
    val requiresSignature: Boolean
) {

    companion object { }

}

enum class DeliveryStatus {
    DEFAULT, ACTIVE, COMPLETED
}

fun Int.toDeliveryStatus(): DeliveryStatus =
    when (this) {
        DeliveryStatus.ACTIVE.ordinal -> DeliveryStatus.ACTIVE
        DeliveryStatus.COMPLETED.ordinal -> DeliveryStatus.COMPLETED
        else -> DeliveryStatus.DEFAULT
    }

fun DeliveryStatus.toButtonString(context: Context): String =
    when (this) {
        DeliveryStatus.DEFAULT -> context.getString(R.string.delivery_button_activate)
        DeliveryStatus.ACTIVE -> context.getString(R.string.delivery_button_finish)
        DeliveryStatus.COMPLETED -> context.getString(R.string.delivery_button_complete)
    }

fun DeliveryStatus.toButtonEnable(): Boolean =
    when (this) {
        DeliveryStatus.DEFAULT,
        DeliveryStatus.ACTIVE -> true
        DeliveryStatus.COMPLETED -> false
    }