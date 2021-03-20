package tech.medina.drivertracking.data.repository.location

import kotlinx.coroutines.flow.StateFlow
import tech.medina.drivertracking.data.datasource.location.LocationDataSource
import tech.medina.drivertracking.data.datasource.location.entity.Location
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val dataSource: LocationDataSource
): LocationRepository {

    override fun init() {
        dataSource.getLocation()
    }

    override fun stop() {
        dataSource.stop()
    }

    override fun getLocation(): StateFlow<Location> =
        dataSource.getLocation()

}