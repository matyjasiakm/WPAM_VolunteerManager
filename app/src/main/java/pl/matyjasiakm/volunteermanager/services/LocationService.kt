package pl.matyjasiakm.volunteermanager.services

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import androidx.core.app.ActivityCompat
import pl.matyjasiakm.volunteermanager.MyApp
import pl.matyjasiakm.volunteermanager.di.component.DaggerLocationServiceComponent
import pl.matyjasiakm.volunteermanager.di.component.DaggerLoginActivityComponent
import pl.matyjasiakm.volunteermanager.di.module.LocationServiceModule
import pl.matyjasiakm.volunteermanager.di.module.LoginActivityMvpModule
import javax.inject.Inject

class LocationService : Service(), LocationServiceContract.Service {
    @Inject
    lateinit var presenter:LocationServiceContract.Presenter

    var mLocationManager: LocationManager? = null

    inner class MyLocationListener(provider: String) : LocationListener {
        var location: Location? = null;

        init {
            location = Location(provider)
        }

        override fun onLocationChanged(location: Location?) {
            this.location?.set(location)

            location?.let{
                presenter.updateMyLocation(it.latitude,it.longitude)
            }

        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }

    }

    val locationListener = arrayListOf<LocationListener>(
        MyLocationListener(LocationManager.GPS_PROVIDER),
        MyLocationListener(LocationManager.NETWORK_PROVIDER)
    )

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }
    override fun onCreate() {
        super.onCreate()
        if (mLocationManager == null)
            mLocationManager =
                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }

        DaggerLocationServiceComponent.builder().applicationComponent((this.applicationContext as MyApp).getApplicationComponent()).locationServiceModule(
            LocationServiceModule(this)).build().inject(this)

        mLocationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 1000, 10f,
            locationListener[0]
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        mLocationManager?.removeUpdates(locationListener[0])
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        mLocationManager?.removeUpdates(locationListener[0])
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}