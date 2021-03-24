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
        }.toTypedArray()
        return localDataSource.saveTracking(* localData)
    }

    override suspend fun updateTrackingData(timestamp: Long, vararg data: Tracking): Boolean {
        val localData = data.map {
            mapper.toLocal(it, timestamp)
        }.toTypedArray()
        return localDataSource.updateTracking(* localData)
    }

    override suspend fun removeTrackingData(vararg data: Tracking): Boolean {
        val localData = data.map {
            mapper.toLocal(it)
        }.toTypedArray()
        return localDataSource.deleteTracking(* localData)
    }

    override suspend fun getTrackingDataWithPredicate(predicate: (Tracking) -> Boolean): List<Tracking> =
        localDataSource.getAllTracking().map {
            mapper.toModel(it)
        }.filter {
            predicate(it)
        }

    override suspend fun getTrackingDataWithStatus(status: Int): List<Tracking> {
        val data = localDataSource.getAllTrackingWithStatus(status)
        return data.map {
            mapper.toModel(it)
        }
    }

    override suspend fun postTrackingData(tracking: List<Tracking>): Boolean {
        val data = mapper.toRemote(tracking, localDataSource.getDriverId())
        val result = remoteDataSource.postTracking(data)
        return result.status == ResponseStatus.OK.status
    }

}