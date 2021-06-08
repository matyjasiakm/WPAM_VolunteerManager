package pl.matyjasiakm.volunteermanager.services

import android.os.Looper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.matyjasiakm.volunteermanager.data.IDataManager
import pl.matyjasiakm.volunteermanager.data.dao.MyLatLng
import javax.inject.Inject

class LocationServiceImpl @Inject constructor(
    val db: IDataManager,
    val service: LocationServiceContract.Service
) : LocationServiceContract.Presenter {
    override fun updateMyLocation(lat: Double, lng: Double) {

        db.getSignedUserEmail().observeOn(AndroidSchedulers.from(Looper.myLooper()))
            .subscribeOn(Schedulers.io()).flatMapCompletable { email ->
            db.updateMyLocation(email,
                MyLatLng(lat, lng)
            )
                .observeOn(AndroidSchedulers.from(Looper.myLooper())).subscribeOn(Schedulers.io())
        }.subscribe()
    }

}