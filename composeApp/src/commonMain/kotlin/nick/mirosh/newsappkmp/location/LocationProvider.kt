package nick.mirosh.newsappkmp.location

import kotlinx.coroutines.flow.Flow

interface LocationProvider {
    fun getCurrentLocation(): Flow<LocationData?>
}

data class LocationData(val latitude: Double, val longitude: Double)



//expect fun getLocationProvider(): LocationProvider