package pl.matyjasiakm.volunteermanager.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.matyjasiakm.volunteermanager.R
import pl.matyjasiakm.volunteermanager.data.dao.Duty
import pl.matyjasiakm.volunteermanager.data.dao.OwnDateTime
import java.util.*

class DutyAdapter() :
    RecyclerView.Adapter<DutyAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        val textView = view.findViewById<TextView>(R.id.text_view_dty_item)
        val buttonDel = view.findViewById<ImageButton>(R.id.delete_button)


    }



    var onDel: IDel? = null

    interface IDel {
        fun onClick(id: String)
    }

    var list: List<Duty> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.duty_list_item, parent, false)
        return MyViewHolder(
            view
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        holder.textView.text =
            "${model.start.toStringTime()} (${model.start.toStringData()}) - ${model.end.toStringTime()} (${model.end.toStringData()})"

        holder.buttonDel.setOnClickListener {
            onDel?.onClick(model.id)
        }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    fun notifyDataSetChange(list: List<Duty>) {


        this.list = list
        notifyDataSetChanged()
    }
}