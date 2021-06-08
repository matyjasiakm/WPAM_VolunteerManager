package pl.matyjasiakm.volunteermanager.mvp

import android.content.Intent
import android.net.Uri
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.matyjasiakm.volunteermanager.SmsActivity
import pl.matyjasiakm.volunteermanager.VolunteerScheduleActivity
import pl.matyjasiakm.volunteermanager.data.IDataManager
import javax.inject.Inject

class VolunteerListFragmentPresenterImpl @Inject constructor(
    private val db: IDataManager,
    private val view: VolunteerListFragmentContract.View
) : VolunteerListFragmentContract.Presenter {
    private val disposables = CompositeDisposable()

    override fun onStart() {
        getVolunnters()
    }

    override fun onSwipe() {
        getVolunnters()
    }

    override fun onSmsClick(phone: String) {
        val intent = Intent(view.getActivityContext(), SmsActivity::class.java).apply {
            putExtra("phone", phone)
        }
        view.startIntent(intent)
    }

    override fun onCallClick(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        view.startIntent(intent)
    }

    override fun onDutyClick(email: String) {
        val intent =
            Intent(view.getActivityContext(), VolunteerScheduleActivity::class.java).apply {
                putExtra("email", email)
            }

        view.startIntent(intent)
    }

    private fun getVolunnters() {

        disposables.add(db.getAllVolunteers().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                view.updateRecyclerView(list)
                view.setSwipeRefreshing(false)
            })
    }

}