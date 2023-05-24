package com.example.app_info_photo_04_03.SocialMedia

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_info_photo_04_03.Home.HomeActivity
import com.example.app_info_photo_04_03.PerfilUsuario
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.adapter.PublicacionAdapters

import com.example.app_info_photo_04_03.databinding.ActivityForoPublicacionesBinding
import com.example.app_info_photo_04_03.model.Likes
import com.example.app_info_photo_04_03.model.Perfil
import com.example.app_info_photo_04_03.model.Publicacion
import com.example.app_info_photo_04_03.model.Reservas
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlin.concurrent.thread

class MediaPublicaciones : AppCompatActivity() {
    //variables:
    lateinit var storage: FirebaseStorage

    lateinit var binding: ActivityForoPublicacionesBinding
    //variable para la base de datos:
    lateinit var db: FirebaseDatabase
    //preferencias de datos:
    lateinit var prefs: Prefs

    var email = ""
    //adapter del foro:
    lateinit var adapter: PublicacionAdapters


    var listaLikes = ArrayList<Likes>()

    var lista = ArrayList<Publicacion>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityForoPublicacionesBinding.inflate(layoutInflater)

        //conexion a la base de datos de real time database de mi proyecto de firebase
        db = FirebaseDatabase.getInstance("https://infophoto-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        prefs = Prefs(this)
         email = prefs.getEmail().toString()
        setContentView(binding.root)
        setRecycler()
        traerPosts()
        setListeners()
        //URL DE LA BASE DE DATOS EN STORAGE:

        storage = FirebaseStorage.getInstance("gs://infophoto-2023.appspot.com")

        //funcion para configurar el Swipe del chat
        configSwipe()
        title="\uD83D\uDCAC  \uD835\uDC11\uD835\uDC04\uD835\uDC03 \uD835\uDC12\uD835\uDC0E\uD835\uDC02\uD835\uDC08\uD835\uDC00\uD835\uDC0B"
    }

    @SuppressLint("ResourceAsColor")
    private fun configSwipe() {
        binding.swipeMedia.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this,
            R.color.botonSubir
        ))
        //listener para el swipe del chat
        binding.swipeMedia.setOnRefreshListener {
            //efecto de carga
            Handler(Looper.getMainLooper()).postDelayed({
                //dentro de 5 segundos se para la animacion:
                binding.swipeMedia.isRefreshing = false
            },5000)

        }
    }

    /**
     *
     */

    private fun traerPosts() {
        db.getReference("posts").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.pgbCarga.isVisible = false
                lista.clear()
                if (snapshot.exists()) {
                    for (item in snapshot.children) {
                        var post = item.getValue(Publicacion::class.java)
                        if (post != null) {
                            lista.add(post)
                        }
                    }
                    lista.sortBy {
                            posts -> posts.fecha
                    }
                    adapter.lista = lista
                    adapter.notifyDataSetChanged()
                 //   binding.recAutores.scrollToPosition(lista.size - 1)

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    /**
     *
     */
    private fun setListeners() {
        binding.btnAdd.setOnClickListener {
            irAddActivity()
        }
    }

    /**
     *
     */
    private fun irAddActivity() {
        startActivity(Intent(this, addPublicacion::class.java))
    }

    /**
     *
     */
    private fun setRecycler() {
        adapter = PublicacionAdapters(lista,{
                post -> onItemView(post as String) }) {
                email,publi -> onItemLike(email as String, publi as Publicacion)
        }
        binding.recAutores.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.recAutores.layoutManager = layoutManager


        }


    @SuppressLint("SuspiciousIndentation")
    /**
     *
     */
        private fun onItemLike(email: String?, publi: Publicacion) {
            var encontradoLike = false
            val comprobacion = ArrayList<String>()

            db.getReference("likes").child(publi.fecha.toString()).child("idUser").get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val dataSnapshot = task.result
                        for (childSnapshot in dataSnapshot.children) {
                            val data = childSnapshot.getValue(String::class.java)
                            data?.let {
                                comprobacion.add(it)
                            }
                        }

                        // Handle the data-dependent logic here
                        for (likes in comprobacion) {
                            if (likes == email.toString().replace(".", "-")) {
                                encontradoLike = true
                            }
                        }

                        // Perform actions based on the retrieved data
                        if (!encontradoLike) {
                            db.getReference("likes").child(publi.fecha.toString())
                                .child("idUser")
                                .child(email.toString().replace(".", "-"))
                                .setValue(email.toString().replace(".", "-"))
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        traerPosts()
                                        println("--------------------------------------- DA LIKE")

                                    } else {
                                        // Handle the error, if any
                                    }
                                }
                            db.getReference("posts").child(publi.fecha.toString()).child("likes")
                                .setValue(comprobacion.size).addOnCanceledListener {
                                    traerPosts()
                                }
                        } else {
                            db.getReference("likes").child(publi.fecha.toString())
                                .child("idUser")
                                .child(email.toString().replace(".", "-")).removeValue()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        println("--------------------------------------- QUITA LIKE")
                                    } else {
                                        // Handle the error, if any
                                    }
                                }
                            db.getReference("posts").child(publi.fecha.toString()).child("likes")
                                .setValue(comprobacion.size).addOnCanceledListener {
                                    traerPosts()
                                }
                        }
                        comprobacion.clear()
                        db.getReference("likes").child(publi.fecha.toString()).child("idUser").get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val dataSnapshot = task.result
                                    for (childSnapshot in dataSnapshot.children) {
                                        val data = childSnapshot.getValue(String::class.java)
                                        data?.let {
                                            comprobacion.add(it)
                                        }
                                    }
                                }
                                db.getReference("posts").child(publi.fecha.toString()).child("likes")
                                    .setValue(comprobacion.size).addOnCanceledListener {
                                        traerPosts()
                                    }
                            }
                    }
                    else {
                        // Handle the error, if any
                    }
                }
        traerPosts()
        }


    /**
     *
     */
    private fun onItemView(it: String) {
        Toast.makeText(this,"¿QUIERES PUBLICAR UN POST?",Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, PerfilUsuario::class.java))
    }

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
        when (item.itemId) {
            R.id.inicio -> {
                startActivity(Intent(this, HomeActivity::class.java))
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    /**
     *hace que el usuario pueda ver la actividad mientras la app se prepara para que esta entre en primer plano
     * y se convierta en interactiva. Por ejemplo, este método es donde la app inicializa el código que mantiene la IU.
     */
    override fun onRestart() {
        traerPosts()
        super.onRestart()
    }
}
