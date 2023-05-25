package com.example.app_info_photo_04_03

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.app_info_photo_04_03.Home.HomeActivity
import com.example.app_info_photo_04_03.Home.SettingsActivity
import com.example.app_info_photo_04_03.databinding.PerfilusuarioBinding
import com.example.app_info_photo_04_03.model.Perfil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.example.app_info_photo_04_03.pref.Prefs

class PerfilUsuario : AppCompatActivity() {


    lateinit var binding: PerfilusuarioBinding
    lateinit var db: FirebaseDatabase
    lateinit var storage: FirebaseStorage
    lateinit var prefs: Prefs

    /**
     *Esta es la funcion on  ejecutas la lógica de arranque básica de la aplicación que debe ocurrir una
     * sola vez en toda la vida de la actividad. Por ejemplo, tu implementación de onCreate() podría vincular
     * datos a listas, asociar la actividad con un ViewModel y crear instancias de algunas variables de alcance de clase.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        prefs = Prefs(this)
        super.onCreate(savedInstanceState)
        binding = PerfilusuarioBinding.inflate(layoutInflater)
        //URL DE LA BASE DE DATOS EN STORAGE:

        storage = FirebaseStorage.getInstance("gs://infophoto-2023.appspot.com")

        //URL DE LA BASE DE DATOS EN REAL TIME DATABASE

        db = FirebaseDatabase.getInstance("https://infophoto-2023-default-rtdb.europe-west1.firebasedatabase.app/")

        binding.tvCorreo.text = prefs.getEmail()

        setContentView(binding.root)
        setListeners()
        obtenerDatos()
        title = "\uD83D\uDC65 \uD835\uDC0F\uD835\uDC04\uD835\uDC11\uD835\uDC05\uD835\uDC08\uD835\uDC0B \uD835\uDC14\uD835\uDC12\uD835\uDC14\uD835\uDC00\uD835\uDC11\uD835\uDC08\uD835\uDC0E"


        binding.bottomNavigationView.setOnItemSelectedListener {


            when(it.itemId){

                R.id.home -> startActivity(Intent(this, HomeActivity::class.java))
                R.id.profile -> startActivity(Intent(this, PerfilUsuario::class.java))
                R.id.settings -> startActivity(Intent(this, SettingsActivity::class.java))

                else ->{
                }
            }

            true

        }

    }

    /**
     * Funcion para el listener del boton modificar en caso de que se quiera modificar los datos el usuario en su cuenta
     */
    private fun setListeners() {
        binding.btnModificar.setOnClickListener {
            startActivity(Intent(this, ModificarPerfil::class.java))
        }
    }

    /**
     * Funcion para traer los datos de cada usuario por pantalla
     * tanto su nombre, apellidos y ciudad
     */
    private fun obtenerDatos() {

        db.getReference("perfiles").addValueEventListener(object : ValueEventListener {



        override fun onDataChange(snapshot: DataSnapshot) {


            binding.prg.isVisible = false
            binding.prg1.isVisible = false
            binding.prg2.isVisible = false
            binding.prg3.isVisible = false


            if(snapshot.exists()){

                    val u = snapshot.child(prefs.getEmail().toString().replace(".", "-")).getValue(Perfil::class.java)

                   if(u != null){
                        binding.tvNombre.text =  u.nombre //"Pepe"
                        binding.tvApellido.text = u.apellido //"Garcia"
                        binding.tvCiudad.text = u.ciudad //"Granada"
                        //recoger imagen
                        u.email?.let { recogerImagen(it) }

                    }

            }
        }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    /**
     * Recogemos la imagen desde el storage de cada usuario para mostrarlo por pantalla
     */
    private fun recogerImagen(email: String) {
        //compruebo si tiene imagen o no
        val ref=storage.reference
        val file = ref.child("$email/perfil.jpg")
        file.metadata.addOnSuccessListener {
            //existe el archivo de perfil le ponemos el suyo
            //creo la url
            file.downloadUrl.addOnSuccessListener {
                rellenarImageView(it)
            }
        }
            .addOnFailureListener {
                val defaultImg = ref.child("default/perfil.png")
                defaultImg.downloadUrl.addOnSuccessListener {
                    rellenarImageView(it)
                }
            }
    }


    /**
     * Funcion para rellenar la imagen por pantalla usando glide
     */
    private fun rellenarImageView(uri: Uri?) {
        val requestOptions = RequestOptions().transform(CircleCrop()) //la transformamos para que sea redonda
        Glide.with(binding.tvNombre.context)
            .load(uri)
            .centerCrop()
            .apply(requestOptions)
            .into(binding.ivAvatar)
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
