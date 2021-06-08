package pl.matyjasiakm.volunteermanager.di.module

import dagger.Module
import dagger.Provides
import pl.matyjasiakm.volunteermanager.mvp.MapsFragmentContract
import pl.matyjasiakm.volunteermanager.mvp.MapsFragmentPresenterImpl

@Module
class MapsFragmentModule constructor(private val mView: MapsFragmentContract.View) {
    @Provides
    fun provideView(): MapsFragmentContract.View {
        return mView;
    }

    @Provides
    fun providePresenter(presenter: MapsFragmentPresenterImpl): MapsFragmentContract.Presenter =
        presenter
}