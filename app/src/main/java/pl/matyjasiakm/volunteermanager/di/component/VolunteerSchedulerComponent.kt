package pl.matyjasiakm.volunteermanager.di.component

import dagger.Component
import pl.matyjasiakm.volunteermanager.VolunteerScheduleActivity
import pl.matyjasiakm.volunteermanager.di.module.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [VolunteerScheduleModule::class, DataManagerModule::class, FirebaseModule::class, WeatherServiceModule::class],
    dependencies = [ApplicationComponent::class]
)
interface VolunteerSchedulerComponent {

    fun inject(activity: VolunteerScheduleActivity)
}