package tech.medina.drivertracking.ui.utils

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

object Constants {

    const val LOG_TAG_APP = "drivetracking.log"
    const val INTENT_EXTRA_DELIVERY_ID = "drivetracking.intent.extra.delivery.id"
    const val LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
    @RequiresApi(Build.VERSION_CODES.Q)
    const val BACKGROUND_LOCATION_PERMISSION = Manifest.permission.ACCESS_BACKGROUND_LOCATION
    const val NOTIFICATION_ID = 77777

}