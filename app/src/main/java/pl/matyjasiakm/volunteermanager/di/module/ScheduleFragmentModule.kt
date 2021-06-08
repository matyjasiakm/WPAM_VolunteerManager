package pl.matyjasiakm.volunteermanager.di.module

import dagger.Module
import dagger.Provides
import pl.matyjasiakm.volunteermanager.mvp.MapsFragmentContract
import pl.matyjasiakm.volunteermanager.mvp.MapsFragmentPresenterImpl
import pl.matyjasiakm.volunteermanager.mvp.ScheduleFragmentContract
import pl.matyjasiakm.volunteermanager.mvp.ScheduleFragmentPresenterImpl

@Module
class ScheduleFragmentModule constructor(val mView: ScheduleFragmentContract.View) {
    @Provides
    fun provideView(): ScheduleFragmentContract.View {
        return mView;
    }

    @Provides
    fun providePresenter(presenter: ScheduleFragmentPresenterImpl): ScheduleFragmentContract.Presenter =
        presenter
}