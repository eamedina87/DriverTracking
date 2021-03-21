package tech.medina.drivertracking.data.datasource.remote.api.entities

import com.google.gson.annotations.SerializedName

data class DeliveriesRemote(
    val deliveries: Array<Delivery>
) {

    //This declaration is necessary to provide mocks of this class by extensions in the tests
    companion object {  }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DeliveriesRemote

        if (!deliveries.contentEquals(other.deliveries)) return false

        return true
    }

    override fun hashCode(): Int {
        return deliveries.contentHashCode()
    }
}

data class DeliveryRemote(
    val delivery: Array<Delivery>
) {

    //This declaration is necessary to provide mocks of this class by extensions in the tests
    companion object {  }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DeliveryRemote

        if (!delivery.contentEquals(other.delivery)) return false

        return true
    }

    override fun hashCode(): Int {
        return delivery.contentHashCode()
    }
}

data class Delivery(
    val id: Long,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    @SerializedName("customer_name")
    val customerName: String,
    val requiresSignature: Boolean?,
    val specialInstructions: String?
)