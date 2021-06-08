package pl.matyjasiakm.volunteermanager.di.module

import dagger.Module
import dagger.Provides
import pl.matyjasiakm.volunteermanager.data.rest.WeatherService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class WeatherServiceModule {
    companion object {
        @JvmStatic var service: WeatherService? = null
    }
    @Provides
    @Singleton
    fun provideService():WeatherService{
        if (service == null){
            val retrofit= Retrofit.Builder().baseUrl("http://dataservice.accuweather.com").addConverterFactory(
                GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build()
            service= retrofit.create(WeatherService::class.java)
        }
        return service!!;
    }
}