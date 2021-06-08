package pl.matyjasiakm.volunteermanager.mvp

import android.content.Context
import pl.matyjasiakm.volunteermanager.data.dao.DailyForecastList

interface WeatherFragmentContract {
    interface View {
        fun updateAdapter(list: DailyForecastList)
    }

    interface Presenter {
        fun onStart(context: Context);
        fun onDestroy();
    }
}