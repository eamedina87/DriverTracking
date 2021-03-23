package tech.medina.drivertracking.ui.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.medina.drivertracking.data.datasource.location.entity.Location
import tech.medina.drivertracking.data.mapper.Mapper
import tech.medina.drivertracking.domain.model.DeliveryStatus
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
    private val _currentLocation: MutableStateFlow<Location> =
        MutableStateFlow(Location(-100.0,-100.0,0))
    val currentLocation: StateFlow<Location> get() = _currentLocation

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
                    _currentLocation.value = it
                    onLocationObtained?.invoke(it)
                }
            }
        }
    }

    fun init(onLocationObtained: (Location) -> Unit) {
        this.onLocationObtained = onLocationObtained
        if (!this::fusedLocationProvider.isInitialized) {
            fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)
        }
        startLocationUpdates()
    }

    fun stop() {
        stopLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        //if (state == LocationState.REQUESTING || state == LocationState.OBTAINED) return
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
        //if (state != LocationState.REQUESTING && state != LocationState.OBTAINED) return
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

