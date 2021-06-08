package pl.matyjasiakm.volunteermanager.mvp

import android.os.Bundle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.matyjasiakm.volunteermanager.data.dao.Duty
import pl.matyjasiakm.volunteermanager.data.IDataManager
import pl.matyjasiakm.volunteermanager.data.dao.OwnDateTime
import pl.matyjasiakm.volunteermanager.data.dao.Volunteer
import java.util.*
import javax.inject.Inject

class VolunteerSchedulerPresenterImpl @Inject constructor(
    val db: IDataManager,
    val view: VolunteerSchedulerContract.View
) : VolunteerSchedulerContract.Presenter {
    val disposables = CompositeDisposable()
    var volunteer: Volunteer? = null
    val calendarStart = Calendar.getInstance()
    val calendarEnd = Calendar.getInstance()

    override fun onStart(extra: Bundle?) {
        extra?.let {
            val email = it.getString("email")
            if (!email.isNullOrEmpty()) {
                disposables.add(db.getVolunteerWithEmail(email).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).flatMap { t ->
                        volunteer = t
                        view.setName(t.FullName)

                        db.getDutiesFromVolunteer(t.Email).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                    }.subscribe { t ->
                        view.updateRecyclerView(t)
                    })
            }
        }

        view.initializeButtons(calendarStart, calendarEnd)

    }

    private fun getDuties() {
        volunteer?.let {
            disposables.add(db.getDutiesFromVolunteer(it.Email).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe { t ->
                    view.updateRecyclerView(t)
                })
        }

    }

    override fun getcalendarStart(): Calendar {
        return calendarStart
    }

    override fun getcalendarEnd(): Calendar {
        return calendarEnd
    }

    override fun onAddClick() {
        val dateStart = OwnDateTime(
            calendarStart.get(Calendar.YEAR),
            calendarStart.get(Calendar.MONTH) + 1,
            calendarStart.get(Calendar.DAY_OF_MONTH),
            calendarStart.get(Calendar.HOUR_OF_DAY),
            calendarStart.get(Calendar.MINUTE)
        )
        val dateEnd = OwnDateTime(
            calendarEnd.get(Calendar.YEAR),
            calendarEnd.get(Calendar.MONTH) + 1,
            calendarEnd.get(Calendar.DAY_OF_MONTH),
            calendarEnd.get(Calendar.HOUR_OF_DAY),
            calendarEnd.get(Calendar.MINUTE)
        )
        val duty = Duty(
            "",
            volunteer!!.Email,
            "Duty",
            dateStart,
            dateEnd
        )
        db.addDuty(duty).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {
                getDuties()
                view.setFloatButtonVisible(true)
                view.setAddFormVisible(false)
            }



    }

    override fun onCancelClick() {
        view.setFloatButtonVisible(true)
        view.setAddFormVisible(false)
    }

    override fun onFloatButtonClick() {
        view.setFloatButtonVisible(false)
        view.setAddFormVisible(true)
    }

    override fun onDestroy() {
        disposables.dispose()
    }

    override fun onDelClick(id: String) {
        disposables.add(
            db.deleteDutyWithId(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    getDuties()
                }
        )
    }

}