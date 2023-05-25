package com.example.app_info_photo_04_03.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.RutasFotos.Almeria
import com.example.app_info_photo_04_03.RutasFotos.Granada
import com.example.app_info_photo_04_03.databinding.ActivityRutasGoogleBinding

class RutasGoogle : AppCompatActivity() {
    lateinit var binding: ActivityRutasGoogleBinding


    /**
     *Esta es la funcion on  ejecutas la lógica de arranque básica de la aplicación que debe ocurrir una
     * sola vez en toda la vida de la actividad. Por ejemplo, tu implementación de onCreate() podría vincular
     * datos a listas, asociar la actividad con un ViewModel y crear instancias de algunas variables de alcance de clase.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rutas_google)
        title="\uD83D\uDCCC \uD835\uDC11\uD835\uDC14\uD835\uDC13\uD835\uDC00\uD835\uDC12 \uD835\uDC05\uD835\uDC0E\uD835\uDC13\uD835\uDC0E\uD835\uDC12"
        binding= ActivityRutasGoogleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
    }


    /**
     * Funcion para el listener de lo boton del activity del cvGranada y cvAlmeria
     *
     * cvGranada -> Nos lleva al activity de Granada que es el mapa con la ruta de las fotos en la Alhambra
     *
     * cvAlmeria -> Nos lleva al activity de Granada que es el mapa con la ruta de las fotos en Almeria Capital
     *
     */
    private fun setListener() {

        //RUTAS GRANADA:

        binding.cvGranada.setOnClickListener{
            startActivity(Intent(this, Granada::class.java))
        }

        //RUTA ALMERIA:

        binding.cvAlmeria.setOnClickListener{
            startActivity(Intent(this, Almeria::class.java))
        }
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
        return when (item.itemId) {
            R.id.inicio -> {
                startActivity(Intent(this, HomeActivity::class.java))
                true
            }
            else -> true
        }
    }
}