package pl.matyjasiakm.volunteermanager.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.matyjasiakm.volunteermanager.data.DataManagerImpl
import pl.matyjasiakm.volunteermanager.data.IDataManager
import pl.matyjasiakm.volunteermanager.mvp.MainActivityContract
import pl.matyjasiakm.volunteermanager.mvp.MainActivityPresenterImpl
import pl.matyjasiakm.volunteermanager.mvp.SmsActivityContract
import pl.matyjasiakm.volunteermanager.mvp.SmsActivityPresenterImpl
import javax.inject.Singleton

@Module
class SmsActivityModule(val mView: SmsActivityContract.View) {
    @Provides
    fun provideView(): SmsActivityContract.View {
        return mView
    }

    @Provides
    fun providePresenter(
        presenter: SmsActivityPresenterImpl
    ): SmsActivityContract.Presenter = presenter

}