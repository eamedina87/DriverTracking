package tech.medina.drivertracking.data.datasource.local

import tech.medina.drivertracking.data.datasource.local.db.Database
import tech.medina.drivertracking.data.datasource.local.db.entities.DeliveryLocal
import tech.medina.drivertracking.data.datasource.local.db.entities.TrackingLocal

class LocalDataSourceImpl(
    private val database: Database
): LocalDataSource {

    //Delivery Dao
    override suspend fun saveDelivery(vararg delivery: DeliveryLocal): Boolean =
        database.deliveryDao().insert(* delivery).isNotEmpty()

    override suspend fun updateDelivery(vararg delivery: DeliveryLocal): Boolean =
        database.deliveryDao().update(* delivery) > 0

    override suspend fun getDeliveryList(): List<DeliveryLocal> =
        database.deliveryDao().getAll()

    override suspend fun getDeliveryWithId(id: Long): DeliveryLocal =
        database.deliveryDao().getDeliveryWithId(id)

    //TrackingDao
    override suspend fun saveTracking(vararg data: TrackingLocal): Boolean =
        database.trackingDao().insert(* data).isNotEmpty()

    override suspend fun updateTracking(vararg data: TrackingLocal): Boolean =
        database.trackingDao().update(* data) > 0

    override suspend fun deleteTracking(vararg data: TrackingLocal): Boolean =
        database.trackingDao().delete(* data) > 0

}