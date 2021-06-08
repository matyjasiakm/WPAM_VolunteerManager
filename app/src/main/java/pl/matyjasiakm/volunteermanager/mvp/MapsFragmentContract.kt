package pl.matyjasiakm.volunteermanager.mvp

import com.google.android.gms.maps.model.Marker
import pl.matyjasiakm.volunteermanager.data.dao.Coordinator
import pl.matyjasiakm.volunteermanager.data.dao.MyLatLng

interface MapsFragmentContract {
    interface View {
        fun updateMarksOnMap(coordinatorsList: List<Coordinator>)
        fun updateLocationOnMap(location: MyLatLng)
    }

    interface Presenter {
        fun update()
        fun addMarker(marker: Marker)
        fun onUpdateBtnClick()
        fun onDestroy()
    }
}