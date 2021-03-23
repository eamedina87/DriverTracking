package tech.medina.drivertracking.domain.usecase

import tech.medina.drivertracking.data.repository.tracking.TrackingRepository
import javax.inject.Inject

class SendTrackingUseCase @Inject constructor(
    private val trackingRepository: TrackingRepository
) {

    suspend operator fun invoke(): Boolean {
        val trackingToBeSent = trackingRepository.getUnsentData()
        return trackingRepository.postTrackingData(trackingToBeSent)
    }

}