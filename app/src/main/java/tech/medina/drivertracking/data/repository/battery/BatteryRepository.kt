package tech.medina.drivertracking.data.repository.battery

import kotlinx.coroutines.flow.StateFlow

interface BatteryRepository {

    fun init()
    fun stop()
    fun getBatteryLevel(): StateFlow<Int>

}