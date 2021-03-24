package tech.medina.drivertracking.domain.usecase

import tech.medina.drivertracking.data.repository.tracking.TrackingRepository
import tech.medina.drivertracking.domain.model.Tracking
import tech.medina.drivertracking.domain.model.TrackingStatus
import javax.inject.Inject

/****
 * This UseCase is in charge of sending the TrackingData to the API
 * and setting the state of the data to SENT in the database
 ****/

class SendTrackingUseCase @Inject constructor(
    private val trackingRepository: TrackingRepository
) {

    suspend operator fun invoke(): Boolean {
        return try {
            val trackingDataToBeSent: List<Tracking> =
                    trackingRepository.getTrackingDataWithStatus(TrackingStatus.DEFAULT.ordinal)
            val result = trackingRepository.postTrackingData(trackingDataToBeSent)
            if (result) {
                val dataToUpdate = trackingDataToBeSent.map {
                    it.status = TrackingStatus.SENT
                    it
                }
                trackingRepository.updateTrackingData(System.currentTimeMillis(), * dataToUpdate.toTypedArray())
            }
            result
        } catch (e: Exception) {
            false
        }
    }

}