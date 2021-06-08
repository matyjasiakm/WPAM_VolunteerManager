package pl.matyjasiakm.volunteermanager.data.rest

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import pl.matyjasiakm.volunteermanager.data.dao.DailyForecastList
import pl.matyjasiakm.volunteermanager.data.dao.WeatherLocation
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    val LOCATION_STRING: String
        get() = "http://dataservice.accuweather.com"

    @GET("/locations/v1/cities/geoposition/search")
    fun getLocationKey(
        @Query("apikey") apiKey: String,
        @Query("q") q: String
    ): Single<WeatherLocation>

    @GET("/forecasts/v1/daily/5day/{locationKey}")
    fun get5DayForecast(
        @Path("locationKey") locationKey: String,
        @Query("apikey") apikey: String
    ): Observable<DailyForecastList>
}