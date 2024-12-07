package nick.mirosh.newsappkmp.location

interface ReverseGeocodingService {
    suspend fun getCountryCode(latitude: Double, longitude: Double): String?
}
