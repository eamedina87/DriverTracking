package tech.medina.drivertracking.data.repository.location

import kotlinx.coroutines.flow.StateFlow
import tech.medina.drivertracking.data.datasource.location.entity.Location

interface LocationRepository {

    fun init()
    fun stop()
    fun getLocation(): StateFlow<Location>

}