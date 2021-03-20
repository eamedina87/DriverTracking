package tech.medina.drivertracking.data.datasource.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "delivery")
data class DeliveryLocal(
    @PrimaryKey val id: Long,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val customerName: String,
    val status: Int,
    val fetchTimestamp: Long,
    val requiresSignature: Boolean?,
    val specialInstructions: String?
) {
    fun isComplete(): Boolean {
        return requiresSignature != null && specialInstructions != null
    }

    //This declaration is necessary to provide mocks of this class by extensions in the tests
    companion object { }

}