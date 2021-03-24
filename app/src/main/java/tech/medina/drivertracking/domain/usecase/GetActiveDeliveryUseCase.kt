package tech.medina.drivertracking.domain.usecase

import tech.medina.drivertracking.data.repository.delivery.DeliveryRepository
import tech.medina.drivertracking.domain.model.Delivery
import javax.inject.Inject

/***
 * This use case is in charge of getting the current ACTIVE Delivery from database
 */

class GetActiveDeliveryUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {

    suspend operator fun invoke(): Delivery? =
        deliveryRepository.getActiveDelivery().firstOrNull()


}