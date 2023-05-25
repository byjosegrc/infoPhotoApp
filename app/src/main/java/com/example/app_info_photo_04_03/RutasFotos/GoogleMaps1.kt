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
    /**
     *Esta es la funcion on  ejecutas la lógica de arranque básica de la aplicación que debe ocurrir una
     * sola vez en toda la vida de la actividad. Por ejemplo, tu implementación de onCreate() podría vincular
     * datos a listas, asociar la actividad con un ViewModel y crear instancias de algunas variables de alcance de clase.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps1)
        title="\uD83C\uDF0E \uD835\uDC0D\uD835\uDC14\uD835\uDC04\uD835\uDC12\uD835\uDC13\uD835\uDC11\uD835\uDC00 \uD835\uDC12\uD835\uDC04\uD835\uDC03\uD835\uDC04"
        createMapFragment()
    }

    /**
     * esta funcion es para crear el Fragment del mapa
     */
    private fun createMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.Maps)as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("SuspiciousIndentation")
    /**
     * la función onMapReady(), que se llamará automáticamente cuando el mapa haya cargado.
     * Es por eso que nuestra activity nos marca un error, pues no hemos sobrescrito el método todavía.
     */
    override fun onMapReady(googleMaps: GoogleMap) {
            map = googleMaps
            createMarker()
    }

    /**
     * Funcion para crear los marcador de nuestra sede
     */
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



    /**
     * funcion de onCreateOptionsMenu() es el que nos va a permitir inflar nuestro menú, es decir, hacer
     * que las opciones definidas en el fichero XML tengan su propia apariencia dentro de nuestra aplicación Android.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * En este método, puedes aumentar el recurso de menú (definido en XML) hacia el Menu proporcionado
     * en la devolución de llamada
     */

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

