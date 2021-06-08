package pl.matyjasiakm.volunteermanager

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import pl.matyjasiakm.volunteermanager.di.component.DaggerSmsActivityComponent
import pl.matyjasiakm.volunteermanager.di.module.SmsActivityModule
import pl.matyjasiakm.volunteermanager.mvp.SmsActivityContract
import pl.matyjasiakm.volunteermanager.adapters.SmsAdapter
import pl.matyjasiakm.volunteermanager.databinding.ActivitySmsBinding
import pl.matyjasiakm.volunteermanager.utils.toEditable
import javax.inject.Inject

class SmsActivity : AppCompatActivity(), SmsActivityContract.View {

    lateinit var binding: ActivitySmsBinding

    @Inject
    lateinit var presenter: SmsActivityContract.Presenter

    val adapter: SmsAdapter =
        SmsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.smsRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@SmsActivity.application)
            (layoutManager as LinearLayoutManager).stackFromEnd = true
            (layoutManager as LinearLayoutManager).reverseLayout = true
            adapter = this@SmsActivity.adapter
        }

        DaggerSmsActivityComponent.builder()
            .applicationComponent((this.applicationContext as MyApp).getApplicationComponent())
            .smsActivityModule(SmsActivityModule(this)).build().inject(this)

        binding.smsSendBtn.setOnClickListener {
            val text = binding.smsTextInput.text.toString()
            presenter.onSendClick(text)
        }
        presenter.onCreate(this, intent)
    }

    override fun updateAdapter(cursor: Cursor) {
        adapter.updateCursor(cursor)
    }

    override fun showToastWithMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun clearSmsBody() {
        binding.smsTextInput.text = "".toEditable()
    }

    override fun hideSoftKey() {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}