package tech.medina.drivertracking.domain.tracking

import android.content.Context
import android.content.Intent
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import tech.medina.drivertracking.ui.service.TrackingService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackingManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private enum class State {
        DEFAULT, STARTED, STOPPED
    }

    private val serviceIntent = Intent(context, TrackingService::class.java)
    private var currentDeliveryId: Long = -1
    private var currentState: State = State.DEFAULT

    fun start(deliveryId: Long, onSuccessStart: (() -> Unit)? = null) {
        when(currentState) {
            State.DEFAULT,
            State.STOPPED -> {
                startTracking()
                currentDeliveryId = deliveryId
                onSuccessStart?.invoke()
            }
            State.STARTED -> {
                stopTracking()
                currentDeliveryId = -1
            }
        }
    }

    fun stop() {
        if (currentState != State.STARTED) return
        stopTracking()
    }

    private fun startTracking() {
        currentState = State.STARTED
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }

    private fun stopTracking() {
        currentState = State.STOPPED
        context.stopService(serviceIntent)
    }

}