package tech.medina.drivertracking.domain.model

data class Delivery(
    val id: Long,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val customerName: String,
    val status: DeliveryStatus,
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
