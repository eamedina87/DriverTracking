package tech.medina.drivertracking.ui.utils

import android.content.Context
import android.os.Build
import tech.medina.drivertracking.R

object Utils {

    fun isTablet(context: Context): Boolean =
        context.resources.getBoolean(R.bool.isTablet)

    fun isAndroidQOrHigher(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

}