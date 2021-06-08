package pl.matyjasiakm.volunteermanager.adapters

import android.database.Cursor
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.matyjasiakm.volunteermanager.R

class SmsAdapter : RecyclerView.Adapter<SmsAdapter.ViewHolder>() {
    var cursor: Cursor? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val body = view.findViewById(R.id.sms_body_textInput) as TextView
        val linear = view.findViewById(R.id.sms_linear) as LinearLayout
        val v = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_sms_layout, parent, false)

        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (cursor!!.moveToPosition(position)) {
            holder.body.text = cursor!!.getString(cursor!!.getColumnIndex("body"))
            val per = cursor!!.getInt(9)
            if (per == 2) {
                holder.linear.gravity = Gravity.RIGHT
            }
        }
    }

    override fun getItemCount(): Int {
        if (cursor == null) {
            return 0
        } else
            return cursor?.count!!
    }

    fun updateCursor(cursor: Cursor) {
        this.cursor = cursor
        notifyDataSetChanged()
    }
}