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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rutas_google)
        title="RUTAS FOTOS"
        binding= ActivityRutasGoogleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
    }

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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return super.onCreateOptionsMenu(menu)
    }

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