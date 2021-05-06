package com.example.mygooglemapsapps

import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var currentLocation : LatLng
    private lateinit var currentAddress:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var locationList = mutableMapOf<String, Double>(
                "latloc1" to 0.0,
                "lngloc1" to 0.0,
                "latloc2" to 0.0,
                "lngloc2" to 0.0
        )
        var addresList = mutableMapOf<String, String>("Address1" to "", "Address2" to "")

        val jakarta = LatLng(-6.2088, 106.8456)
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jakarta, 16f))

        mMap.setOnCameraIdleListener {
            currentLocation = mMap.cameraPosition.target
            val geocoder = Geocoder(this)
            mMap.clear()
            var geocoderResult = geocoder.getFromLocation(currentLocation.latitude, currentLocation.longitude, 1)
            currentAddress = geocoderResult[0].getAddressLine(0)
            mMap.addMarker(MarkerOptions().position(currentLocation).title("Posisi"))


            btnset.setOnClickListener {
                if (locationList.get("latloc1") == 0.0) {
                    locationList.put("latloc1", currentLocation.latitude)
                    locationList.put("lngloc1", currentLocation.longitude)
                    addresList.put("Address1", currentAddress)
                } else {
                    locationList.put("latloc2", currentLocation.latitude)
                    locationList.put("lngloc2", currentLocation.longitude)
                    addresList.put("Address2", currentAddress)
                }
                tvlati1.text = "${locationList.get("latloc1")} - ${locationList.get("lngloc1")}"
                tvlati2.text = "${locationList.get("latloc2")} - ${locationList.get("lngloc2")}"
                tvlongti1.text = addresList.get("Address1").toString()
                tvlongti2.text = addresList.get("Address2").toString()

            }

            btnhitung.setOnClickListener {
                val loc1 = LatLng(locationList.get("latloc1")!!, locationList.get("lngloc1")!!)
                val location1 = Location("")
                location1.latitude = locationList.get("latloc1")!!
                location1.longitude = locationList.get("lngloc1")!!

                val loc2 = LatLng(locationList.get("latloc2")!!, locationList.get("lngloc2")!!)
                val location2 = Location("")
                location2.latitude = locationList.get("latloc2")!!
                location2.longitude = locationList.get("lngloc2")!!

                mMap.addMarker(MarkerOptions().position(loc1).title("Posisi"))
                mMap.addMarker(MarkerOptions().position(loc2).title("Posisi"))

                mMap.addPolyline(
                        PolylineOptions()
                                .clickable(true)
                                .add(loc1, loc2)
                                .color(R.color.purple_700)
                                .width(16f)
                )
                val distance1 = location1.distanceTo(location2)
                tvkedua.text = "${distance1 / 1000} KM"
            }

            btnreset.setOnClickListener {
                locationList.putAll(
                        setOf(
                                "latloc1" to 0.0,
                                "lngloc1" to 0.0,
                                "latloc2" to 0.0,
                                "lngloc2" to 0.0
                        )
                )
                tvlati2.text = ""
                tvlongti2.text = ""
                tvlati1.text = ""
                tvlongti1.text = ""
                tvkedua.text = ""
                addresList.clear()
                mMap.clear()
            }

        }


//        mMap.uiSettings.isZoomControlsEnabled = true
//        mMap.uiSettings.isMapToolbarEnabled = true

    }
}