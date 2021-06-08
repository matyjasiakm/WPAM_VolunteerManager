package pl.matyjasiakm.volunteermanager.di.component

import dagger.Component
import pl.matyjasiakm.volunteermanager.LoginActivity
import pl.matyjasiakm.volunteermanager.MainActivity
import pl.matyjasiakm.volunteermanager.di.module.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DataManagerModule::class, LoginActivityMvpModule::class, FirebaseModule::class, WeatherServiceModule::class],
    dependencies = [ApplicationComponent::class]
)
interface LoginActivityComponent {
    fun inject(activity: LoginActivity)
}