package tech.medina.drivertracking.data.datasource.local.db.dao

import androidx.room.*
import tech.medina.drivertracking.data.datasource.local.db.entities.DeliveryLocal

@Dao
interface DeliveryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg delivery: DeliveryLocal): List<Long>

    @Update
    suspend fun update(vararg delivery: DeliveryLocal): Int

    @Query("SELECT * FROM delivery")
    suspend fun getAll(): List<DeliveryLocal>

    @Query("SELECT * FROM delivery WHERE id=:id")
    suspend fun getDeliveryWithId(id: Long): DeliveryLocal

    @Query("SELECT * FROM delivery WHERE status=:status")
    suspend fun getDeliveryWithStatus(status: Int): List<DeliveryLocal>

}