package com.example.app_info_photo_04_03.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.cardview.widget.CardView

import com.example.app_info_photo_04_03.*
import com.example.app_info_photo_04_03.SocialMedia.MediaPublicaciones
import com.example.app_info_photo_04_03.databinding.ActivityHomeBinding
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.firebase.auth.FirebaseAuth
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var preferencias: Prefs
    private lateinit var rutasFotos: CardView
    private lateinit var socialMedia: CardView
    private lateinit var sobreNosotrosInfo: CardView
    private lateinit var reservas: CardView

    //creo el arraylistof para almacenar las imagenes del carusel
    private val list = mutableListOf<CarouselItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title =
            "\uD83C\uDFE0 \uD835\uDC08\uD835\uDC0D\uD835\uDC08\uD835\uDC02\uD835\uDC08\uD835\uDC0E"

        // setListeners()
        preferencias = Prefs(this)
        initComponents()
        initListeners()
      //  setToolbar()


        // CARRUSEL:

        //busco el compone del carusel del layout
        val carousel: ImageCarousel = findViewById(R.id.carousel)

        //añado las imagenes al arraylistOf
        list.add(CarouselItem(R.drawable.banneruno))
        list.add(CarouselItem(R.drawable.bannerdos))
        list.add(CarouselItem(R.drawable.bannertres))
        list.add(CarouselItem(R.drawable.bannercuatro))
        //le pasamos al array la lista de valores en este caso
        // imagenes al carusel de imagenes
        carousel.addData(list)


        binding.bottomNavigationView.setOnItemSelectedListener {


            when (it.itemId) {

                R.id.home -> startActivity(Intent(this, HomeActivity::class.java))
                R.id.profile -> startActivity(Intent(this, PerfilUsuario::class.java))
                R.id.settings -> startActivity(Intent(this, SettingsActivity::class.java))

                else -> {


                }

            }

            true

        }


    }

   /* private fun setToolbar() {
        val toolbar: Toolbar = binding.mainToolbar
        setSupportActionBar(toolbar)
        var drawerLayout = binding.drawerLayout
        var navigationView = binding.navView

        var actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.openNavDrawer,
            R.string.closeNavDrawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
    }*/

    private fun initComponents() {
        rutasFotos = findViewById(R.id.cvRutas)
        sobreNosotrosInfo = findViewById(R.id.cvSobreNosotros)
        reservas = findViewById(R.id.cvReservas)
        socialMedia = findViewById(R.id.cvChat)
    }

    private fun initListeners() {
        rutasFotos.setOnClickListener {
            startActivity(Intent(this, RutasGoogle::class.java))
        }
        sobreNosotrosInfo.setOnClickListener {
            startActivity(Intent(this, SobreNosotros::class.java))
        }
        reservas.setOnClickListener {
            startActivity(Intent(this, ReservasActivity::class.java))
        }
        socialMedia.setOnClickListener {
            startActivity(Intent(this, MediaPublicaciones::class.java))
        }

    }


/*private fun setListeners() {
  //BOTON RUTAS:
  binding.btnMaps.setOnClickListener{
    startActivity(Intent(this, RutasGoogle::class.java))
  }
  //BOTON CAMARA
  binding.btnCamara.setOnClickListener {
      startActivity(Intent(this, CamaraActivity::class.java))
  }

  //BOTON VIDEO
  binding.btnVideo.setOnClickListener {
      startActivity(Intent(this, SobreNosotros::class.java))
  }
  //BOTON SEDE
  binding.btnSede.setOnClickListener {
      startActivity(Intent(this, GoogleMaps1::class.java))
  }
  //BOTON CARUSEL
  binding.btnCarusel.setOnClickListener {
      startActivity(Intent(this, CaruselActivity::class.java))
  }
  //BOTON RESERVA
  binding.btReserva.setOnClickListener {
      startActivity(Intent(this, ReservasActivity::class.java))
  }
  //BOTON CHAT
  binding.btnChat.setOnClickListener {
      startActivity(Intent(this, MediaPublicaciones::class.java))
  }
}*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater.inflate(R.menu.menu_app, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemSalir -> {
                finishAffinity()
                true
            }
            R.id.itemCerrar -> {
                FirebaseAuth.getInstance().signOut()
                preferencias.deleteAll()
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

/*    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        transaction = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.itemSalir -> {
                finishAffinity()
                true
            }
            R.id.itemCerrar -> {
                FirebaseAuth.getInstance().signOut()
                prefs.deleteAll()
                finish()
                true
            }
            else ->{
              return false
            }
        }
    }*/

}






