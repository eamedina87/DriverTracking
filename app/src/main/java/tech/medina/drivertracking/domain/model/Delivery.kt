package tech.medina.drivertracking.domain.model

data class Delivery(
    val id: Long,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val customerName: String,
    val status: STATUS,
    val fetchTimestamp: Long,
    val specialInstructions: String,
    val requiresSignature: Boolean
) {

    companion object { }

}

enum class STATUS {
    DEFAULT, ACTIVE, COMPLETED
}

fun Int.toDeliveryStatus(): STATUS =
    when (this) {
        STATUS.ACTIVE.ordinal -> STATUS.ACTIVE
        STATUS.COMPLETED.ordinal -> STATUS.COMPLETED
        else -> STATUS.DEFAULT
    }
