package pl.matyjasiakm.volunteermanager.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import pl.matyjasiakm.volunteermanager.R
import pl.matyjasiakm.volunteermanager.data.dao.Duty
import pl.matyjasiakm.volunteermanager.data.dao.OwnDateTime
import pl.matyjasiakm.volunteermanager.data.dao.Volunteer
import java.util.*

class VolunteerListAdapter : RecyclerView.Adapter<VolunteerListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val fullNameTextView: TextView = view.findViewById(R.id.names_text_view)
        val phoneTextView: TextView = view.findViewById(R.id.phone_number_text_view)
        val descriptionTextView: TextView = view.findViewById(R.id.description_text_view)
        val callBtn: ImageButton = view.findViewById(R.id.call_button)
        val smsBtn: ImageButton = view.findViewById(R.id.sms_button)
        val dutyBtn: ImageButton = view.findViewById(R.id.duty_button)
        val duty: MaterialTextView = view.findViewById(R.id.schedule_TexView)
    }

    var onSmsBtnClick: ISms? = null
    var onCallBtnClick: ICall? = null
    var onDutyClick: IDuty? = null

    interface ISms {
        fun onClick(phone: String)
    }

    interface ICall {
        fun onClick(phone: String)
    }

    interface IDuty {
        fun onClick(email: String)
    }

    private var volunteerList: List<Volunteer>? = null
    private var dutiesList: List<Duty>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.volunteer_item_card_view, parent, false)
        return ViewHolder(
            view
        )
    }

    lateinit var date: OwnDateTime

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var v: Volunteer
        var d: Duty
        if (dutiesList == null) {
            v = volunteerList?.get(position)!!
        } else {
            d = dutiesList?.get(position)!!
            v = volunteerList?.find { t ->
                t.Email == d.email
            }!!

            holder.duty.visibility = View.VISIBLE
            holder.duty.text =
                "Duty: ${d.start.toStringTime()} (${d.start.toStringData()}) - ${d.end.toStringTime()} (${d.end.toStringData()})"

            if (d.start.dateTime <= date.dateTime && d.end.dateTime >= date.dateTime) {
                holder.duty.setBackgroundColor(Color.YELLOW)
            }
        }

        holder.fullNameTextView.text = v.FullName
        holder.phoneTextView.text = "phone: ${v.PhoneNumber}"
        holder.descriptionTextView.text = v.Group

        holder.smsBtn.setOnClickListener {
            onSmsBtnClick?.onClick(v.PhoneNumber)
        }
        holder.callBtn.setOnClickListener {
            onCallBtnClick?.onClick(v.PhoneNumber)
        }
        holder.dutyBtn.setOnClickListener {
            onDutyClick?.onClick(v.Email)
        }
    }


    fun updateAdapter(list: List<Volunteer>) {
        volunteerList = list
        notifyDataSetChanged()
    }

    fun updateAdapter(list: List<Volunteer>, list2: List<Duty>) {
        volunteerList = list
        dutiesList = list2
        dutiesList=dutiesList!!.sortedBy {
            it.start.dateTime
        }

        notifyDataSetChanged()
        var calendarStart = Calendar.getInstance()
        date = OwnDateTime(
            calendarStart.get(Calendar.YEAR),
            calendarStart.get(Calendar.MONTH) + 1,
            calendarStart.get(Calendar.DAY_OF_MONTH),
            calendarStart.get(Calendar.HOUR_OF_DAY),
            calendarStart.get(Calendar.MINUTE)
        )

    }


    override fun getItemCount(): Int {
        if (dutiesList == null) {
            volunteerList?.let {
                return it.size
            }
        } else {
            dutiesList?.let {
                return it.size
            }
        }
        return 0
    }
}