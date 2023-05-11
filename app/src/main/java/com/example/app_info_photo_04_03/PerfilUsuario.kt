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

    private fun setListeners() {
        binding.btnModificar.setOnClickListener {
            startActivity(Intent(this, ModificarPerfil::class.java))
        }
    }

    private fun obtenerDatos() {

        db.getReference("perfiles").addValueEventListener(object : ValueEventListener {



        override fun onDataChange(snapshot: DataSnapshot) {


            binding.prg.isVisible = false
            binding.prg1.isVisible = false
            binding.prg2.isVisible = false
            binding.prg3.isVisible = false


            if(snapshot.exists()){

                    val u = snapshot.child(prefs.getEmail().toString().replace(".", "-")).getValue(Perfil::class.java)



                    // val u = usuarios.getValue(Perfil::class.java)


           //     val datos = intent.getStringExtra("email")

          //      val prueba = u.getValue(Perfil::class.java)

                   if(u != null){
                        binding.tvNombre.text =  u.nombre //"Pepe"
                        binding.tvApellido.text = u.apellido //"Garcia"
                        binding.tvCiudad.text = u.ciudad //"Granada"
                        //recoger imagen
                        u.email?.let { recogerImagen(it) }

                       println("----------------------------------")

                    }
                 //   println("---------------$autor-----------------")
                    println("---------------$u-----------------")

             //   println("--------------------$prueba---------------")

            }
        }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

       /* db.getReference("perfiles").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val perfil = snapshot.child(intent.getStringExtra("email").toString().replace(".", "-")).getValue(Perfil::class.java)
                //if (perfil != null) {
                    binding.tvNombre.text =  perfil.nombre //"Pepe"
                    binding.tvApellido.text = perfil.apellido //"Garcia"
                    binding.tvCiudad.text = perfil.ciudad //"Granada"

                    //recoger imagen
                  //  recogerImagen(perfil.email)

               // }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }*/


  /*  private fun recogerImagen(email: String?) {
        val ref = storage.reference
        val imagen = ref.child("$email/perfil.jpg")
        imagen.metadata.addOnSuccessListener {
            //Existe el archivo
            Log.d("Perfil EMAIL", "existe la imagen")
            imagen.downloadUrl.addOnSuccessListener {
                Picasso.get().load(it).into(binding.ivAvatar)
            }

        } .addOnFailureListener {
            //No existe el archivo, sacamos el por defecto
            val default = storage.getReference("default/perfil.png")
            default.downloadUrl.addOnSuccessListener {
                Picasso.get().load(it).into(binding.ivAvatar)
            }
        }
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

    private fun rellenarImageView(uri: Uri?) {
        val requestOptions = RequestOptions().transform(CircleCrop())
        Glide.with(binding.tvNombre.context)
            .load(uri)
            .centerCrop()
            .apply(requestOptions)
            .into(binding.ivAvatar)
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
