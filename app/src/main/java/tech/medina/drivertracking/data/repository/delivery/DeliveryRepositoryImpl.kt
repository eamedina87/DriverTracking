package tech.medina.drivertracking.data.repository.delivery

import tech.medina.drivertracking.data.datasource.local.LocalDataSource
import tech.medina.drivertracking.data.datasource.local.db.entities.DeliveryLocal
import tech.medina.drivertracking.data.datasource.remote.RemoteDataSource
import tech.medina.drivertracking.data.mapper.Mapper
import tech.medina.drivertracking.domain.model.Delivery
import tech.medina.drivertracking.domain.model.DeliveryStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeliveryRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val mapper: Mapper
): DeliveryRepository {

    /***
     * Obtains the List of deliveries from local
     * If local is empty or update is forced, we must update the data from remote
     ***/
    override suspend fun getDeliveryList(forceUpdate: Boolean): List<Delivery> {
        var localList = localDataSource.getDeliveryList()
        if (forceUpdate || localList.isEmpty()) {
            val timestamp = System.currentTimeMillis()
            val localData: List<DeliveryLocal> = remoteDataSource.getDeliveryList().deliveries.map {
                mapper.toLocal(it, timestamp)
            }
            localDataSource.saveDelivery(* localData.toTypedArray())
            localList = localDataSource.getDeliveryList()
        }
        return localList.map {
            mapper.toModel(it)
        }
    }

    /***
     * Obtains the specific delivery from local
     * If it is complete (has all information) returns from local, else updates from remote
     ***/
    override suspend fun getDeliveryDetailForId(id: Long): Delivery {
        val localData = localDataSource.getDeliveryWithId(id)
        if (!localData.isComplete()) {
            val remoteData = remoteDataSource.getDeliveryDetailForId(id)
            localData.requiresSignature = remoteData.requiresSignature
            localData.specialInstructions = remoteData.specialInstructions
            localDataSource.updateDelivery(localData)
        }
        return mapper.toModel(localData)
    }

    override suspend fun getActiveDelivery(): List<Delivery> =
        localDataSource.getDeliveryWithStatus(DeliveryStatus.ACTIVE.ordinal).map {
            mapper.toModel(it)
        }

    override suspend fun updateDelivery(vararg delivery: Delivery): Boolean {
        val localData = delivery.map {
            mapper.toLocal(it)
        }
        return localDataSource.updateDelivery(* localData.toTypedArray())
    }
}