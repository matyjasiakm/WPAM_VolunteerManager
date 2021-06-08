package pl.matyjasiakm.volunteermanager

import android.graphics.Color
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ui.IconGenerator
import pl.matyjasiakm.volunteermanager.data.dao.Coordinator
import pl.matyjasiakm.volunteermanager.data.dao.MyLatLng
import pl.matyjasiakm.volunteermanager.databinding.FragmentMapsBinding
import pl.matyjasiakm.volunteermanager.di.component.DaggerMapsFragmentComponent
import pl.matyjasiakm.volunteermanager.di.module.MapsFragmentModule
import pl.matyjasiakm.volunteermanager.mvp.MapsFragmentContract
import javax.inject.Inject

class MapsFragment : Fragment(), MapsFragmentContract.View {
    @Inject
    lateinit var presenter: MapsFragmentContract.Presenter

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DaggerMapsFragmentComponent.builder()
            .applicationComponent((activity?.applicationContext as MyApp).getApplicationComponent())
            .mapsFragmentModule(
                MapsFragmentModule(this)
            ).build().inject(this)


        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.updateBtn.setOnClickListener {
            presenter.onUpdateBtnClick()
        }
        presenter.update()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun updateMarksOnMap(coordinatorsList: List<Coordinator>) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            for (c in coordinatorsList) {
                c.location?.let {
                    val latLng = LatLng(it.lat, it.lng);
                    val title = c.FullName

                    val text = TextView(context)
                    text.setTextColor(Color.parseColor("#ffffff"))
                    text.text = title

                    val generator = IconGenerator(context);
                    generator.setBackground(context?.getDrawable(R.drawable.amu_bubble_mask))
                    generator.setContentView(text)
                    val icon = generator.makeIcon()

                    val marker = googleMap.addMarker(
                        MarkerOptions().position(latLng).icon(
                            BitmapDescriptorFactory.fromBitmap(icon)
                        )
                    )
                    presenter.addMarker(marker)
                }

            }
        }
    }

    override fun updateLocationOnMap(location: MyLatLng) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        location.lat,
                        location.lng
                    ), 12.0f
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}