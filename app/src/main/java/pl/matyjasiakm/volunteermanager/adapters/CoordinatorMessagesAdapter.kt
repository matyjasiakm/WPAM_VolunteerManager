package pl.matyjasiakm.volunteermanager.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import pl.matyjasiakm.volunteermanager.R
import pl.matyjasiakm.volunteermanager.data.dao.CoordinatorMessage
import pl.matyjasiakm.volunteermanager.data.dao.Level
import pl.matyjasiakm.volunteermanager.data.dao.OwnDateTime
import java.lang.IllegalStateException
import java.util.*

class CoordinatorMessagesAdapter(
    private var list: List<CoordinatorMessage>,
    private var levels: List<Level>
) :
    RecyclerView.Adapter<CoordinatorMessagesAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameTextView: MaterialTextView = view.findViewById(R.id.coordinator_name_textView)
        val groupTextView: MaterialTextView = view.findViewById(R.id.group_name_textView)
        val messageTextView: MaterialTextView = view.findViewById(R.id.message_textView)
        val infoLevelImageView: ImageView = view.findViewById(R.id.info_level_ImageView)
        val dateTextView: MaterialTextView = view.findViewById(R.id.date_textView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.info_display_layout, parent, false)
        return ViewHolder(
            view
        )
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = "${list[position].coordinator.FullName}"
        holder.groupTextView.text = list[position].coordinator.coordinatedGroups.joinToString()
        holder.messageTextView.text = list[position].message.info
        holder.dateTextView.text = "Added: ${list[position].message.created}"
        val iconID =
            when (levels.find { t -> t.levelVal == list[position].message.level }!!.levelName) {
                "Info" -> R.drawable.ic_baseline_info_24
                "Warning" -> R.drawable.ic_baseline_warning_24
                "Alert" -> R.drawable.ic_baseline_error_outline_24
                else -> R.drawable.ic_baseline_info_24
            }

        holder.infoLevelImageView.setImageResource(iconID)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun notifyDataSetChanged(messages: List<CoordinatorMessage>, l: List<Level>) {
        list = messages
        levels = l


        notifyDataSetChanged()
    }
}