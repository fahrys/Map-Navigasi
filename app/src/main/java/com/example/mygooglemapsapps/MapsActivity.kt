package com.example.mygooglemapsapps

import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val jakarta = LatLng(-6.2088, 106.8456)
        val jakartaLocation = Location("")
        jakartaLocation.latitude = -6.2088
        jakartaLocation.longitude = 106.8456

        val bekasi = LatLng (-6.2551243,106.9262634)
        val bekasiLocation = Location("")
        bekasiLocation.latitude = -6.2551243
        bekasiLocation.latitude = 106.9262634

        val distancel = jakartaLocation.distanceTo(bekasiLocation)

        val serang = LatLng (-6.1105671 , 106.1240002)
        val bogor = LatLng (-6.5950181 , 106.7218509)

        mMap.addMarker(MarkerOptions().position(jakarta).title("Jarak Bekasi Ke Jakarta $distancel"))
//        mMap.addMarker(MarkerOptions().position(jakarta).title("Marker in Jakarta"))
        mMap.addMarker(MarkerOptions().position(bekasi).title("Marker in Bekasi"))
        mMap.addMarker(MarkerOptions().position(serang).title("Marker in Serang"))
        mMap.addMarker(MarkerOptions().position(bogor).title("Marker in Bogor"))
/////////
        mMap.setOnCameraIdleListener {
            var currentLocation: LatLng = mMap.cameraPosition.target
            val geocoder = Geocoder(this)
            geocoder.getFromLocation(currentLocation.latitude , currentLocation.longitude , 1)
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(currentLocation).title("Position"))
        }
////////////////////
        mMap.moveCamera(CameraUpdateFactory.newLatLng(jakarta))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jakarta , 16f))

        mMap.addPolyline(PolylineOptions()
                .clickable(true)
                .add(jakarta , bekasi)
                .color(R.color.purple_700)
                .width(16f))
        mMap.uiSettings.isZoomControlsEnabled = true

        mMap.addPolyline(PolylineOptions()
                .clickable(true)
                .add(bekasi , serang)
                .color(R.color.purple_700)
                .width(16f))
        mMap.uiSettings.isZoomControlsEnabled = true

        mMap.addPolyline(PolylineOptions()
                .clickable(true)
                .add(serang , bogor)
                .color(R.color.purple_700)
                .width(16f))
        mMap.uiSettings.isZoomControlsEnabled = true

        mMap.addCircle(CircleOptions()
                .clickable(true)
                .center(jakarta)
                .radius(10000.0)
                .strokeColor(R.color.black))

        mMap.uiSettings.isZoomControlsEnabled = true
    }
}