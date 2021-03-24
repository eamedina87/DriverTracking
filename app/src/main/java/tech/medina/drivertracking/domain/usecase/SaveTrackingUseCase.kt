package tech.medina.drivertracking.domain.usecase

import tech.medina.drivertracking.data.repository.tracking.TrackingRepository
import tech.medina.drivertracking.domain.model.Tracking
import javax.inject.Inject

/****
 * This UseCase is in charge of saving the TrackingData in the database
 ****/

class SaveTrackingUseCase @Inject constructor(
    private val trackingRepository: TrackingRepository
) {

    suspend operator fun invoke(vararg data: Tracking): Boolean =
        try {
            trackingRepository.saveTrackingData(* data)
        } catch (e: Exception) {
            false
        }

}