package tech.medina.drivertracking.domain.model

data class Delivery(
    val id: Long,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val customerName: String,
    val status: Status,
    val fetchTimestamp: Long,
    val specialInstructions: String,
    val requiresSignature: Boolean
) {

    companion object { }

    enum class Status {
        DEFAULT, ACTIVE, COMPLETED
    }

}