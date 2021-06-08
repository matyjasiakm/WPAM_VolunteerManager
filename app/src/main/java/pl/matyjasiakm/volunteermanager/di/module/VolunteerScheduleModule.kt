package pl.matyjasiakm.volunteermanager.di.module

import dagger.Module
import dagger.Provides
import pl.matyjasiakm.volunteermanager.mvp.VolunteerSchedulerContract
import pl.matyjasiakm.volunteermanager.mvp.VolunteerSchedulerPresenterImpl

@Module
class VolunteerScheduleModule constructor(private val mView: VolunteerSchedulerContract.View){
    @Provides
    fun provideView(): VolunteerSchedulerContract.View {
        return mView
    }

    @Provides
    fun providePresenter(presenter: VolunteerSchedulerPresenterImpl): VolunteerSchedulerContract.Presenter =
        presenter
}