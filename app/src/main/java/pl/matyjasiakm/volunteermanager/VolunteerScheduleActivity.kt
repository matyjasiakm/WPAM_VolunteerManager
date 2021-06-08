package pl.matyjasiakm.volunteermanager

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import pl.matyjasiakm.volunteermanager.data.dao.Duty
import pl.matyjasiakm.volunteermanager.di.component.DaggerVolunteerSchedulerComponent
import pl.matyjasiakm.volunteermanager.di.module.VolunteerScheduleModule
import pl.matyjasiakm.volunteermanager.adapters.DutyAdapter
import pl.matyjasiakm.volunteermanager.databinding.ActivityVolunteerScheduleBinding
import pl.matyjasiakm.volunteermanager.mvp.VolunteerSchedulerContract
import pl.matyjasiakm.volunteermanager.utils.toStringDate
import pl.matyjasiakm.volunteermanager.utils.toStringTime
import java.util.*
import javax.inject.Inject

class VolunteerScheduleActivity : AppCompatActivity(), VolunteerSchedulerContract.View {
    @Inject
    lateinit var presenter: VolunteerSchedulerContract.Presenter

    private var _binding: ActivityVolunteerScheduleBinding? = null
    private val binding get() = _binding!!

    val adapter = DutyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_volunteer_schedule)

        DaggerVolunteerSchedulerComponent.builder()
            .applicationComponent((applicationContext as MyApp).getApplicationComponent())
            .volunteerScheduleModule(
                VolunteerScheduleModule(this)
            ).build().inject(this)

        _binding = ActivityVolunteerScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dutyRecyclerView.apply {

            layoutManager = LinearLayoutManager(this@VolunteerScheduleActivity.applicationContext)
            adapter = this@VolunteerScheduleActivity.adapter

        }
        binding.addDutyFloatButton.setOnClickListener {
            presenter.onFloatButtonClick()
        }
        binding.test.cancelDutyFormButton.setOnClickListener {
            presenter.onCancelClick()
        }
        adapter.onDel = object : DutyAdapter.IDel {
            override fun onClick(id: String) {
                presenter.onDelClick(id)
            }

        }
        presenter.onStart(intent.extras)

    }

    override fun setName(name: String) {
        binding.dutyVolunterName.text = name;
    }

    private val datePickerListener = { view: View ->
        val calendar =
            if (view.id == R.id.start_date_button) presenter.getcalendarStart() else presenter.getcalendarEnd()

        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                (view as MaterialButton).text =
                    toStringDate(
                        dayOfMonth,
                        month,
                        year
                    )
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private val timePickerListener = { view: View ->
        val calendar =
            if (view.id == R.id.start_time_button) presenter.getcalendarStart() else presenter.getcalendarEnd()
        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                (view as MaterialButton).text =
                    toStringTime(
                        hourOfDay,
                        minute
                    )

            }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true
        ).show()

    }

    override fun initializeButtons(calendarStart: Calendar, calendarEnd: Calendar) {

        binding.test.startDateButton.text = calendarStart.toStringDate()
        binding.test.startDateButton.setOnClickListener(datePickerListener)

        binding.test.endDateButton.text = calendarEnd.toStringDate()
        binding.test.endDateButton.setOnClickListener(datePickerListener)

        binding.test.startTimeButton.text = calendarStart.toStringTime()
        binding.test.startTimeButton.setOnClickListener(timePickerListener)

        binding.test.endTimeButton.text = calendarEnd.toStringTime()
        binding.test.endTimeButton.setOnClickListener(timePickerListener)

        binding.test.addDutyFormButton.setOnClickListener {
            presenter.onAddClick()
        }
    }

    override fun updateRecyclerView(list: List<Duty>) {
        adapter.notifyDataSetChange(list)
    }

    override fun setFloatButtonVisible(visible: Boolean) {
        binding.addDutyFloatButton.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun setAddFormVisible(visible: Boolean) {
        binding.test.addDutyCardView.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}