package pl.matyjasiakm.volunteermanager.di.module

import dagger.Module
import dagger.Provides
import pl.matyjasiakm.volunteermanager.mvp.InfoWallFragmentContract
import pl.matyjasiakm.volunteermanager.mvp.InfoWallFragmentPresenterImpl

@Module
class InfoWallFragmentModule constructor(private val mView: InfoWallFragmentContract.View) {
    @Provides
    fun provideView(): InfoWallFragmentContract.View {
        return mView
    }

    @Provides
    fun providePresenter(presenter: InfoWallFragmentPresenterImpl): InfoWallFragmentContract.Presenter =
        presenter
}