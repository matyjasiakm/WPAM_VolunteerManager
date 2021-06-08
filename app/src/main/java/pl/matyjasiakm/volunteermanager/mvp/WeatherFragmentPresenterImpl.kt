package pl.matyjasiakm.volunteermanager.mvp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.matyjasiakm.volunteermanager.BuildConfig
import pl.matyjasiakm.volunteermanager.data.IDataManager
import pl.matyjasiakm.volunteermanager.services.LocationService
import javax.inject.Inject

class WeatherFragmentPresenterImpl @Inject constructor(
    private val db: IDataManager,
    private val view: WeatherFragmentContract.View
) : WeatherFragmentContract.Presenter {

    private val disposables = CompositeDisposable()
    private var context: Context? = null;
    var location: String = ""
    var permission: Boolean = false;
    fun updateWeather() {
        if (location.isNotEmpty()) {
            disposables.add(
                db.getLocationKey(BuildConfig.apiKey, location).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).flatMapObservable { t ->
                        db.get5DayForecast(t.Key, BuildConfig.apiKey).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())

                    }.subscribe(
                        { t ->
                            view.updateAdapter(t)
                        },
                        { e -> print(e) }
                    ))

        }
    }

    override fun onStart(context: Context) {
        this.context = context
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permission = false
            return
        } else {
            permission = true
            LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener { t ->
                location = "${t.latitude},${t.longitude}"
                updateWeather()
            }
        }

    }

    override fun onDestroy() {
        disposables.dispose()
    }

}