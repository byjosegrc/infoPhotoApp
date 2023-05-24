package com.example.app_info_photo_04_03

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.app_info_photo_04_03.Home.HomeActivity
import com.example.app_info_photo_04_03.databinding.ActivityModificarPerfilBinding
import com.example.app_info_photo_04_03.model.Perfil
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class ModificarPerfil : AppCompatActivity() {


    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.ivPerfil.setImageURI(uri)
            guardarImagen(uri)
        }
    }


    /**
     *
     */
    private fun guardarImagen(uri: Uri) {
        val ref = storage.reference
        val imagen = ref.child("${prefs.getEmail()}/perfil.jpg")
        val upload = imagen.putFile(uri).addOnSuccessListener {
            //Se ha subido la imagen
            Toast.makeText(this, "Se ha subido la imagen", Toast.LENGTH_SHORT).show()
        } .addOnFailureListener {
            //No se ha subido la imagen
            Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
        }
    }


    lateinit var storage: FirebaseStorage
    lateinit var binding: ActivityModificarPerfilBinding
    lateinit var prefs: Prefs
    lateinit var db: FirebaseDatabase
    var nombre = ""
    var apellido = ""
    var ciudad = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarPerfilBinding.inflate(layoutInflater)
        prefs = Prefs(this)
        //recogo el email y se lo pongo al tv

        binding.tvCorreo.text = prefs.getEmail()

        //URL DE LA BASE DE DATOS EN STORAGE:

        storage = FirebaseStorage.getInstance("gs://infophoto-2023.appspot.com")
        //URL DE LA BASE DE DATOS EN REAL TIME DATABASE

        db = FirebaseDatabase.getInstance("https://infophoto-2023-default-rtdb.europe-west1.firebasedatabase.app/")

        setContentView(binding.root)
        setListeners()
        title = "\uD83D\uDC65 \uD835\uDC0C\uD835\uDC0E\uD835\uDC03\uD835\uDC08\uD835\uDC05\uD835\uDC08\uD835\uDC02\uD835\uDC00\uD835\uDC11 \uD835\uDC14\uD835\uDC12\uD835\uDC14\uD835\uDC00\uD835\uDC11\uD835\uDC08\uD835\uDC0E"
    }


    /**
     *Funcion de listeners de los botones del activity modificar perfil
     * Botones: btnCancelar, btnSubirFoto ,btnGuardar
     *
     * -> btnCancelar: cancela la accion
     * -> btnSubirFoto: boton para abrir el dialogo de seleccion de fotos en el dispositivo
     * -> btnGuardar: boton que guardara los datos modificados del usuario modificados
     * */
    private fun setListeners() {
        binding.btnCancelar.setOnClickListener {
            finish()
        }
        binding.btnSubirFoto.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.btnGuardar.setOnClickListener {
            guardarAutor()
        }
    }


    /**
     * Funcion que guarda los datos del usuario: nombre, apellidos, ciudad
     */
    private fun guardarAutor() {
        //Nombre
        nombre = binding.etNombre.text.toString().trim()
        if (nombre.isEmpty()) {
            binding.etNombre.error = "Este campo no puede estar vacio"
            binding.etNombre.requestFocus()
            return
        }
        //apellido
        apellido = binding.etApellido.text.toString().trim()
        if (apellido.isEmpty()) {
            binding.etApellido.error = "Este campo no puede estar vacio"
            binding.etApellido.requestFocus()
            return
        }
        //ciudad
        val ciudad = binding.spnCiudad.selectedItem.toString()

        //recoger email
        val autor = Perfil(prefs.getEmail(), nombre, apellido , ciudad)

        //conexion db
        db.getReference("perfiles").child(prefs.getEmail().toString().replace(".", "-")).setValue(autor).addOnSuccessListener {
            startActivity(Intent(this, HomeActivity::class.java))
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