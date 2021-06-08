package pl.matyjasiakm.volunteermanager.mvp

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.telephony.SmsManager
import javax.inject.Inject

class SmsActivityPresenterImpl @Inject constructor(val view: SmsActivityContract.View) :
    SmsActivityContract.Presenter {
    lateinit var cursor: Cursor
    lateinit var contentResolver: ContentResolver
    lateinit var phone: String

    fun updateSms() {
        cursor = contentResolver.query(
            Uri.parse("content://sms/"), null, "address=?",
            arrayOf(phone), null, null
        )!!

        view.updateAdapter(cursor)
    }

    override fun onCreate(context: Context, intent: Intent) {
        phone = intent.getStringExtra("phone")
        contentResolver = context.contentResolver
        updateSms()
    }

    override fun onSendClick(text: String) {
        if (text.isEmpty()) {
            view.showToastWithMessage("Sms cannot be empty!")
            view.clearSmsBody()
            view.hideSoftKey()
            return
        }
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto:$phone")
        intent.putExtra("sms_body", text)
        val smsManager = SmsManager.getDefault() as SmsManager
        smsManager.sendTextMessage(phone, null, text, null, null)
        view.clearSmsBody()
        view.hideSoftKey()
        Handler().postDelayed({
            updateSms()
        },1000)

    }
}