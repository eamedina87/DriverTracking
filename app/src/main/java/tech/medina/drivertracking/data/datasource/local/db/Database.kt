package tech.medina.drivertracking.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import tech.medina.drivertracking.data.datasource.local.db.dao.DeliveryDao
import tech.medina.drivertracking.data.datasource.local.db.dao.TrackingDao
import tech.medina.drivertracking.data.datasource.local.db.entities.DeliveryLocal
import tech.medina.drivertracking.data.datasource.local.db.entities.TrackingLocal

@Database(entities = [DeliveryLocal::class, TrackingLocal::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun deliveryDao(): DeliveryDao
    abstract fun trackingDao(): TrackingDao

}