package pl.matyjasiakm.volunteermanager.di.component

import dagger.Component
import pl.matyjasiakm.volunteermanager.WeatherFragment
import pl.matyjasiakm.volunteermanager.di.module.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [WeatherFragmentModule::class, DataManagerModule::class, FirebaseModule::class, WeatherServiceModule::class],
    dependencies = [ApplicationComponent::class]
)
interface WeatherFragmentComponent {
    fun inject(fragment: WeatherFragment)
}