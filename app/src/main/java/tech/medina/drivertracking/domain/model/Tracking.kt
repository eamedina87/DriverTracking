package tech.medina.drivertracking.domain.model

data class Tracking(
    val latitude: Double,
    val longitude: Double,
    val deliveryId: Long,
    val batteryLevel: Int,
    val timestamp: Long,
    var status: TrackingStatus
) {

    companion object { }

}

enum class TrackingStatus {
    DEFAULT, SENT
}

fun Int.toTrackingStatus(): TrackingStatus =
    when (this) {
        TrackingStatus.SENT.ordinal -> TrackingStatus.SENT
        else -> TrackingStatus.DEFAULT
    }
