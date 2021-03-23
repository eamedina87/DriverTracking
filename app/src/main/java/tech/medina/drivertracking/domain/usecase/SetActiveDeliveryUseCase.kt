package tech.medina.drivertracking.domain.usecase

import tech.medina.drivertracking.data.repository.delivery.DeliveryRepository
import tech.medina.drivertracking.domain.model.Delivery
import tech.medina.drivertracking.domain.model.DeliveryStatus
import javax.inject.Inject

class SetActiveDeliveryUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {

    /***
     * This use case is in charge of setting the current active delivery to COMPLETED and
     * setting the parameter delivery as ACTIVE
     */

    suspend operator fun invoke(activeDelivery: Delivery): Boolean {
        activeDelivery.status = DeliveryStatus.ACTIVE
        val deliveries: ArrayList<Delivery> = ArrayList(deliveryRepository.getActiveDelivery())
        deliveries.map {
            it.status = DeliveryStatus.COMPLETED
        }
        deliveries.add(activeDelivery)
        return deliveryRepository.updateDelivery(* deliveries.toTypedArray())
    }


}