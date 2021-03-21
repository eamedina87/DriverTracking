package tech.medina.drivertracking.ui.utils

import android.content.Context
import tech.medina.drivertracking.R

fun Boolean.toYesOrNo(context: Context): String =
    if (this) context.getString(R.string.delivery_requires_signature_yes)
    else context.getString(R.string.delivery_requires_signature_no)