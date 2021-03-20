package tech.medina.drivertracking.domain.model

data class Tracking(
    val latitude: Double,
    val longitude: Double,
    val deliveryId: Long,
    val batteryLevel: Int,
    val timestamp: Long,
    val status: TrackingStatus
) {

    companion object { }

}

enum class TrackingStatus {
    DEFAULT, SAVED, SENT
}

fun Int.toTrackingStatus(): TrackingStatus =
    when (this) {
        TrackingStatus.SAVED.ordinal -> TrackingStatus.SAVED
        TrackingStatus.SENT.ordinal -> TrackingStatus.SENT
        else -> TrackingStatus.DEFAULT
    }
