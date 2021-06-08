package pl.matyjasiakm.volunteermanager.mvp

interface LoginActivityContract {
    interface View {
        fun showLoginProgress()
        fun hideProgress()
        fun navigateToMainActivity()
        fun displayToastWithMessage(message: String)
    }

    interface Presenter {
        fun signIn(email: String, password: String)
        fun activityCreated()
    }
}