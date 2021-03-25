package tech.medina.drivertracking.ui.utils

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

object Constants {

    const val LOG_TAG_APP = "drivertracking.log"
    const val INTENT_EXTRA_DELIVERY_ID = "drivetracking.intent.extra.delivery.id"
    const val LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
    @RequiresApi(Build.VERSION_CODES.Q)
    const val BACKGROUND_LOCATION_PERMISSION = Manifest.permission.ACCESS_BACKGROUND_LOCATION
    const val NOTIFICATION_ID = 77777

    //Timers
    const val BATTERY_UPDATE_PERIOD =  5 * 60 * 1000L // 5 minutes
    const val TRACKING_SAVE_PERIOD = 1000L // 1 second
    const val TRACKING_SAVE_INIT_DELAY = 3 * 1000L // 3 seconds
    const val TRACKING_SEND_PERIOD = 10 * 1000L // 10 seconds
    const val TRACKING_SEND_INIT_DELAY = 30 * 1000L // 30 seconds
    const val TRACKING_REMOVE_PERIOD = 60 * 1000L // 1 minute
    const val TRACKING_REMOVE_INIT_DELAY = 60 * 1000L // 1 minute

    //Location
    const val LOCATION_UPDATE_INTERVAL  = 2 * 1000L // 2 seconds
    const val LOCATION_UPDATE_FASTEST_INTERVAL  = 1 * 1000L // 1 seconds
    const val LOCATION_SMALLEST_DISPLACEMENT = 5f //5 meters

}