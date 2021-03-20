package tech.medina.drivertracking.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import tech.medina.drivertracking.data.repository.battery.BatteryRepository

import javax.inject.Inject

class GetBatteryUseCase @Inject constructor(
    private val batteryRepository: BatteryRepository,
) {

    suspend operator fun invoke(): StateFlow<Int> =
        batteryRepository.getBatteryLevel()

}