package pl.matyjasiakm.volunteermanager.di.module

import dagger.Module
import dagger.Provides
import pl.matyjasiakm.volunteermanager.mvp.MainActivityContract
import pl.matyjasiakm.volunteermanager.mvp.MainActivityPresenterImpl

@Module
class MainActivityMvpModule constructor(private val mView: MainActivityContract.View) {
    @Provides
    fun provideView(): MainActivityContract.View {
        return mView
    }

    @Provides
    fun providePresenter(presenter: MainActivityPresenterImpl): MainActivityContract.Presenter = presenter
}