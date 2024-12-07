package nick.mirosh.newsappkmp.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import co.touchlab.kermit.Logger
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.io.IOException
import java.util.Locale
import kotlin.coroutines.resume

class ReverseGeocodingServiceImpl(private val context: Context) : ReverseGeocodingService {
    override suspend fun getCountryCode(latitude: Double, longitude: Double): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getCountryCodeAsync(latitude, longitude)
        } else {
            getCountryCodeLegacy(latitude, longitude)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private suspend fun getCountryCodeAsync(latitude: Double, longitude: Double): String? {
        return suspendCancellableCoroutine { continuation ->
            val geocoder = Geocoder(context, Locale.getDefault())
            geocoder.getFromLocation(
                latitude,
                longitude,
                1,
                object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        val countryCode = addresses.firstOrNull()?.countryCode
                        continuation.resume(countryCode)
                    }

                    override fun onError(errorMessage: String?) {
                        Logger.e("Error getting country code: $errorMessage")
                        continuation.resume(null)
                    }
                }
            )
        }
    }

    private fun getCountryCodeLegacy(latitude: Double, longitude: Double): String? {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addressList = geocoder.getFromLocation(latitude, longitude, 1)
            addressList?.firstOrNull()?.countryCode
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
