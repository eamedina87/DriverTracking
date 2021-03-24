package tech.medina.drivertracking.data.datasource.local.db.dao

import androidx.room.*
import tech.medina.drivertracking.data.datasource.local.db.entities.TrackingLocal

@Dao
interface TrackingDao {

    @Insert
    suspend fun insert(vararg data: TrackingLocal): List<Long>

    @Update
    suspend fun update(vararg data: TrackingLocal): Int

    @Update
    suspend fun updateList(data: List<TrackingLocal>): Int

    @Delete
    suspend fun delete(vararg data: TrackingLocal): Int

    @Query("SELECT * FROM tracking")
    suspend fun getAll(): List<TrackingLocal>

    @Query("SELECT * FROM tracking WHERE status=:status ORDER BY timestamp ASC LIMIT 10")
    suspend fun getAllWithStatus(status: Int): List<TrackingLocal>

}