package com.example.app_info_photo_04_03.RutasFotos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import android.annotation.SuppressLint
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.example.app_info_photo_04_03.Home.HomeActivity
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*


class Granada  : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    lateinit var prefs: Prefs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_granada)
        title = "ALHAMBRA"
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
                .add(LatLng(37.174787, -3.585901))
                .add(LatLng(37.174780, -3.585676))
                .add(LatLng(37.174874, -3.585283))
                .add(LatLng(37.175284, -3.585161))
                .add(LatLng(37.175617, -3.585802))
                .add(LatLng(37.176183, -3.586363))
                .add(LatLng(37.176603, -3.586834))
                .add(LatLng(37.177061, -3.587463))
                .add(LatLng(37.177084, -3.588393))
                .add(LatLng(37.176325, -3.588692))
                .add(LatLng(37.175750, -3.588689))
                .add(LatLng(37.174787, -3.585901))
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
        val coordenadas = LatLng(37.174787, -3.585901)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadas, 18f), 4500, null
        )
    }


        private fun createMarker() {


            //SALIDA DE LA RUTA
            val salidaRuta = LatLng(37.174780, -3.585676)
            map.addMarker(MarkerOptions().position(salidaRuta).title("Torre de Baltasar de La Cruz"))

            //PUNTOS PARA HACER FOTOS:


            //1

            val primera = LatLng(37.174787, -3.585901)
            map.addMarker(MarkerOptions()
                .position(primera)
                .title("1")
             //   .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador))
                )


            //2

            val segunda = LatLng(37.174874, -3.585283)
            map.addMarker(MarkerOptions().position(segunda).title("2")
                )


            //3
            val tercera = LatLng(37.175284, -3.585161)
            map.addMarker(MarkerOptions().position(tercera).title("3")
                )


            //5

            val cuarta = LatLng(37.175617, -3.585802)
            map.addMarker(MarkerOptions().position(cuarta).title("4")
             )


            //6
            val quita = LatLng(37.176603, -3.586834)
            map.addMarker(MarkerOptions().position(quita).title("5")
          )


            //7
            val siete = LatLng(37.177061, -3.587463)
            map.addMarker(MarkerOptions().position(siete).title("6")
            )


            //8
            val ocho = LatLng(37.176325, -3.588692)
            map.addMarker(MarkerOptions().position(ocho).title("7"))

            //9
            val nueve = LatLng(37.175750, -3.588689)
            map.addMarker(MarkerOptions().position(nueve).title("8")
            )




            //ANIMACIÓN PARA LA CAMARA DE LA SALIDA DE LA RUTA:


            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(salidaRuta, 20f),
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
