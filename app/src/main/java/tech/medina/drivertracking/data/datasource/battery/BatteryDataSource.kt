package tech.medina.drivertracking.data.datasource.battery

import kotlinx.coroutines.flow.StateFlow

interface BatteryDataSource {

    fun init()
    fun stop()
    fun getBatteryLevel(): StateFlow<Int>

}