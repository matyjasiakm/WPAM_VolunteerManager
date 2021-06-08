package pl.matyjasiakm.volunteermanager.di.component

import android.content.BroadcastReceiver
import dagger.Component
import pl.matyjasiakm.volunteermanager.MainActivity
import pl.matyjasiakm.volunteermanager.di.module.DataManagerModule
import pl.matyjasiakm.volunteermanager.di.module.FirebaseModule
import pl.matyjasiakm.volunteermanager.di.module.MainActivityMvpModule
import pl.matyjasiakm.volunteermanager.di.module.WeatherServiceModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [MainActivityMvpModule::class, DataManagerModule::class, FirebaseModule::class, WeatherServiceModule::class],
    dependencies = [ApplicationComponent::class]
)
interface MainActivityComponent {
    fun inject(activity: MainActivity)

}