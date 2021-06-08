package pl.matyjasiakm.volunteermanager.di.module

import dagger.Module
import dagger.Provides
import pl.matyjasiakm.volunteermanager.mvp.LoginActivityContract
import pl.matyjasiakm.volunteermanager.mvp.LoginPresenterImpl

@Module
class LoginActivityMvpModule constructor(private val mView: LoginActivityContract.View) {
    @Provides
    fun provideView(): LoginActivityContract.View {
        return mView
    }

    @Provides
    fun providePresenter(presenter: LoginPresenterImpl): LoginActivityContract.Presenter = presenter
}