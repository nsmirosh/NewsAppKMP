package nick.mirosh.newsappkmp.location

import co.touchlab.kermit.Logger
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLPlacemark
import kotlin.coroutines.resume

class IosReverseGeocodingService : ReverseGeocodingService {
    override suspend fun getCountryCode(latitude: Double, longitude: Double): String? {
        return suspendCancellableCoroutine { continuation ->
            val geocoder = CLGeocoder()
            geocoder.reverseGeocodeLocation(CLLocation(latitude, longitude)) { placemarks, error ->
                if (error != null) {
                    Logger.e("Error getting country code: ${error.localizedDescription()}")
                    continuation.resume(null)
                } else {
                    val placeMark = placemarks?.firstOrNull() as? CLPlacemark
                    continuation.resume(placeMark?.ISOcountryCode)
                }
            }
        }
    }
}
