package pl.matyjasiakm.volunteermanager.di.component

import android.content.Context
import dagger.Component
import pl.matyjasiakm.volunteermanager.MyApp

@Component
interface ApplicationComponent {
    fun inject(myApp: MyApp)
}