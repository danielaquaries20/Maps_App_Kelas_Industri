package com.example.mapsappkelasindustri

import android.location.Location
import android.os.Bundle
import android.util.Log
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.extension.checkLocationPermission
import com.crocodic.core.extension.popNotification
import com.example.mapsappkelasindustri.databinding.ActivityMainBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.maps.route.extensions.drawRouteOnMap
import com.maps.route.extensions.moveCameraOnMap
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : NoViewModelActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_main)
        binding.mapView.onCreate(savedInstanceState)

        //Notification In App
//        binding.btnNotif.setOnClickListener {
//            popNotification("Notifikasi Baru", "Hallo, ini pesan baru anda tolong diperhatikan.")
//        }

        //Notification with Event Bus
        binding.btnNotif.setOnClickListener {
            val newMessage = MyMessage("Pesan Baru", "Halo, bagaimana kabarmu?")
            EventBus.getDefault().post(newMessage)
        }


        //Draw Route Google Map
        binding.mapView.getMapAsync {
            //Set Location and Destination
            val crocodic = LatLng(-7.0644051, 110.4165274)//starting point (Latlng)
            val hermina = LatLng(-7.0727976, 110.411677)//ednding point (Latlng)

            //Called the DrawRouteOnMap extension to draw the ployline/route on google maps
            it.moveCameraOnMap(latLng = crocodic)

            it.drawRouteOnMap(
                getString(R.string.google_api_key),
                source = crocodic,
                destination = hermina,
                context = applicationContext
            )

        }

        checkLocationPermission {
            listenLocationChange()
        }

        //Autocomplete Fragment
        autocomplete()

    }

    //Notification with Event Bus
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showMyMessage(myMessage: MyMessage) {
        popNotification("Notifikasi Baru", "Hallo, ini pesan baru anda tolong diperhatikan.")
    }

    private fun autocomplete() {
        //Initialize the SDK
        Places.initialize(applicationContext, "AIzaSyCLdFcogaVqXIrBQnAl4229PVcbg7zp-SE")

        //Initialize the AutocompleteSupportFragment
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentAutocomplete) as AutocompleteSupportFragment

        //Specify the types of place data to return
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        //Set up a PlaceSelectionListener to handle the response
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(status: Status) {
                Log.i("latihan_autocomple", "An error occurred: $status")
            }

            override fun onPlaceSelected(place: Place) {
                Log.i("latihan_autocomple", "Place: ${place.name}, ${place.id}")
            }

        })
    }

    override fun retrieveLocationChange(location: Location) {
        Log.d("lokasi device", "latitude: ${location.latitude} longitude: ${location.longitude}")

        binding.mapView.getMapAsync {
            it.clear()

            val myLocation = LatLng(location.latitude, location.longitude)
            it.addMarker(MarkerOptions().position(myLocation).title("My Location"))

            it.animateCamera(CameraUpdateFactory.newLatLng(myLocation))
        }

    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }


}