package tech.medina.drivertracking.data.repository.battery

import kotlinx.coroutines.flow.StateFlow
import tech.medina.drivertracking.data.datasource.battery.BatteryDataSource
import javax.inject.Inject

class BatteryRepositoryImpl @Inject constructor(
    private val dataSource: BatteryDataSource
    ): BatteryRepository {

    override fun init() {
        dataSource.init()
    }

    override fun stop() {
        dataSource.stop()
    }

    override fun getBatteryLevel(): StateFlow<Int> =
        dataSource.getBatteryLevel()

}