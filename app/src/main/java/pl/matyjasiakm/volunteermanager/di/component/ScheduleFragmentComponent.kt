package pl.matyjasiakm.volunteermanager.di.component

import dagger.Component
import pl.matyjasiakm.volunteermanager.MapsFragment
import pl.matyjasiakm.volunteermanager.ScheduleFragment
import pl.matyjasiakm.volunteermanager.di.module.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ScheduleFragmentModule::class, DataManagerModule::class, FirebaseModule::class, WeatherServiceModule::class],
    dependencies = [ApplicationComponent::class]
)
interface ScheduleFragmentComponent {
    fun inject(fragment: ScheduleFragment)
}