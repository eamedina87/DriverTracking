package tech.medina.drivertracking.ui.tracking

object TrackingManager {

    private enum class State {
        DEFAULT, STARTED, STOPPED
    }

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
        //todo launchService
    }

    private fun stopTracking() {
        currentState = State.STOPPED
        //todo stopService
    }

}