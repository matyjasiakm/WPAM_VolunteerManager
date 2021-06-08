package pl.matyjasiakm.volunteermanager.di.module

import dagger.Module
import dagger.Provides
import pl.matyjasiakm.volunteermanager.mvp.InfoWallFragmentContract
import pl.matyjasiakm.volunteermanager.mvp.InfoWallFragmentPresenterImpl
import pl.matyjasiakm.volunteermanager.mvp.VolunteerListFragmentContract
import pl.matyjasiakm.volunteermanager.mvp.VolunteerListFragmentPresenterImpl

@Module
class VolunteerListFragmentModule constructor(private val mView: VolunteerListFragmentContract.View){
    @Provides
    fun provideView(): VolunteerListFragmentContract.View {
        return mView
    }

    @Provides
    fun providePresenter(presenter: VolunteerListFragmentPresenterImpl): VolunteerListFragmentContract.Presenter =
        presenter
}