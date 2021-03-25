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
    private var currentState: State = State.DEFAULT

    fun start(onSuccessStart: (() -> Unit)? = null) {
        when(currentState) {
            State.DEFAULT,
            State.STOPPED -> {
                startTracking()
                onSuccessStart?.invoke()
            }
            State.STARTED -> {
                stopTracking()
            }
        }
    }

    fun update() {
        if (currentState != State.STARTED) return
        startTracking()
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