package com.example.app_info_photo_04_03.Home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.MediaController
import androidx.core.view.isVisible
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.RutasFotos.GoogleMaps1
import com.example.app_info_photo_04_03.databinding.SobreNosotrosBinding
import com.example.app_info_photo_04_03.pref.Prefs
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class SobreNosotros : AppCompatActivity() {
    lateinit var binding: SobreNosotrosBinding

    lateinit var prefs: Prefs
    /**
     *Esta es la funcion on  ejecutas la lógica de arranque básica de la aplicación que debe ocurrir una
     * sola vez en toda la vida de la actividad. Por ejemplo, tu implementación de onCreate() podría vincular
     * datos a listas, asociar la actividad con un ViewModel y crear instancias de algunas variables de alcance de clase.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SobreNosotrosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title="\uD83C\uDF10 \uD835\uDC12\uD835\uDC0E\uD835\uDC01\uD835\uDC11\uD835\uDC04 \uD835\uDC0D\uD835\uDC0E\uD835\uDC12\uD835\uDC0E\uD835\uDC13\uD835\uDC11\uD835\uDC0E\uD835\uDC12"
        setListeners()
    }


    /**
     * Funcion para el listener de los botones de cvGaleria y  cvSede
     *
     * Donde ...
     *
     * cvGaleria -> Nos lleva al activity GaleriaFotografias
     *
     * cvSede -> Nos lleva al activity GoogleMaps1
     */
    private fun setListeners() {

        binding.cvGaleria.setOnClickListener{
            startActivity(Intent(this, GaleriaFotografias::class.java))
        }
        binding.cvSede.setOnClickListener{
            startActivity(Intent(this, GoogleMaps1::class.java))
        }
    }


    /**
     * funcion de onCreateOptionsMenu() es el que nos va a permitir inflar nuestro menú, es decir, hacer
     * que las opciones definidas en el fichero XML tengan su propia apariencia dentro de nuestra aplicación Android.
     */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1,menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * En este método, puedes aumentar el recurso de menú (definido en XML) hacia el Menu proporcionado
     * en la devolución de llamada
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.inicio ->{
                startActivity(Intent(this, HomeActivity::class.java))
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}