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


    //parte2
    lateinit var mediaController: MediaController

    //parte 3

    var posicion = 0 //para recordar las posiciones
    // cuando vamos agirar


    var rutaVideo = ""


    //creo el arraylistof para almacenar las imagenes del carusel
    private val list = mutableListOf<CarouselItem>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SobreNosotrosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title="\uD83C\uDF10 \uD835\uDC12\uD835\uDC0E\uD835\uDC01\uD835\uDC11\uD835\uDC04 \uD835\uDC0D\uD835\uDC0E\uD835\uDC12\uD835\uDC0E\uD835\uDC13\uD835\uDC11\uD835\uDC0E\uD835\uDC12"
        setListeners()
        mediaController = MediaController(this)
    }



    private fun setListeners() {

        binding.cvGaleria.setOnClickListener{
           // reproducirVideo()
            startActivity(Intent(this, GaleriaFotografias::class.java))
        }
        binding.cvSede.setOnClickListener{
            startActivity(Intent(this, GoogleMaps1::class.java))
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1,menu)
        return super.onCreateOptionsMenu(menu)
    }

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