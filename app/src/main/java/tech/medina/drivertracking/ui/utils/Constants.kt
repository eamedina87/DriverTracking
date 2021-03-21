package tech.medina.drivertracking.ui.utils

import android.Manifest

object Constants {

    const val LOG_TAG_APP = "drivetracking.log"
    const val INTENT_EXTRA_DELIVERY_ID = "drivetracking.intent.extra.delivery.id"
    val LOCATION_PERMISSION = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    } else {
        Manifest.permission.ACCESS_FINE_LOCATION
    }

}