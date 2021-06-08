package pl.matyjasiakm.volunteermanager.mvp

import android.content.Intent
import android.net.Uri
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.matyjasiakm.volunteermanager.SmsActivity
import pl.matyjasiakm.volunteermanager.VolunteerScheduleActivity
import pl.matyjasiakm.volunteermanager.data.dao.Duty
import pl.matyjasiakm.volunteermanager.data.IDataManager
import pl.matyjasiakm.volunteermanager.data.dao.OwnDateTime
import pl.matyjasiakm.volunteermanager.data.dao.Volunteer
import java.util.*
import javax.inject.Inject

class ScheduleFragmentPresenterImpl @Inject constructor(
    val database: IDataManager,
    val mView: ScheduleFragmentContract.View
) : ScheduleFragmentContract.Presenter {
    lateinit var volunteers: List<Volunteer>
    lateinit var duties: List<Duty>

    fun updateAdapter() {
        val calendar = Calendar.getInstance()
        val queryDate = OwnDateTime(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE)
        )

        database.getAllVolunteers().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).flatMap {
                volunteers = it
                database.getDutiesFromDate(queryDate)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                duties = t
                mView.adapterUpdate(volunteers, duties)
            }
    }

    override fun onCreateView() {
        updateAdapter()
    }

    override fun onSmsClick(phone: String) {
        val intent = Intent(mView.getActivityContext(), SmsActivity::class.java).apply {
            putExtra("phone", phone)
        }
        mView.startIntent(intent)
    }

    override fun onCallClick(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        mView.startIntent(intent)
    }

    override fun onDutyClick(email: String) {
        val intent =
            Intent(mView.getActivityContext(), VolunteerScheduleActivity::class.java).apply {
                putExtra("email", email)
            }
        mView.startIntent(intent)
    }

}