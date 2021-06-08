package pl.matyjasiakm.volunteermanager.mvp

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle

interface SmsActivityContract {
    interface View {
        fun updateAdapter(cursor: Cursor)
        fun showToastWithMessage(message: String)
        fun clearSmsBody()
        fun hideSoftKey()
    }

    interface Presenter {
        fun onCreate(context: Context, intent: Intent)
        fun onSendClick(text: String)
    }
}