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


    //creo el arraylistof para almacenar las imagenes del carusel
    private val list = mutableListOf<CarouselItem>()



    /**
     *Esta es la funcion on  ejecutas la lógica de arranque básica de la aplicación que debe ocurrir una
     * sola vez en toda la vida de la actividad. Por ejemplo, tu implementación de onCreate() podría vincular
     * datos a listas, asociar la actividad con un ViewModel y crear instancias de algunas variables de alcance de clase.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title =
            "\uD83C\uDFE0 \uD835\uDC08\uD835\uDC0D\uD835\uDC08\uD835\uDC02\uD835\uDC08\uD835\uDC0E"

        preferencias = Prefs(this)
        initListeners()
        // CARRUSEL:
        //busco el compone del carusel del layout
        val carousel: ImageCarousel = findViewById(R.id.carousel)

        //añado las imagenes al arraylistOf
        list.add(CarouselItem(R.drawable.bannercinco))
        list.add(CarouselItem(R.drawable.bannerpromo))
        list.add(CarouselItem(R.drawable.promo))
        list.add(CarouselItem(R.drawable.promouno))
        list.add(CarouselItem(R.drawable.promodos))
        list.add(CarouselItem(R.drawable.bannerrutas))
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


    /**
     * Esta funcion es para iniciar los listener de los activity de los botones del HOME
     */
    private fun initListeners() {
        binding.cvRutas.setOnClickListener {
            startActivity(Intent(this, RutasGoogle::class.java))
        }
        binding.cvSobreNosotros.setOnClickListener {
            startActivity(Intent(this, SobreNosotros::class.java))
        }
        binding.cvReservas.setOnClickListener {
            startActivity(Intent(this, ReservasActivity::class.java))
        }
        binding.cvChat.setOnClickListener {
            startActivity(Intent(this, MediaPublicaciones::class.java))
        }

    }


    /**
     * funcion de onCreateOptionsMenu() es el que nos va a permitir inflar nuestro menú, es decir, hacer
     * que las opciones definidas en el fichero XML tengan su propia apariencia dentro de nuestra aplicación Android.
     */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater.inflate(R.menu.menu_app, menu)
        return true
    }

    /**
     * En este método, puedes aumentar el recurso de menú (definido en XML) hacia el Menu proporcionado
     * en la devolución de llamada
     */
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
}






