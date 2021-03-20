package tech.medina.drivertracking.data.repository.delivery

import tech.medina.drivertracking.data.datasource.local.LocalDataSource
import tech.medina.drivertracking.data.datasource.remote.RemoteDataSource
import tech.medina.drivertracking.data.mapper.Mapper
import tech.medina.drivertracking.domain.model.Delivery

class DeliveryRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val mapper: Mapper
): DeliveryRepository {

    override fun getDeliveryList(forceUpdate: Boolean): List<Delivery> {
        TODO("Not yet implemented")
    }

    override fun getDeliveryDetailForId(id: Long): Delivery {
        TODO("Not yet implemented")
    }

    override fun activateDelivery(delivery: Delivery): Boolean {
        TODO("Not yet implemented")
    }

    override fun deactivateDelivery(delivery: Delivery): Boolean {
        TODO("Not yet implemented")
    }

    override fun deactivateAllDeliveries(): Boolean {
        TODO("Not yet implemented")
    }

}