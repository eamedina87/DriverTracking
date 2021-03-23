package tech.medina.drivertracking.data.repository.tracking

import tech.medina.drivertracking.domain.model.Tracking

interface TrackingRepository {

    suspend fun saveTrackingData(vararg data: Tracking): Boolean
    suspend fun getTrackingDataWithPredicate(predicate: (Tracking) -> Boolean): List<Tracking>
    suspend fun getUnsentData(): List<Tracking>
    suspend fun postTrackingData(tracking: List<Tracking>): Boolean

}