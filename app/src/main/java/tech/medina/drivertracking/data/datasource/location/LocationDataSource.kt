package tech.medina.drivertracking.data.datasource.location

import kotlinx.coroutines.flow.StateFlow
import tech.medina.drivertracking.data.datasource.location.entity.Location

interface LocationDataSource {

    fun init()
    fun stop()
    fun getLocation(): StateFlow<Location>

}