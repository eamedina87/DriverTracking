package tech.medina.drivertracking.ui.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import dagger.hilt.android.qualifiers.ApplicationContext
import tech.medina.drivertracking.data.mapper.Mapper
import tech.medina.drivertracking.domain.model.Location
import tech.medina.drivertracking.ui.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mapper: Mapper
) {

    private enum class LocationState {
        DEFAULT, REQUESTING, OBTAINED, STOPPED
    }

    private var onLocationObtained: ((Location) -> Unit)? = null
    private var state = LocationState.DEFAULT
    private lateinit var fusedLocationProvider: FusedLocationProviderClient

    private val locationRequest: LocationRequest by lazy {
        LocationRequest.create().apply {
            interval = 2000
            fastestInterval = 1000
            smallestDisplacement = 5f
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private val locationCallback: LocationCallback by lazy {
        object: LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.locations.sortedBy {
                    it.accuracy
                }.map {
                    mapper.toLocation(it)
                }.firstOrNull()?.let {
                    state = LocationState.OBTAINED
                    Log.d(Constants.LOG_TAG_APP, "LocationManager.location $it")
                    onLocationObtained?.invoke(it)
                }
            }
        }
    }

    fun init(onLocationObtained: (Location) -> Unit) {
        Log.d(Constants.LOG_TAG_APP, "LocationManager.init")
        this.onLocationObtained = onLocationObtained
        if (!this::fusedLocationProvider.isInitialized) {
            fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)
        }
        startLocationUpdates()
    }

    fun stop() {
        Log.d(Constants.LOG_TAG_APP, "LocationManager.stop")
        stopLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        if (state == LocationState.REQUESTING || state == LocationState.OBTAINED) return
        runIfLocationProviderIsInitialized {
            fusedLocationProvider.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            state = LocationState.REQUESTING
        }
    }

    private fun stopLocationUpdates() {
        if (state != LocationState.REQUESTING && state != LocationState.OBTAINED) return
        runIfLocationProviderIsInitialized {
            fusedLocationProvider.removeLocationUpdates(locationCallback)
            state = LocationState.STOPPED
        }
    }

    private fun runIfLocationProviderIsInitialized(block: () -> Unit) {
        if (this::fusedLocationProvider.isInitialized) {
            block()
        }
    }

    fun mustBeInitialized(): Boolean = state == LocationState.DEFAULT || state == LocationState.STOPPED

}

