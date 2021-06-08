package pl.matyjasiakm.volunteermanager.mvp

import com.google.android.gms.maps.model.Marker
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.matyjasiakm.volunteermanager.data.IDataManager
import javax.inject.Inject

class MapsFragmentPresenterImpl @Inject constructor(
    val db: IDataManager,
    val view: MapsFragmentContract.View
) : MapsFragmentContract.Presenter {
    private val markerList = mutableListOf<Marker>()
    private val disposables = CompositeDisposable()
    private fun removeMarkers() {
        val iterator = markerList.iterator()
        while (iterator.hasNext()) {
            val marker = iterator.next()
            marker.remove()
            iterator.remove()
        }
    }

    override fun update() {
        removeMarkers()

        disposables.addAll(db.getSignedUserEmail().flatMap { email ->
            db.getCoordinatorWithEmail(email)
        }.subscribe { coordinator ->
            coordinator.location?.let {
                view.updateLocationOnMap(it)
            }
        },
            db.getAllCoordinators().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe { coordinatorsList ->
                    view.updateMarksOnMap(coordinatorsList)
                })
    }

    override fun addMarker(marker: Marker) {
        markerList.add(marker)
        marker.showInfoWindow()
    }

    override fun onUpdateBtnClick() {
        update()
    }

    override fun onDestroy() {
        disposables.dispose()
    }

}