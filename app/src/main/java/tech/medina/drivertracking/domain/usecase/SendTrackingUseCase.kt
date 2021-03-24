package tech.medina.drivertracking.domain.usecase

import tech.medina.drivertracking.data.repository.tracking.TrackingRepository
import tech.medina.drivertracking.domain.model.Tracking
import tech.medina.drivertracking.domain.model.TrackingStatus
import javax.inject.Inject

class SendTrackingUseCase @Inject constructor(
    private val trackingRepository: TrackingRepository
) {

    suspend operator fun invoke(): Boolean {
        val trackingToBeSent: List<Tracking> = trackingRepository.getUnsentData()
        val result = trackingRepository.postTrackingData(trackingToBeSent)
        if (result) {
            val datoToUpdate = trackingToBeSent.map {
                it.status = TrackingStatus.SENT
                it
            }
            trackingRepository.updateTrackingData(* datoToUpdate.toTypedArray()) //todo check db is updated after sending
        }
        return result
    }

}