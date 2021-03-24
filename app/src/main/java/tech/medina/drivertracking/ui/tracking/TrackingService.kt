package tech.medina.drivertracking.ui.tracking

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import tech.medina.drivertracking.R
import tech.medina.drivertracking.domain.model.Delivery
import tech.medina.drivertracking.domain.model.Location
import tech.medina.drivertracking.domain.model.Tracking
import tech.medina.drivertracking.domain.model.TrackingStatus
import tech.medina.drivertracking.domain.usecase.GetActiveDeliveryUseCase
import tech.medina.drivertracking.domain.usecase.RemoveTrackingUseCase
import tech.medina.drivertracking.domain.usecase.SendTrackingUseCase
import tech.medina.drivertracking.domain.usecase.SaveTrackingUseCase
import tech.medina.drivertracking.ui.battery.BatteryManager
import tech.medina.drivertracking.ui.delivery.list.DeliveryListActivity
import tech.medina.drivertracking.ui.location.LocationManager
import tech.medina.drivertracking.ui.utils.Constants.LOG_TAG_APP
import tech.medina.drivertracking.ui.utils.Constants.NOTIFICATION_ID
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class TrackingService: Service() {

    @Inject
    lateinit var locationManager: LocationManager
    @Inject
    lateinit var batteryManager: BatteryManager
    @Inject
    lateinit var getActiveDeliveryUseCase: GetActiveDeliveryUseCase
    @Inject
    lateinit var saveTrackingUseCase: SaveTrackingUseCase
    @Inject
    lateinit var sendTrackingUseCase: SendTrackingUseCase
    @Inject
    lateinit var removeTrackingUseCase: RemoveTrackingUseCase


    private var currentLocation: Location? = null
    private var currentBatteryPercentage: Int = -1
    private var activeDelivery: Delivery? = null
    private var coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var isSaveTimerActive: Boolean = false
    private var isSendTimerActive: Boolean = false
    private var isRemoveTimerActive: Boolean = false
    private val savePeriod = 1000L // 1 second
    private val sendPeriod = 30 * 1000L // 10 seconds
    private val removePeriod = 60 * 1000L // 1 minute
    private val saveTimer: Timer = Timer()
    private val sendTimer: Timer = Timer()
    private val removeTimer: Timer = Timer()

    private val saveTimerTask: TimerTask = object: TimerTask() {
        override fun run() {
            saveTrackingData()
        }
    }

    private val sendTimerTask: TimerTask = object: TimerTask() {
        override fun run() {
            sendTrackingData()
        }
    }

    private val removeTimerTask: TimerTask = object: TimerTask() {
        override fun run() {
            removeTrackingData()
        }
    }

    private val notificationIntent: Intent by lazy {
        Intent(applicationContext, DeliveryListActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }

    private val pendingIntent: PendingIntent by lazy {
        PendingIntent.getActivity(applicationContext, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    override fun onBind(intent: Intent?): IBinder? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG_TAG_APP, "TrackingService.start")
        startForeground(NOTIFICATION_ID, getNotification())
        handleWork()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG_APP, "TrackingService.destroy")
        locationManager.stop()
        batteryManager.stop()
        sendTimer.cancel()
        saveTimer.cancel()
        removeTimer.cancel()
        isSaveTimerActive = false
        isSendTimerActive = false
        coroutineScope.cancel()
    }

    private fun handleWork() {
        initLocationManager()
        initBatteryManager()
        coroutineScope.launch {
            getActiveDelivery()
        }
        initSaveTrackingTimer()
        initSendTrackingTimer()
        initRemoveTrackingTimer()
    }


    private fun initSendTrackingTimer() {
        if (!isSendTimerActive) {
            sendTimer.scheduleAtFixedRate(sendTimerTask, 3 * 1000, sendPeriod)
            isSendTimerActive = true
        }
    }

    private fun initSaveTrackingTimer() {
        if (!isSaveTimerActive) {
            saveTimer.scheduleAtFixedRate(saveTimerTask, 5 * 1000, savePeriod)
            isSaveTimerActive = true
        }
    }

    private fun initRemoveTrackingTimer() {
        if (!isRemoveTimerActive) {
            removeTimer.scheduleAtFixedRate(removeTimerTask, 40 * 1000, removePeriod)
            isRemoveTimerActive = true
        }
    }

    private fun initBatteryManager() {
        if (batteryManager.mustBeInitialized()) {
            batteryManager.init() {
                currentBatteryPercentage = it
            }
        }
    }

    private suspend fun getActiveDelivery() {
        getActiveDeliveryUseCase()?.let {
            activeDelivery = it
        }
    }

    private fun initLocationManager() {
        if (locationManager.mustBeInitialized()) {
            locationManager.init() {
                currentLocation = it
            }
        }
    }

    private fun saveTrackingData() {
        currentLocation ?: return
        activeDelivery ?: return
        if (currentBatteryPercentage == -1) return
        val tracking = Tracking(
            id = 0,
            latitude = currentLocation!!.latitude,
            longitude = currentLocation!!.longitude,
            deliveryId = activeDelivery!!.id,
            batteryLevel = currentBatteryPercentage,
            timestamp = System.currentTimeMillis(),
            status = TrackingStatus.DEFAULT
        )
        coroutineScope.launch {
            Log.d(LOG_TAG_APP, "TrackingService.saveTrackingData $tracking")
            saveTrackingUseCase(tracking)
        }
    }

    private fun sendTrackingData() {
        coroutineScope.launch {
            Log.d(LOG_TAG_APP, "TrackingService.sendTrackingData")
            sendTrackingUseCase()
        }
    }

    private fun removeTrackingData() {
        coroutineScope.launch {
            Log.d(LOG_TAG_APP, "TrackingService.removeTrackingData")
            removeTrackingUseCase()
        }
    }

    private fun getNotification(): Notification =
        NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_message))
            .setContentIntent(pendingIntent)
            .build()

}