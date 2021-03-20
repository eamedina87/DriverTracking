package tech.medina.drivertracking.data.repository.tracking

import tech.medina.drivertracking.data.datasource.local.LocalDataSource
import tech.medina.drivertracking.data.datasource.remote.RemoteDataSource
import tech.medina.drivertracking.data.datasource.remote.api.entities.response.ResponseStatus
import tech.medina.drivertracking.data.mapper.Mapper
import tech.medina.drivertracking.domain.model.Tracking
import tech.medina.drivertracking.domain.model.TrackingStatus
import javax.inject.Inject

class TrackingRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val mapper: Mapper
): TrackingRepository {

    override suspend fun saveTrackingData(vararg data: Tracking): Boolean {
        val localData = data.map {
            mapper.toLocal(it)
        }
        return localDataSource.saveTracking(* localData.toTypedArray())
    }

    override suspend fun getTrackingDataWithPredicate(predicate: (Tracking) -> Boolean): List<Tracking> =
        localDataSource.getAllTracking().map {
            mapper.toModel(it)
        }.filter {
            predicate(it)
        }

    override suspend fun getUnsentData(): List<Tracking> =
        localDataSource.getAllTrackingWithStatusNot(TrackingStatus.SENT.ordinal).map {
            mapper.toModel(it)
        }

    override suspend fun postTrackingData(list: List<Tracking>): Boolean {
        val data = mapper.toRemote(list, localDataSource.getDriverId())
        return remoteDataSource.postTracking(data).status == ResponseStatus.OK.status
    }

}