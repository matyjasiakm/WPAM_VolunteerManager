package pl.matyjasiakm.volunteermanager

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import pl.matyjasiakm.volunteermanager.data.dao.CoordinatorMessage
import pl.matyjasiakm.volunteermanager.data.dao.Level
import pl.matyjasiakm.volunteermanager.databinding.FragmentInfoWallBinding
import pl.matyjasiakm.volunteermanager.di.component.DaggerInfoWallFragmentComponent
import pl.matyjasiakm.volunteermanager.di.module.InfoWallFragmentModule
import pl.matyjasiakm.volunteermanager.adapters.CoordinatorMessagesAdapter
import pl.matyjasiakm.volunteermanager.mvp.InfoWallFragmentContract
import javax.inject.Inject

class InfoWallFragment : Fragment(), InfoWallFragmentContract.View {

    @Inject
    lateinit var presenter: InfoWallFragmentContract.Presenter
    private var _binding: FragmentInfoWallBinding? = null
    private val binding get() = _binding!!

    val adapter: CoordinatorMessagesAdapter =
        CoordinatorMessagesAdapter(
            ArrayList(),
            ArrayList()
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoWallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        DaggerInfoWallFragmentComponent.builder()
            .applicationComponent((activity?.applicationContext as MyApp).getApplicationComponent())
            .infoWallFragmentModule(
                InfoWallFragmentModule(this)
            ).build().inject(this)


        binding.addVolunteerFab.setOnClickListener {
            onFloatButtonClick()
        }

        binding.volunteersRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@InfoWallFragment.context)
            adapter = this@InfoWallFragment.adapter
        }

        binding.form.addMessageBtn.setOnClickListener(this::onAddButtonFormClick)
        binding.form.cancelMessageBtn.setOnClickListener(this::onCancelButtonFormClick)

        binding.volunteerRefreshSwipe.setOnRefreshListener {
            onSwipeRefresh()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onFloatButtonClick() {
        presenter.onAddNewInfoButtonClick()
    }

    override fun updateRecyclerView(message: List<CoordinatorMessage>, level: List<Level>) {
        adapter.notifyDataSetChanged(message, level)
    }

    override fun switchToTextView() {
        if (binding.volunteerListSwitcher.nextView == binding.volunteerListSwitcher.findViewById(R.id.no_volunteer_view))
            binding.volunteerListSwitcher.showPrevious()
    }

    override fun switchToRecyclerView() {
        if (binding.volunteerListSwitcher.nextView == binding.volunteerListSwitcher.findViewById(R.id.volunteers_recycler_view))
            binding.volunteerListSwitcher.showNext()
    }

    override fun hideAddForm() {
        binding.addVolunteerFab.visibility = View.VISIBLE
        binding.form.root.visibility = View.GONE
    }

    override fun showAddForm() {
        binding.form.root.visibility = View.VISIBLE
        binding.addVolunteerFab.visibility = View.GONE
    }

    override fun isAddFormVisible(): Boolean {
        return binding.form.root.visibility == View.VISIBLE
    }

    override fun setRefreshing(refresh: Boolean) {
        binding.volunteerRefreshSwipe.isRefreshing = refresh
    }

    override fun setLevelAdapter(levels: Array<String>) {
        binding.form.levelSpinner.apply {
            setAdapter(
                ArrayAdapter(
                    this.context!!,
                    android.R.layout.simple_spinner_item,
                    levels
                )
            )
            setText(levels[0], false)
        }
    }

    override fun displayToastWithMessage(resId: Int) {
        val msg = activity?.resources?.getString(resId)
        Toast.makeText(this.activity, msg, Toast.LENGTH_LONG).show()
    }

    override fun hideKeyBoard() {
        val img = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        img.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun onAddButtonFormClick(view: View) {
        val msg = binding.form.messageEditText.text.toString()
        val level = binding.form.levelSpinner.text.toString()
        presenter.addNewMessage(msg, level)
    }

    private fun onCancelButtonFormClick(view: View) {
        presenter.onCancelButtonFormClick()
    }

    private fun onSwipeRefresh() {
        presenter.onSwipe()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}