package pl.matyjasiakm.volunteermanager.mvp

import android.content.Context
import android.content.Intent
import pl.matyjasiakm.volunteermanager.data.dao.Duty
import pl.matyjasiakm.volunteermanager.data.dao.Volunteer

interface ScheduleFragmentContract {
    interface Presenter {
        fun onCreateView()
        fun onSmsClick(phone: String)
        fun onCallClick(phone: String)
        fun onDutyClick(email:String)
    }

    interface View {
        fun adapterUpdate(volunteerList: List<Volunteer>, dutiesList: List<Duty>)
        fun startIntent(intent : Intent)
        fun getActivityContext(): Context
    }
}