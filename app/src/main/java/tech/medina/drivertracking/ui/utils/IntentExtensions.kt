package tech.medina.drivertracking.ui.utils

import android.content.Intent
import android.os.Bundle
import android.util.Log
import tech.medina.drivertracking.ui.utils.Constants.LOG_TAG_APP

inline fun <reified T> Intent?.getExtra(extraName: String, crossinline function: (T) -> Unit) {
    checkExtra(extraName) { intent ->
        if (intent.extras?.get(extraName)?.javaClass == T::class.java) {
            (intent.extras?.get(extraName) as T)?.let {
                function(it)
            }
        } else {
            Log.e(LOG_TAG_APP, "getExtra: Extra's ${this?.extras?.get(extraName)?.javaClass}" +
                    " is different than specified ${T::class.java}")
        }
    }
}

fun Intent?.checkExtra(extraName: String, function: (Intent) -> Unit) {
    if (this?.hasExtra(extraName) == true) {
        function(this)
    } else {
        Log.e(LOG_TAG_APP, "checkExtra: No extra with name '$extraName' found in Intent")
    }
}

inline fun <reified T> Bundle?.getExtra(extraName: String, crossinline function: (T) -> Unit) {
    checkExtra(extraName) { bundle ->
        if (bundle.get(extraName)?.javaClass == T::class.java) {
            (bundle.get(extraName) as T)?.let {
                function(it)
            }
        } else {
            Log.e(LOG_TAG_APP, "getExtra: Extra's ${this?.get(extraName)?.javaClass}" +
                    " is different than specified ${T::class.java}")
        }
    }
}

fun Bundle?.checkExtra(extraName: String, function: (Bundle) -> Unit) {
    if (this?.containsKey(extraName) == true) {
        function(this)
    } else {
        Log.e(LOG_TAG_APP, "checkExtra: No extra with name '$extraName' found in Intent")
    }
}