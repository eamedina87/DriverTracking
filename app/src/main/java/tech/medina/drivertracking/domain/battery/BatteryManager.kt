package tech.medina.drivertracking.domain.battery

import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import tech.medina.drivertracking.ui.utils.Constants
import tech.medina.drivertracking.ui.utils.Constants.BATTERY_UPDATE_PERIOD
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BatteryManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private enum class State {
        DEFAULT, INITIALIZED, OBTAINED, STOPPED, ERROR
    }

    private var onBatteryPercentageObtained: ((Int) -> Unit)? = null
    private var state: State = State.DEFAULT
    private var timer: Timer? = null

    fun init(onBatteryPercentageObtained: (Int) -> Unit) {
        if (!mustBeInitialized()) return
        Log.d(Constants.LOG_TAG_APP, "BatteryManager.init")
        this.onBatteryPercentageObtained = onBatteryPercentageObtained
        state = try {
            timer = Timer()
            timer?.scheduleAtFixedRate(object: TimerTask() {
                override fun run() {
                    getBatteryData()
                }
            }, 0, BATTERY_UPDATE_PERIOD)
            State.INITIALIZED
        } catch (e: IllegalStateException) {
            State.ERROR
        }
    }

    fun stop() {
        Log.d(Constants.LOG_TAG_APP, "BatteryManager.stop")
        timer?.cancel()
        state = State.STOPPED
    }

    private fun getBatteryData() {
        val battery = if (Build.VERSION.SDK_INT >= 21) {
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
        Log.d(Constants.LOG_TAG_APP, "BatteryManager.percentage $battery")
        onBatteryPercentageObtained?.invoke(battery)
        state = State.OBTAINED
    }

    fun mustBeInitialized(): Boolean = state != State.INITIALIZED && state != State.OBTAINED

}