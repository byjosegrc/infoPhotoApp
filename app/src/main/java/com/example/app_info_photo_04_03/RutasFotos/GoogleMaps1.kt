package com.example.app_info_photo_04_03.RutasFotos

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.app_info_photo_04_03.Home.HomeActivity
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.databinding.ActivityGoogleMaps1Binding
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMaps1 : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityGoogleMaps1Binding

    private lateinit var map:GoogleMap

    lateinit var prefs: Prefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps1)
        title="¿DONDE NOS ENCONTRAMOS?"
        createMapFragment()
    }

    private fun createMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.Maps)as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onMapReady(googleMaps: GoogleMap) {
            map = googleMaps
            createMarker()
    }
        private fun createMarker() {

            //UBICACION SEDE
            val sede = LatLng(36.847448,-2.462117)
            map.addMarker(MarkerOptions().position(sede).title("Nuestra Sede"))


            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(sede, 20f),
                4000,
                null
            )
        }

    //----------------------------------------------------------------------------------------------




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.inicio -> {
                startActivity(Intent(this, HomeActivity::class.java))
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

