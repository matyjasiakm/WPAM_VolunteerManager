package pl.matyjasiakm.volunteermanager

import android.app.Application
import pl.matyjasiakm.volunteermanager.di.component.ApplicationComponent
import pl.matyjasiakm.volunteermanager.di.component.DaggerApplicationComponent

class MyApp : Application() {

    lateinit var appComp: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        appComp = DaggerApplicationComponent.create()
        appComp.inject(this)
    }

    fun getApplicationComponent() = appComp
}