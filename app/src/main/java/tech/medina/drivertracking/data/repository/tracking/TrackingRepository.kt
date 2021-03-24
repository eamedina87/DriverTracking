package tech.medina.drivertracking.data.repository.tracking

import tech.medina.drivertracking.domain.model.Tracking

interface TrackingRepository {

    suspend fun saveTrackingData(vararg data: Tracking): Boolean
    suspend fun updateTrackingData(timestamp: Long, vararg data: Tracking): Boolean
    suspend fun removeTrackingData(vararg data: Tracking): Boolean
    suspend fun getTrackingDataWithPredicate(predicate: (Tracking) -> Boolean): List<Tracking>
    suspend fun getTrackingDataWithStatus(status: Int): List<Tracking>
    suspend fun postTrackingData(tracking: List<Tracking>): Boolean

}