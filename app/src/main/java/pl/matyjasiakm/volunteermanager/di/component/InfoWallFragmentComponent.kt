package pl.matyjasiakm.volunteermanager.di.component

import dagger.Component
import pl.matyjasiakm.volunteermanager.InfoWallFragment
import pl.matyjasiakm.volunteermanager.di.module.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [InfoWallFragmentModule::class, DataManagerModule::class, FirebaseModule::class, WeatherServiceModule::class],
    dependencies = [ApplicationComponent::class]
)
interface InfoWallFragmentComponent {
    fun inject(fragment: InfoWallFragment)
}