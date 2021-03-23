package tech.medina.drivertracking.domain.usecase

import tech.medina.drivertracking.data.repository.delivery.DeliveryRepository
import tech.medina.drivertracking.domain.model.Delivery
import javax.inject.Inject

class GetActiveDeliveryUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {

    suspend operator fun invoke(): Delivery? =
        deliveryRepository.getActiveDelivery().firstOrNull()


}