package pl.matyjasiakm.volunteermanager.mvp


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.matyjasiakm.volunteermanager.data.authentication.FirebaseAuthData

import javax.inject.Inject

class LoginPresenterImpl @Inject constructor(
    private val auth: FirebaseAuthData,
    private val view: LoginActivityContract.View
) :
    LoginActivityContract.Presenter {

    override fun signIn(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) return
        view.showLoginProgress()
        val disp = auth.signInUser(email, password).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    view.navigateToMainActivity()

                }, { it ->
                    view.hideProgress()
                    it.message?.let {
                        view.displayToastWithMessage(it)
                    }
                }
            )
    }

    override fun activityCreated() {
        auth.isSigned().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ signed ->
                if (signed) view.navigateToMainActivity()
            }, { e ->
                print(e.message)
            })
    }
}