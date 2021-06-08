package pl.matyjasiakm.volunteermanager.di.module

import dagger.Module
import dagger.Provides
import pl.matyjasiakm.volunteermanager.mvp.VolunteerListFragmentContract
import pl.matyjasiakm.volunteermanager.mvp.VolunteerListFragmentPresenterImpl
import pl.matyjasiakm.volunteermanager.mvp.WeatherFragmentContract
import pl.matyjasiakm.volunteermanager.mvp.WeatherFragmentPresenterImpl


@Module
class WeatherFragmentModule constructor(private val mView: WeatherFragmentContract.View){
    @Provides
    fun provideView():WeatherFragmentContract.View{
        return mView
    }

    @Provides
    fun providePresenter(presenter: WeatherFragmentPresenterImpl): WeatherFragmentContract.Presenter =
        presenter
}