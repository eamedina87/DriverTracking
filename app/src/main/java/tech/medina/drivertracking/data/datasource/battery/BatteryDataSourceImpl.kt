package tech.medina.drivertracking.data.datasource.battery

import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import javax.inject.Inject

class BatteryDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
): BatteryDataSource {

    private enum class State {
        DEFAULT, INITIALIZED, OBTAINED, STOPPED, ERROR
    }

    private var state: State = State.DEFAULT
    private val _currentBatteryPercentage: MutableStateFlow<Int> =
        MutableStateFlow(-1)

    private val updatePeriod = 3 * 60 * 1000L // 3 minutes

    private val timer: Timer by lazy {
        Timer()
    }

    private val timerTask: TimerTask by lazy {
        object: TimerTask() {
            override fun run() {
                getBatteryData()
            }
        }
    }

    override fun init() {
        if (state != State.INITIALIZED) {
            state = try {
                timer.schedule(timerTask, updatePeriod)
                State.INITIALIZED
            } catch (e: IllegalStateException) {
                State.ERROR
            }
        }
    }

    override fun stop() {
        timer.cancel()
        state = State.STOPPED
    }

    override fun getBatteryLevel(): StateFlow<Int> =
        _currentBatteryPercentage.asStateFlow()

    private fun getBatteryData() {
        _currentBatteryPercentage.value = if (Build.VERSION.SDK_INT >= 21) {
            val batteryManager = context.getSystemService(BATTERY_SERVICE) as BatteryManager
            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            val batteryStatus: Intent? = context.registerReceiver(null, intentFilter)
            val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
            val batteryPct = level / scale.toDouble()
            (batteryPct * 100).toInt()
        }
        state = State.OBTAINED
    }

}