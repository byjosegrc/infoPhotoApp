package com.example.app_info_photo_04_03.RutasFotos

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.example.app_info_photo_04_03.Home.HomeActivity
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class Almeria : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap

    lateinit var prefs: Prefs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_granada)
        title = "Almeria Capital Tour"
        createMapFragment()
    }

    private fun createMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.Maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onMapReady(googleMaps: GoogleMap) {
        map = googleMaps
        createMarker() //PARA SEÑALAR SITIO IMPORTANTE
        crearPolyLines()
        crearAnimacion()

    }


    private fun crearPolyLines() {
        val polyLineOptions = PolylineOptions()
            .add(LatLng(36.832174, -2.463499))
            .add(LatLng(36.832838, -2.463571))
            .add(LatLng(36.833116, -2.463530))
            .add(LatLng(36.833361, -2.464272))

            .add(LatLng(36.834985, -2.462990))
            .add(LatLng(36.837345, -2.463321))

            .add(LatLng(36.836848, -2.464306))

            .add(LatLng(36.837306, -2.465892))

            .add(LatLng(36.838186, -2.465529))
            .add(LatLng(36.838600, -2.467061))

            .add(LatLng(36.837060, -2.467511))
            .add(LatLng(36.833737, -2.464509))
            .add(LatLng(36.832174, -2.463499))
            .width(15f)
            .color(ContextCompat.getColor(this, R.color.black))

        val polyline = map.addPolyline(polyLineOptions)
        val patron = listOf(
            //Dibujo del patron
            //.  _________   .   ________  .  __________
            Dot(), Gap(10f), Dash(50f), Gap(10f)
        )
        polyline.pattern = patron
    }

    private fun crearAnimacion() {
        //Añadimos una pequeña animacion
        val coordenadas = LatLng(36.832174, -2.463499)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadas, 18f), 4500, null
        )
    }


    private fun createMarker() {
        val favoritePlace = LatLng(36.832174, -2.463499)
        map.addMarker(MarkerOptions().position(favoritePlace).title("El cable Ingles"))
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(favoritePlace, 20f),
            4000,
            null
        )
        //PUNTOS PARA HACER FOTOS:


        //1

        val primera = LatLng(36.832838, -2.463571)
        map.addMarker(MarkerOptions()
            .position(primera)
            .title("1")
            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador))
        )
        //2

        val segunda = LatLng(36.832838, -2.463571)
        map.addMarker(MarkerOptions()
            .position(segunda)
            .title("2")
        )

        //3

        val tres = LatLng(36.833116, -2.463530)
        map.addMarker(MarkerOptions()
            .position(tres)
            .title("3")
        )

        //4

        val cuatro = LatLng( 36.833361, -2.464272)
        map.addMarker(MarkerOptions()
            .position(cuatro)
            .title("4")
        )

        //5

        val cinco = LatLng( 36.834985, -2.462990)
        map.addMarker(MarkerOptions()
            .position(cinco)
            .title("5")
        )


        //6

        val seis = LatLng( 36.837345, -2.463321)
        map.addMarker(MarkerOptions()
            .position(seis)
            .title("6")
        )



        //7

        val siete = LatLng( 36.834985, -2.462990)
        map.addMarker(MarkerOptions()
            .position(siete)
            .title("7")
        )




        //CATEDRAL ALMERIA

        val ocho = LatLng( 36.838600, -2.467061)
        map.addMarker(MarkerOptions()
            .position(ocho)
            .title("CATEDRAL")
        )




        //museo de fotografia

        val nueve = LatLng(36.837060, -2.467511)
        map.addMarker(MarkerOptions()
            .position(nueve)
            .title("MUSEO DE LA FOTOGRAFIA")
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