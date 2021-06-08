package pl.matyjasiakm.volunteermanager.mvp

import pl.matyjasiakm.volunteermanager.data.dao.CoordinatorMessage
import pl.matyjasiakm.volunteermanager.data.dao.Level

interface InfoWallFragmentContract {

    interface View {
        fun updateRecyclerView(message: List<CoordinatorMessage>, level: List<Level>)
        fun switchToTextView()
        fun switchToRecyclerView()
        fun hideAddForm()
        fun showAddForm()
        fun isAddFormVisible(): Boolean
        fun setRefreshing(refresh: Boolean)
        fun setLevelAdapter(levels: Array<String>)
        fun displayToastWithMessage(resId: Int)
        fun hideKeyBoard()
    }

    interface Presenter {
        fun onAddNewInfoButtonClick()
        fun onStart()
        fun onCancelButtonFormClick()
        fun onSwipe()
        fun onDestroy()
        fun addNewMessage(message: String, level: String)
    }
}