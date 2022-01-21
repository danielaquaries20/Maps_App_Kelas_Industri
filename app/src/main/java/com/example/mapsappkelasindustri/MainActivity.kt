package com.example.mapsappkelasindustri

import android.location.Location
import android.os.Bundle
import android.util.Log
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.extension.checkLocationPermission
import com.example.mapsappkelasindustri.databinding.ActivityMainBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MainActivity : NoViewModelActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_main)
        binding.mapView.onCreate(savedInstanceState)

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
                TODO("Not yet implemented")
                Log.i("latihan_autocomple", "An error occurred: $status")
            }

            override fun onPlaceSelected(place: Place) {
                TODO("Not yet implemented")
                Log.i("latihan_autocomple", "Place: ${place.name}, ${place.id}")
            }

        })


        checkLocationPermission {
            listenLocationChange()
        }

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