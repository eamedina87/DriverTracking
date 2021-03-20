package tech.medina.drivertracking.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import tech.medina.drivertracking.data.datasource.location.entity.Location
import tech.medina.drivertracking.data.repository.location.LocationRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
) {

    suspend operator fun invoke(): StateFlow<Location> =
        locationRepository.getLocation()

}