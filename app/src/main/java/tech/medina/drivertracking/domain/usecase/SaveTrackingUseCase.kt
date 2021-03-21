package tech.medina.drivertracking.domain.usecase

import tech.medina.drivertracking.data.repository.tracking.TrackingRepository
import tech.medina.drivertracking.domain.model.Tracking
import javax.inject.Inject

class SaveTrackingUseCase @Inject constructor(
    private val trackingRepository: TrackingRepository
) {

    suspend operator fun invoke(vararg data: Tracking): Boolean =
        trackingRepository.saveTrackingData(* data)

}