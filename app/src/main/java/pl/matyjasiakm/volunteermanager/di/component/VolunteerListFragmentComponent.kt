package pl.matyjasiakm.volunteermanager.di.component

import dagger.Component
import pl.matyjasiakm.volunteermanager.VolunteersListFragment
import pl.matyjasiakm.volunteermanager.di.module.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [VolunteerListFragmentModule::class, DataManagerModule::class, FirebaseModule::class, WeatherServiceModule::class],
    dependencies = [ApplicationComponent::class]
)
interface VolunteerListFragmentComponent {
    fun inject(fragment: VolunteersListFragment)
}