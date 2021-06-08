package pl.matyjasiakm.volunteermanager.mvp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import org.w3c.dom.DocumentFragment

interface MainActivityContract {
    interface View {
        fun setCoordinatorInfoHeader(name: String, surname: String, phoneNumber: String)
        fun setFragment(fragment: Fragment)
        fun closeDrawer()
        fun startNewActivity(intent: Intent)
        fun finishActivity()
        fun getCont(): Context
    }

    interface Presenter {
        fun onActivityCreate()
        fun onDrawerOptionInfoWallClick()
        fun onDrawerOptionVolunteerListClick()
        fun onLocalizationOptionClick()
        fun onWeatherOptionClick()
        fun onScheduleClick()
        fun onCreate()
        fun signOutClick()

    }
}