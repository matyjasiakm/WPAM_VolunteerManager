package pl.matyjasiakm.volunteermanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import pl.matyjasiakm.volunteermanager.R
import pl.matyjasiakm.volunteermanager.data.dao.DailyForecastList

class WeatherForecastAdapter(private var list: DailyForecastList?) :
    RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date = view.findViewById(R.id.date) as MaterialTextView
        val day = view.findViewById(R.id.day) as MaterialTextView
        val night = view.findViewById(R.id.night) as MaterialTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.weather_info_card, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.let {
            val forecast = it.DailyForecasts[position]
            if (position == 0) holder.date.text = "Today"
            else if (position == 1) holder.date.text = "Tomorrow"
            else holder.date.text = forecast.getOnlyDate()
            holder.day.text =
                "${forecast.Temperature.getMaxC()
                    .toInt()}\u2103 / ${forecast.Temperature.getMinC()
                    .toInt()}℃ "
            holder.night.text =
                "Day: ${forecast.Day.IconPhrase}\nNight: ${forecast.Night.IconPhrase}"
        }
    }

    override fun getItemCount(): Int {
        list?.let {
            return it.DailyForecasts.size
        }
        return 0
    }

    fun notifyDataSetChange(list: DailyForecastList) {
        this.list = list
        notifyDataSetChanged()
    }
}