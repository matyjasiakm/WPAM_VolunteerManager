package pl.matyjasiakm.volunteermanager.mvp

import android.content.Context
import android.content.Intent
import pl.matyjasiakm.volunteermanager.data.dao.Volunteer

interface VolunteerListFragmentContract {
    interface View {
        fun updateRecyclerView(volunteers: List<Volunteer>)
        fun setSwipeRefreshing(refresh: Boolean)
        fun startIntent(intent : Intent)
        fun getActivityContext():Context
    }

    interface Presenter {
        fun onStart()
        fun onSwipe()
        fun onSmsClick(phone: String)
        fun onCallClick(phone: String)
        fun onDutyClick(email:String)
    }
}