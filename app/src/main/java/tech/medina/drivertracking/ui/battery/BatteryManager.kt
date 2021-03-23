package tech.medina.drivertracking.ui.battery

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
    private val _currentBatteryPercentage: MutableStateFlow<Int> = MutableStateFlow(-1)
    val currentBatteryPercentage: StateFlow<Int> get() = _currentBatteryPercentage
    private val updatePeriod =  30 * 1000L // 3 minutes
    private var timer: Timer? = null

    fun init(onBatteryPercentageObtained: (Int) -> Unit) {
        //if (!mustBeInitialized()) return
        this.onBatteryPercentageObtained = onBatteryPercentageObtained
        state = try {
            timer?.cancel()
            timer = Timer().apply {
                scheduleAtFixedRate(object: TimerTask() {
                    override fun run() {
                        getBatteryData()
                    }
                }, 0, updatePeriod)
            }
            State.INITIALIZED
        } catch (e: IllegalStateException) {
            State.ERROR
        }
    }

    fun stop() {
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
        _currentBatteryPercentage.value = battery
        onBatteryPercentageObtained?.invoke(battery)
        state = State.OBTAINED
    }

    fun mustBeInitialized(): Boolean = state != State.INITIALIZED && state != State.OBTAINED

}