package nick.mirosh.newsappkmp.location

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import platform.CoreLocation.*
import platform.Foundation.NSError
import platform.darwin.NSObject

class IOSLocationProvider : LocationProvider {
    private val locationFlow = MutableSharedFlow<LocationData?>()
    private val locationManager = CLLocationManager()
    private val delegate = IOSLocationDelegate { locationData ->
        locationFlow.tryEmit(locationData)
        locationManager.stopUpdatingLocation()
    }

    init {
        locationManager.delegate = delegate
        locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
    }

    override fun getCurrentLocation(): Flow<LocationData?> {
        requestLocationPermission()
        locationManager.startUpdatingLocation()
        return locationFlow
    }

    private fun requestLocationPermission() {
        val authorizationStatus = CLLocationManager.authorizationStatus()
        if (authorizationStatus == kCLAuthorizationStatusNotDetermined) {
            locationManager.requestWhenInUseAuthorization()
        } else if (authorizationStatus != kCLAuthorizationStatusAuthorizedWhenInUse &&
            authorizationStatus != kCLAuthorizationStatusAuthorizedAlways
        ) {
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
        val lastLocation = didUpdateLocations.lastOrNull() as? CLLocation
        val coordinate = lastLocation?.coordinate
        val latitude: Double = coordinate?.useContents { latitude } ?: 0.0
        val longitude: Double = coordinate?.useContents { longitude } ?: 0.0
        onLocationUpdated(LocationData(latitude, longitude))
    }

    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
        onLocationUpdated(null)
    }
}
