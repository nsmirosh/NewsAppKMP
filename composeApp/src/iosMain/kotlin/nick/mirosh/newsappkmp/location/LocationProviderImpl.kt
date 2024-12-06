package nick.mirosh.newsappkmp.location

import co.touchlab.kermit.Logger
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import platform.CoreLocation.*
import platform.Foundation.NSError
import platform.darwin.NSObject

class IOSLocationProvider : LocationProvider {
    private val locationFlow = MutableSharedFlow<LocationData?>(onBufferOverflow = BufferOverflow.DROP_LATEST, extraBufferCapacity = 1 )
    private val locationManager = CLLocationManager()
    private val delegate = IOSLocationDelegate { locationData ->
        Logger.w("IOSLocationDelegate: location updated with data $locationData")
        val emmited = locationFlow.tryEmit(locationData)
        if (!emmited) {
            Logger.e("IOSLocationDelegate: failed to emit location data")
        }
        locationManager.stopUpdatingLocation()
    }

    init {
        locationManager.delegate = delegate
        locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
    }

    override fun getCurrentLocation(): Flow<LocationData?> {
        Logger.w("IOS: getCurrentLocation()")
        requestLocationPermission()
        locationManager.startUpdatingLocation()
        return locationFlow
    }

    private fun requestLocationPermission() {
        val authorizationStatus = CLLocationManager.authorizationStatus()
        if (authorizationStatus == kCLAuthorizationStatusNotDetermined) {
            Logger.w("LocationProvider: requesting when in use authorization")
            locationManager.requestWhenInUseAuthorization()
        } else if (authorizationStatus != kCLAuthorizationStatusAuthorizedWhenInUse &&
            authorizationStatus != kCLAuthorizationStatusAuthorizedAlways
        ) {
            Logger.e("LocationProvider: Location permission is denied")
            // Handle case where location permission is denied
            locationFlow.tryEmit(null)
        }
    }
}


class IOSLocationDelegate(
    private val onLocationUpdated: (LocationData?) -> Unit
) : NSObject(), CLLocationManagerDelegateProtocol {

    @OptIn(ExperimentalForeignApi::class)
    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        Logger.w("LocationProvider: updating location")
        val lastLocation = didUpdateLocations.lastOrNull() as? CLLocation
        val coordinate = lastLocation?.coordinate

        val latitude: Double = coordinate?.useContents { latitude } ?: 0.0
        val longitude: Double = coordinate?.useContents { longitude } ?: 0.0
        Logger.w("LocationProvider: latitude: $latitude, longitude: $longitude")
        onLocationUpdated(LocationData(latitude, longitude))

    }

    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
        Logger.w("LocationProvider: another callback without implementation")
        onLocationUpdated(null)
    }
}
