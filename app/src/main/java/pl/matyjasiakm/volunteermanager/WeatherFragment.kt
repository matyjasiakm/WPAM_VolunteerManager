package pl.matyjasiakm.volunteermanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import pl.matyjasiakm.volunteermanager.data.dao.DailyForecastList
import pl.matyjasiakm.volunteermanager.di.component.DaggerWeatherFragmentComponent
import pl.matyjasiakm.volunteermanager.di.module.WeatherFragmentModule
import pl.matyjasiakm.volunteermanager.adapters.WeatherForecastAdapter
import pl.matyjasiakm.volunteermanager.databinding.FragmentWeatherBinding
import pl.matyjasiakm.volunteermanager.mvp.WeatherFragmentContract
import javax.inject.Inject


class WeatherFragment : Fragment(), WeatherFragmentContract.View {

    lateinit var binding: FragmentWeatherBinding

    @Inject
    lateinit var presenter: WeatherFragmentContract.Presenter
    val adapter =
        WeatherForecastAdapter(null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DaggerWeatherFragmentComponent.builder()
            .applicationComponent((activity?.applicationContext as MyApp).getApplicationComponent())
            .weatherFragmentModule(WeatherFragmentModule(this)).build().inject(this)
        binding = FragmentWeatherBinding.inflate(inflater, container, false)

        binding.weatherRecycler.apply {
            layoutManager = LinearLayoutManager(this@WeatherFragment.context)
            adapter = this@WeatherFragment.adapter
        }

        presenter.onStart(context!!)
        return binding.root;
    }

    override fun updateAdapter(list: DailyForecastList) {
        adapter.notifyDataSetChange(list)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}