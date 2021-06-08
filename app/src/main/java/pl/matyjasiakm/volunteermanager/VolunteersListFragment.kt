package pl.matyjasiakm.volunteermanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import pl.matyjasiakm.volunteermanager.data.dao.Volunteer
import pl.matyjasiakm.volunteermanager.databinding.FragmentVolunteersListBinding
import pl.matyjasiakm.volunteermanager.di.component.DaggerVolunteerListFragmentComponent
import pl.matyjasiakm.volunteermanager.di.module.VolunteerListFragmentModule
import pl.matyjasiakm.volunteermanager.adapters.VolunteerListAdapter
import pl.matyjasiakm.volunteermanager.mvp.VolunteerListFragmentContract
import javax.inject.Inject


class VolunteersListFragment() : Fragment(), VolunteerListFragmentContract.View {

    @Inject
    lateinit var presenter: VolunteerListFragmentContract.Presenter

    private var _binding: FragmentVolunteersListBinding? = null
    private val binding get() = _binding!!

    val adapter = VolunteerListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentVolunteersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        DaggerVolunteerListFragmentComponent.builder()
            .applicationComponent((activity?.applicationContext as MyApp).getApplicationComponent())
            .volunteerListFragmentModule(VolunteerListFragmentModule(this))
            .build().inject(this)

        binding.volunteerListSwipeRefreshLayout.setOnRefreshListener {
            presenter.onSwipe()
        }

        binding.volunteerListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@VolunteersListFragment.context)
            adapter = this@VolunteersListFragment.adapter

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
        adapter.onDutyClick=object :
            VolunteerListAdapter.IDuty{
            override fun onClick(email: String) {
                presenter.onDutyClick(email)
            }

        }
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun updateRecyclerView(volunteers: List<Volunteer>) {
        adapter.updateAdapter(volunteers)
    }

    override fun setSwipeRefreshing(refresh: Boolean) {
        binding.volunteerListSwipeRefreshLayout.isRefreshing = refresh
    }

    override fun startIntent(intent: Intent) {
        startActivity(intent)
    }

    override fun getActivityContext(): Context {
        return context!!
    }


}