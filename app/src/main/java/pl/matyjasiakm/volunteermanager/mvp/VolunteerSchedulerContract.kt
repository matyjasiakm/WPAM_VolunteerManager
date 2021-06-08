package pl.matyjasiakm.volunteermanager.mvp

import android.os.Bundle
import pl.matyjasiakm.volunteermanager.data.dao.Duty
import java.util.*

interface VolunteerSchedulerContract {
    interface View {
        fun setName(name: String)
        fun initializeButtons(calendarStart: Calendar, calendarEnd: Calendar)
        fun updateRecyclerView(list: List<Duty>)
        fun setFloatButtonVisible(visible: Boolean)
        fun setAddFormVisible(visible: Boolean)

    }

    interface Presenter {
        fun onStart(extra: Bundle?)
        fun getcalendarStart(): Calendar
        fun getcalendarEnd(): Calendar
        fun onAddClick()
        fun onCancelClick()
        fun onFloatButtonClick()
        fun onDestroy()
        fun onDelClick(id: String)
    }
}