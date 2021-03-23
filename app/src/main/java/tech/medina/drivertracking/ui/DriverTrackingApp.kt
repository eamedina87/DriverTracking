package tech.medina.drivertracking.ui

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import tech.medina.drivertracking.R

@HiltAndroidApp
class DriverTrackingApp: Application() {

    override fun onCreate() {
        super.onCreate()
        registerNotificationChannel()
    }

    private fun registerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(getString(R.string.notification_channel_id), name, importance)
            channel.description = getString(R.string.notification_channel_description)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}