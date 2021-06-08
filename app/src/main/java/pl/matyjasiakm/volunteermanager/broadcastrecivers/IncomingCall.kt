package pl.matyjasiakm.volunteermanager.broadcastrecivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.telephony.PhoneStateListener
import android.widget.Toast
import android.telephony.TelephonyManager
import android.view.Gravity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.matyjasiakm.volunteermanager.data.DataManagerImpl_Factory
import pl.matyjasiakm.volunteermanager.data.IDataManager
import pl.matyjasiakm.volunteermanager.data.authentication.FirebaseAuthData
import pl.matyjasiakm.volunteermanager.data.database.FirebaseFirestoreRepo
import pl.matyjasiakm.volunteermanager.di.module.WeatherServiceModule
import pl.matyjasiakm.volunteermanager.di.module.WeatherServiceModule_ProvideServiceFactory


class IncomingCall : BroadcastReceiver() {

    var db: IDataManager = DataManagerImpl_Factory.newInstance(
        FirebaseAuthData(
            FirebaseAuth.getInstance()
        ),
        FirebaseFirestoreRepo(FirebaseFirestore.getInstance()),
        WeatherServiceModule_ProvideServiceFactory.provideService(
            WeatherServiceModule()
        )
    )

    override fun onReceive(context: Context?, intent: Intent?) {
        val tmgr = (context?.getSystemService(Context.TELEPHONY_SERVICE)) as (TelephonyManager)
        tmgr.listen(MyPhoneStateListener(context), PhoneStateListener.LISTEN_CALL_STATE)


    }

    inner class MyPhoneStateListener(var context: Context) : PhoneStateListener() {
        var displaying = true
        override fun onCallStateChanged(state: Int, number: String) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                var nr=if(number.get(0)=='+')number.substring(3, 12) else number;
                db.getVolunteerFromPhone(nr).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe { t ->
                        if(t.Email.isEmpty())
                            return@subscribe
                        val handler = Handler()
                        val runable = object : Runnable {
                            override fun run() {
                                val toast = Toast.makeText(
                                    context,
                                    "Call: ${t.FullName}",
                                    Toast.LENGTH_SHORT
                                )
                                toast.setGravity(
                                    Gravity.BOTTOM, 0, 0
                                )
                                toast.show()

                                if (displaying)
                                    handler.postDelayed(this, 2500)
                            }
                        }
                        handler.post(runable)
                    }

            } else {
                displaying = false
            }
        }
    }

}