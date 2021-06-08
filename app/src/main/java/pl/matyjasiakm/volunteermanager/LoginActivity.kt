package pl.matyjasiakm.volunteermanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import pl.matyjasiakm.volunteermanager.databinding.ActivityLoginBinding
import pl.matyjasiakm.volunteermanager.di.component.DaggerLoginActivityComponent
import pl.matyjasiakm.volunteermanager.di.module.LoginActivityMvpModule
import pl.matyjasiakm.volunteermanager.mvp.LoginActivityContract
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginActivityContract.View, View.OnClickListener {

    lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var presenter: LoginActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerLoginActivityComponent.builder()
            .applicationComponent((this.applicationContext as MyApp).getApplicationComponent())
            .loginActivityMvpModule(LoginActivityMvpModule(this)).build().inject(this)


        binding.signInBtn.setOnClickListener(this)

        presenter.activityCreated()

    }

    override fun showLoginProgress() {
        binding.loginCircularProgress.visibility = View.VISIBLE
        binding.emailTextInputLayout.visibility = View.GONE
        binding.passwordTextInputLayout.visibility = View.GONE
        binding.signInBtn.visibility = View.GONE
    }

    override fun hideProgress() {

        binding.loginCircularProgress.visibility = View.GONE
        binding.emailTextInputLayout.visibility = View.VISIBLE
        binding.passwordTextInputLayout.visibility = View.VISIBLE
        binding.signInBtn.visibility = View.VISIBLE
    }

    override fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun displayToastWithMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View?) {
        if (v is Button)
            presenter.signIn(
                binding.emailTextInput.text.toString(),
                binding.passwordTextInput.text.toString()
            )
    }
}