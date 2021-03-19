package tech.medina.drivertracking.data.datasource.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import tech.medina.drivertracking.data.datasource.local.db.entities.TrackingLocal

@Dao
interface TrackingDao {

    @Insert
    suspend fun insert(vararg data: TrackingLocal): List<Long>

    @Update
    suspend fun update(vararg data: TrackingLocal): Int

    @Delete
    suspend fun delete(vararg data: TrackingLocal): Int

}