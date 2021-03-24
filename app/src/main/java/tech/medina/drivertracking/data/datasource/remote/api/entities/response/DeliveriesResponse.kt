package tech.medina.drivertracking.data.datasource.remote.api.entities

import com.google.gson.annotations.SerializedName

data class DeliveriesResponse(
    val deliveries: Array<DeliveryRemote>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DeliveriesResponse

        if (!deliveries.contentEquals(other.deliveries)) return false

        return true
    }

    override fun hashCode(): Int {
        return deliveries.contentHashCode()
    }
}

data class DeliveryResponse(
    val delivery: Array<DeliveryRemote>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DeliveryResponse

        if (!delivery.contentEquals(other.delivery)) return false

        return true
    }

    override fun hashCode(): Int {
        return delivery.contentHashCode()
    }
}

data class DeliveryRemote(
    val id: Long,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    @SerializedName("customer_name")
    val customerName: String,
    @SerializedName("requires_signature")
    val requiresSignature: Boolean?,
    @SerializedName("special_instructions")
    val specialInstructions: String?
)