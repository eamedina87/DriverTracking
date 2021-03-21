package tech.medina.drivertracking.data.datasource.remote.api.entities

import com.google.gson.annotations.SerializedName

data class DeliveryRemote(
    val deliveries: Array<Delivery>
) {

    //This declaration is necessary to provide mocks of this class by extensions in the tests
    companion object {  }

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DeliveryRemote

        if (!deliveries.contentEquals(other.deliveries)) return false

        return true
    }

    override fun hashCode(): Int {
        return deliveries.contentHashCode()
    }
}