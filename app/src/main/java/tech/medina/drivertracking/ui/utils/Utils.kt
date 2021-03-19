package tech.medina.drivertracking.ui.utils

import android.content.Context
import tech.medina.drivertracking.R

object Utils {

    fun isTablet(context: Context): Boolean =
        context.resources.getBoolean(R.bool.isTablet)

}