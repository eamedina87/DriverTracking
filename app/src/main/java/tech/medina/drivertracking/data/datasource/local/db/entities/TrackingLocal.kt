package tech.medina.drivertracking.data.datasource.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracking")
data class TrackingLocal(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val latitude: Double,
    val longitude: Double,
    val deliveryId: Long,
    val batteryLevel: Int,
    val timestamp: Long,
    val status: Int,
    val syncTimestamp: Long?
) {

    //This declaration is necessary to provide mocks of this class by extensions in the tests
    companion object { }

}