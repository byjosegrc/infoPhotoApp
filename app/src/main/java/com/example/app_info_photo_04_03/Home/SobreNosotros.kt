package com.example.app_info_photo_04_03.Home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.MediaController
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
        title="\uD83D\uDCF1 SOBRE NOSOTROS"
        setListeners()
        mediaController = MediaController(this)
    }



    private fun setListeners() {

        binding.cvPlay.setOnClickListener{
            reproducirVideo()
        }
        binding.cvSede.setOnClickListener{
            startActivity(Intent(this, GoogleMaps1::class.java))
        }
    }

    private fun reproducirVideo() {


        var aleatorio = (0..1).random()
        var idVideo = 0
        when (aleatorio) {
            0 -> {
                idVideo = R.raw.video
            }
            1 -> {
                idVideo = R.raw.video1
            }
        }



        rutaVideo = "android.resource://" + packageName + "/$idVideo"
        var uri = Uri.parse(rutaVideo)
        try {
            binding.videoView.setVideoURI(uri)
            binding.videoView.requestFocus()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.videoView.setMediaController(mediaController)
        mediaController.setAnchorView(binding.videoView)
        // binding.videoView.start()

        if (posicion == 0) {
            binding.videoView.start()
        } else {
            binding.videoView.pause()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outPersistentState.putInt("POSICION",binding.videoView.currentPosition)
        binding.videoView.pause()
    }
    override fun onRestoreInstanceState(savedInstanceState:Bundle){
        super.onRestoreInstanceState(savedInstanceState)
        posicion = savedInstanceState.getInt("POSICION")
        binding.videoView.seekTo(posicion)
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