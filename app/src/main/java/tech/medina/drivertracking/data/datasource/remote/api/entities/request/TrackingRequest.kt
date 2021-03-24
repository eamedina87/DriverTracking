package tech.medina.drivertracking.data.datasource.remote.api.entities.request

import com.google.gson.annotations.SerializedName

data class TrackingRequest(
    @SerializedName("driver_id")
    val driverId: Long,
    @SerializedName("tracking_data")
    val list: List<TrackingData>
)

data class TrackingData(
        @SerializedName("latitude")
        val latitude: Double,
        @SerializedName("longitude")
        val longitude: Double,
        @SerializedName("delivery_id")
        val deliveryId: Long,
        @SerializedName("battery_level")
        val batteryLevel: Int,
        @SerializedName("timestamp")
        val timestamp: Long
)