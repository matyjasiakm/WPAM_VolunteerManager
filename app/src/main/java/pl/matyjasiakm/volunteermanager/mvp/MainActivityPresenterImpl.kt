package pl.matyjasiakm.volunteermanager.mvp

import android.content.Intent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.matyjasiakm.volunteermanager.*
import pl.matyjasiakm.volunteermanager.data.IDataManager
import javax.inject.Inject

class MainActivityPresenterImpl @Inject constructor(
    val db: IDataManager,
    val view: MainActivityContract.View
) : MainActivityContract.Presenter {

    override fun onActivityCreate() {
        db.getSignedUserEmail().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).flatMap { email ->
                db.getCoordinatorWithEmail(email).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }.subscribe { coordinator ->
                view.setCoordinatorInfoHeader(
                    coordinator.name,
                    coordinator.surname,
                    coordinator.phoneNumber
                )
            }
        view.setFragment(InfoWallFragment())
        view.closeDrawer()


    }


    override fun onDrawerOptionInfoWallClick() {
        view.setFragment(InfoWallFragment())
        view.closeDrawer()
    }

    override fun onDrawerOptionVolunteerListClick() {
        view.setFragment(VolunteersListFragment())
        view.closeDrawer()
    }

    override fun onLocalizationOptionClick() {
        view.setFragment(MapsFragment())
        view.closeDrawer()
    }


    override fun onWeatherOptionClick() {
        view.setFragment(WeatherFragment())
        view.closeDrawer()
    }

    override fun onScheduleClick() {
        view.setFragment(ScheduleFragment())
        view.closeDrawer()
    }

    override fun onCreate() {
        view.setFragment(InfoWallFragment())
        view.closeDrawer()
    }

    override fun signOutClick() {
        db.logOutUser()
        view.startNewActivity(Intent(view.getCont(), LoginActivity::class.java))
        view.finishActivity()
    }

}