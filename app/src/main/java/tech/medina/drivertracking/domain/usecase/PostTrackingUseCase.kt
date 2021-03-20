package tech.medina.drivertracking.domain.usecase

import tech.medina.drivertracking.data.repository.tracking.TrackingRepository
import tech.medina.drivertracking.domain.model.Tracking
import javax.inject.Inject

class PostTrackingUseCase @Inject constructor(
    private val trackingRepository: TrackingRepository
) {

    suspend operator fun invoke(list: List<Tracking>): Boolean =
        trackingRepository.postTrackingData(list)

}