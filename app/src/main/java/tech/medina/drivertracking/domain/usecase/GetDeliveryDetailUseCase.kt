package tech.medina.drivertracking.domain.usecase

import tech.medina.drivertracking.data.repository.delivery.DeliveryRepository
import tech.medina.drivertracking.domain.model.Delivery
import javax.inject.Inject

class GetDeliveryDetailUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {

    suspend operator fun invoke(id: Long): Delivery =
        deliveryRepository.getDeliveryDetailForId(id)

}