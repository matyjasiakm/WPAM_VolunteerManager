package pl.matyjasiakm.volunteermanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.matyjasiakm.volunteermanager.data.dao.Duty
import pl.matyjasiakm.volunteermanager.data.dao.Volunteer
import pl.matyjasiakm.volunteermanager.di.component.DaggerScheduleFragmentComponent
import pl.matyjasiakm.volunteermanager.di.module.ScheduleFragmentModule
import pl.matyjasiakm.volunteermanager.mvp.ScheduleFragmentContract
import pl.matyjasiakm.volunteermanager.adapters.VolunteerListAdapter
import pl.matyjasiakm.volunteermanager.databinding.FragmentScheduleBinding
import javax.inject.Inject

class ScheduleFragment : Fragment(), ScheduleFragmentContract.View {

    lateinit var binding: FragmentScheduleBinding

    @Inject
    lateinit var presenter: ScheduleFragmentContract.Presenter

    val adapter = VolunteerListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerScheduleFragmentComponent.builder()
            .applicationComponent((activity?.applicationContext as MyApp).getApplicationComponent())
            .scheduleFragmentModule(
                ScheduleFragmentModule(this)
            ).build().inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        binding.scheduleRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ScheduleFragment.context)
            this.adapter = this@ScheduleFragment.adapter
        }

        adapter.onCallBtnClick = object : VolunteerListAdapter.ICall {
            override fun onClick(phone: String) {
                presenter.onCallClick(phone)
            }
        }

        adapter.onSmsBtnClick = object : VolunteerListAdapter.ISms {
            override fun onClick(phone: String) {
                presenter.onSmsClick(phone)
            }
        }
        adapter.onDutyClick = object :
            VolunteerListAdapter.IDuty {
            override fun onClick(email: String) {
                presenter.onDutyClick(email)
            }

        }

        presenter.onCreateView()
        return binding.root
    }

    override fun adapterUpdate(volunteerList: List<Volunteer>, dutiesList: List<Duty>) {
        adapter.updateAdapter(volunteerList, dutiesList)
    }

    override fun startIntent(intent: Intent) {
        startActivity(intent)
    }

    override fun getActivityContext(): Context {
        return context!!
    }

    override fun onResume() {
        super.onResume()
        presenter.onCreateView()
    }


}