package tech.medina.drivertracking.domain.usecase

import tech.medina.drivertracking.data.repository.delivery.DeliveryRepository
import tech.medina.drivertracking.domain.model.Delivery
import tech.medina.drivertracking.domain.model.DeliveryStatus
import javax.inject.Inject

/****
 * This UseCase is in charge of updating the Delivery's status
 * to Completed/Finished in the database
 ****/

class SetCompleteDeliveryUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {

    suspend operator fun invoke(delivery: Delivery): Boolean {
        delivery.status = DeliveryStatus.COMPLETED
        return deliveryRepository.updateDelivery(delivery)
    }


}