package tech.medina.drivertracking.domain.usecase

import tech.medina.drivertracking.data.repository.tracking.TrackingRepository
import tech.medina.drivertracking.domain.model.Tracking
import tech.medina.drivertracking.domain.model.TrackingStatus
import javax.inject.Inject

/****
 * This UseCase is in charge of clearing the database
 * by deleting all TrackingData with SENT status
 ****/

class RemoveTrackingUseCase @Inject constructor(
    private val trackingRepository: TrackingRepository
) {

    suspend operator fun invoke(): Boolean {
        return try {
            val trackingDataToBeRemoved: List<Tracking> =
                    trackingRepository.getTrackingDataWithStatus(TrackingStatus.SENT.ordinal)

            trackingRepository.removeTrackingData(* trackingDataToBeRemoved.toTypedArray())
        } catch (e: Exception) {
            false
        }
    }

}