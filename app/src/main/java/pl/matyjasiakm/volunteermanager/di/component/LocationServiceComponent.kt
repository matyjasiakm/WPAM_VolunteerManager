package pl.matyjasiakm.volunteermanager.di.component

import android.location.Location
import dagger.Component

import pl.matyjasiakm.volunteermanager.di.module.*

import pl.matyjasiakm.volunteermanager.services.LocationService
import javax.inject.Singleton

@Singleton
@Component(modules = [LocationServiceModule::class, DataManagerModule::class, FirebaseModule::class, WeatherServiceModule::class],

    dependencies = [ApplicationComponent::class])
interface LocationServiceComponent {
    fun inject(service:LocationService)
}