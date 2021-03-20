package tech.medina.drivertracking.data.datasource.location

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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mapper: Mapper
): LocationDataSource {

    private enum class LocationState {
        DEFAULT, REQUESTING, OBTAINED, STOPPED
    }

    private val _currentLocation: MutableStateFlow<Location> =
        MutableStateFlow(Location(-100.0,-100.0,0))

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
                }
            }

        }
    }

    override fun init() {
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)
        startLocationUpdates()
    }

    override fun stop() {
        stopLocationUpdates()
    }

    override fun getLocation(): StateFlow<Location> =
        _currentLocation.asStateFlow()

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
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
        if (state == LocationState.REQUESTING || state == LocationState.OBTAINED) {
            runIfLocationProviderIsInitialized {
                fusedLocationProvider.removeLocationUpdates(locationCallback)
                state = LocationState.STOPPED
            }
        }
    }

    private fun runIfLocationProviderIsInitialized(block: () -> Unit) {
        if (this::fusedLocationProvider.isInitialized) {
            block()
        }
    }

}

