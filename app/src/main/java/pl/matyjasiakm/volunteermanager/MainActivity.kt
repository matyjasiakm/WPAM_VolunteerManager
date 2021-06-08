package pl.matyjasiakm.volunteermanager

import android.Manifest
import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.textview.MaterialTextView
import pl.matyjasiakm.volunteermanager.databinding.ActivityMainBinding
import pl.matyjasiakm.volunteermanager.di.component.DaggerMainActivityComponent
import pl.matyjasiakm.volunteermanager.di.module.MainActivityMvpModule
import pl.matyjasiakm.volunteermanager.mvp.MainActivityContract
import pl.matyjasiakm.volunteermanager.services.LocationService
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var presenter: MainActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerMainActivityComponent.builder()
            .applicationComponent((this.applicationContext as MyApp).getApplicationComponent())
            .mainActivityMvpModule(MainActivityMvpModule(this)).build().inject(this)



        setupDrawer()
        setupNavMenu()

        presenter.onActivityCreate()


        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS
            ),
            1
        )
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "1"
            val descriptionText = "Mychannel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setupDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setupNavMenu() {
        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.infoWall_nav_menuItem -> {
                    presenter.onDrawerOptionInfoWallClick()
                    true
                }
                R.id.volunteerList_nav_menuItem -> {
                    presenter.onDrawerOptionVolunteerListClick()
                    true
                }

                R.id.location_nav_menuItem -> {
                    presenter.onLocalizationOptionClick()
                    true
                }
                R.id.weather_nav_menuItem -> {
                    presenter.onWeatherOptionClick()
                    true
                }
                R.id.schedule_nav_menuItem -> {
                    presenter.onScheduleClick()
                    true
                }
                R.id.logout_nav_menuItem -> {
                    presenter.signOutClick()
                    true
                }
                else -> false
            }

        }
    }

    override fun setCoordinatorInfoHeader(name: String, surname: String, phoneNumber: String) {
        val header = binding.navView.getHeaderView(0)
        val tes = header.findViewById<MaterialTextView>(R.id.nav_name_textView)
        header.findViewById<MaterialTextView>(R.id.nav_name_textView).text = "$name $surname"
        header.findViewById<TextView>(R.id.nav_phone_textView).text = phoneNumber
        tes.invalidate()
    }

    override fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.contentFrame.id, fragment)
            .commit()
    }

    override fun closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun startNewActivity(intent: Intent) {
        startActivity(intent)
    }

    override fun finishActivity() {
        finish()
    }

    override fun getCont(): Context {
        return applicationContext
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {

            1 -> {
                if (grantResults.size > 0) {

                }

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

                startService(Intent(this, LocationService::class.java))

            }
        }
    }
}