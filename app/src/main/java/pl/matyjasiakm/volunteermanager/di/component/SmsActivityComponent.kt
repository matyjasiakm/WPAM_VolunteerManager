package pl.matyjasiakm.volunteermanager.di.component

import android.app.Activity
import android.content.BroadcastReceiver
import dagger.Component
import pl.matyjasiakm.volunteermanager.SmsActivity
import pl.matyjasiakm.volunteermanager.di.module.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [SmsActivityModule::class,DataManagerModule::class, FirebaseModule::class, WeatherServiceModule::class],
    dependencies = [ApplicationComponent::class]
)

interface SmsActivityComponent {
    fun inject(activity: SmsActivity)

}