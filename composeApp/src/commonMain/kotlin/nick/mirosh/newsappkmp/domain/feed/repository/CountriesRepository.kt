package nick.mirosh.newsappkmp.domain.feed.repository

import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsappkmp.domain.feed.model.Country

interface CountriesRepository {
    suspend fun getCountries(): Result<List<Country>>
}
