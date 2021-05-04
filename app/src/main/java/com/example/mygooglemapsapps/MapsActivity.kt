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
        mMap.uiSettings.isZoomControlsEnabled = true
        var zoomScale: Float = 15f
        var x = 0

// Add a marker in Jakarta and move the camera
        val megaMallBKS = LatLng(-6.249869204395685, 106.99336250098648)
        val megaMallBKSLocation = Location("")
        megaMallBKSLocation.latitude = -6.249869204395685
        megaMallBKSLocation.longitude = 106.99336250098648

        val mallM = LatLng(-6.248162790389965, 106.99117381848073)
        val mallMLocation = Location("")
        mallMLocation.latitude = -6.248162790389965
        mallMLocation.longitude = 106.99117381848073

        val blk = LatLng(-6.234532061960018, 106.99041027874719)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(blk, 15f))

        var currentMarker = mMap.addMarker(MarkerOptions().position(blk).title("current "))

        val blkLocation = Location("")
        blkLocation.latitude = -6.234532061960018
        blkLocation.longitude = 106.99041027874719

        val stasiunBekasi = LatLng(-6.2362817288371035, 106.99922044547033)
        val stasiunBekasiLocation = Location("")
        stasiunBekasiLocation.latitude = -6.2362817288371035
        stasiunBekasiLocation.longitude = 106.99922044547033

        mMap.setOnCameraIdleListener {
            currentMarker.remove()
            var currentLocation: LatLng = mMap.cameraPosition.target
            val geocoder = Geocoder(this)
            geocoder.getFromLocation(currentLocation.latitude, currentLocation.longitude, 1)
            mMap.clear()
            currentMarker = mMap.addMarker(MarkerOptions()
                .position(currentLocation).title("posisition"))
        }

        val location1 = Location("")
        val location2 = Location("")
        var latLng1 = blk
        var latLng2 = blk
        var marker1 = currentMarker
        var marker2 = currentMarker
        marker1.isVisible = false
        marker2.isVisible = false
        var distancePolyline = mMap.addPolyline(PolylineOptions().clickable(true)
            .add(latLng1, latLng2).color(R.color.purple_700))


        btnset.setOnClickListener{
            x++
            val cameraPosition : LatLng = mMap.cameraPosition.target
            val latLngString = "${cameraPosition.longitude} | ${cameraPosition.latitude}"
            val geocoder = Geocoder(this)
            geocoder.getFromLocation(cameraPosition.latitude, cameraPosition.longitude, 1)

            if (x % 2 == 1){
                latLng1 = latLng2
                marker1.isVisible = true
                marker1.remove()
                marker1 = this.mMap.addMarker(MarkerOptions().position(latLng1).title("Location 1")
                    .snippet(latLngString))
                location1.latitude = latLng1.latitude
                location1.longitude = latLng1.longitude
                tvlati1.setText(location1.latitude.toString())
                tvlongti1.setText(location1.longitude.toString())

            } else {
                latLng2 = cameraPosition
                marker2.isVisible = true
                marker2.remove()
                marker2 = mMap.addMarker(MarkerOptions().position(latLng2).title("Location 2")
                    .snippet(latLngString))
                location2.latitude = latLng2.latitude
                location2.longitude = latLng2.longitude
                tvlati2.setText(location2.latitude.toString())
                tvlongti2.setText(location2.longitude.toString())
            }

            if ((latLng1.toString() != blk.toString()) && (latLng2.toString() != blk.toString())){
                distancePolyline.remove()
                distancePolyline = mMap.addPolyline(PolylineOptions().clickable(true)
                    .add(latLng1, latLng2).color(R.color.purple_700))
            }
            btnhitung.setOnClickListener{
                mMap.addPolyline(PolylineOptions()
                    .add(latLng1, latLng2).color(R.color.teal_700))
                tvkedua.setText(location1.distanceTo(location2).toInt().toString() + " meter")
            }
            btnreset.setOnClickListener {

                tvlati1.setText("")
                tvlongti1.setText("")
                tvlati2.setText("")
                tvlongti2.setText("")
                tvkedua.setText("")
            }
        }
    }
}