package com.example.mapsappkelasindustri

import android.location.Location
import android.os.Bundle
import android.util.Log
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.extension.checkLocationPermission
import com.example.mapsappkelasindustri.databinding.ActivityMainBinding

class MainActivity : NoViewModelActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_main)
        binding.mapView.onCreate(savedInstanceState)

        checkLocationPermission {
            listenLocationChange()
        }

        binding.mapView.getMapAsync {
        }
    }

    override fun retrieveLocationChange(location: Location) {
        Log.d("lokasi device", "latitude: ${location.latitude} longitude: ${location.longitude}")
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