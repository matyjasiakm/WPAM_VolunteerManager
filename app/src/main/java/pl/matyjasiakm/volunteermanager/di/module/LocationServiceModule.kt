package pl.matyjasiakm.volunteermanager.di.module

import android.app.Service
import dagger.Module
import dagger.Provides
import pl.matyjasiakm.volunteermanager.services.LocationServiceContract
import pl.matyjasiakm.volunteermanager.services.LocationServiceImpl

@Module
class LocationServiceModule (val mService: LocationServiceContract.Service){
    @Provides
    fun provideService(): LocationServiceContract.Service{
        return mService
    }

    @Provides
    fun providePresenter(presenter: LocationServiceImpl): LocationServiceContract.Presenter =
        presenter
}