package pl.matyjasiakm.volunteermanager.mvp

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.disposables.DisposableContainer
import pl.matyjasiakm.volunteermanager.R
import pl.matyjasiakm.volunteermanager.data.*
import pl.matyjasiakm.volunteermanager.data.dao.CoordinatorMessage
import pl.matyjasiakm.volunteermanager.data.dao.Level
import pl.matyjasiakm.volunteermanager.data.dao.WallMessage
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class InfoWallFragmentPresenterImpl @Inject constructor(
    private val db: IDataManager,
    private val view: InfoWallFragmentContract.View
) :
    InfoWallFragmentContract.Presenter {

    var disposables = CompositeDisposable()
    var levles: List<Level>? = null

    override fun onAddNewInfoButtonClick() {
        view.showAddForm()
    }

    private fun getAndSetMessages() {


        val observer = db.getAllInfoList().flatMap(
            {
                db.getAllCoordinators()
            }, { msg, coordinators ->
                if (msg.isEmpty()) {
                    view.switchToTextView()
                    view.setRefreshing(false)

                } else {
                    val list = LinkedList<CoordinatorMessage>()
                    for (m in msg) {
                        val c = coordinators.find { c -> c.email.equals(m.ownerEmail) }!!
                        list.add(
                            CoordinatorMessage(
                                c,
                                m
                            )
                        )
                    }
                    view.switchToRecyclerView()
                    view.updateRecyclerView(list, levles!!)
                    view.setRefreshing(false)
                }
            }
        ).subscribe()

        disposables.add(observer)
    }

    override fun onStart() {
        getAndSetMessages()
        val observer = db.getLevels().subscribe { l ->
            levles = l
            val lvArray: Array<String> = levles?.map { t -> t.levelName }?.toTypedArray()!!
            view.setLevelAdapter(lvArray)
        }
        disposables.add(observer)
    }

    override fun onCancelButtonFormClick() {
        view.hideAddForm()
    }


    override fun onSwipe() {
        getAndSetMessages()
    }

    override fun onDestroy() {
        disposables.dispose()
    }

    override fun addNewMessage(message: String, level: String) {
        if (message.isEmpty()) {
            view.displayToastWithMessage(R.string.empty_msg)
            return
        }
        val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
        val currentDate = sdf.format(Date())

        val observer1 = db.getSignedUserEmail().subscribe { email ->

            val observer2 = db.addMessage(
                WallMessage(
                    message,
                    levles?.find { t -> t.levelName.equals(level) }?.levelVal!!,
                    email,
                    currentDate
                )

            ).subscribe {
                view.hideKeyBoard()
                view.hideAddForm()
                view.displayToastWithMessage(R.string.add_succ)
                getAndSetMessages()
            }
            disposables.add(observer2)
        }
        disposables.add(observer1)
    }

}
