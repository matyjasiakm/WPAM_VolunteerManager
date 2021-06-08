package pl.matyjasiakm.volunteermanager.di.component

import dagger.Component
import pl.matyjasiakm.volunteermanager.MapsFragment
import pl.matyjasiakm.volunteermanager.di.module.DataManagerModule
import pl.matyjasiakm.volunteermanager.di.module.FirebaseModule
import pl.matyjasiakm.volunteermanager.di.module.MapsFragmentModule
import pl.matyjasiakm.volunteermanager.di.module.WeatherServiceModule

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Component(modules = [MapsFragmentModule::class, DataManagerModule::class, FirebaseModule::class, WeatherServiceModule::class],

    dependencies = [ApplicationComponent::class])
interface MapsFragmentComponent {
    fun inject(fragment:MapsFragment)
}