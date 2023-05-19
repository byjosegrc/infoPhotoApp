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
        obtenerLikes()
        setListeners()




        //URL DE LA BASE DE DATOS EN STORAGE:

        storage = FirebaseStorage.getInstance("gs://infophoto-2023.appspot.com")

        //funcion para configurar el Swipe del chat
        configSwipe()
        title="RED SOCIAL"
    }



    private fun obtenerLikes() {
        db.getReference("likes").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaLikes.clear()
                if (snapshot.exists()) {
                    for (item in snapshot.children){
                        val likes = item.getValue(Likes::class.java)
                        if(likes!=null){
                            listaLikes.add(likes)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }




    @SuppressLint("ResourceAsColor")
    private fun configSwipe() {
        //colores
        binding.swipeMedia.setColorSchemeColors(R.color.rojo,R.color.amarillo,R.color.verde)

        binding.swipeMedia.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this,R.color.rojo))

        //listener para el swipe del chat
        binding.swipeMedia.setOnRefreshListener {

            //efecto de carga
            Handler(Looper.getMainLooper()).postDelayed({
                //dentro de 5 segundos se para la animacion:
                binding.swipeMedia.isRefreshing = false
            },3000)

        }
    }

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
                    lista.sortBy { posts -> posts.fecha }
                    adapter.lista = lista
                    adapter.notifyDataSetChanged()
                    binding.recAutores.scrollToPosition(lista.size - 1)

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setListeners() {
        binding.btnAdd.setOnClickListener {
            irAddActivity()
        }
    }
//{ onItemView(it as String) }
    private fun irAddActivity() {
        startActivity(Intent(this, addPublicacion::class.java))
    }

    private fun setRecycler() {
        adapter = PublicacionAdapters(lista,listaLikes,{
                post -> onItemView(post as String) }) {
                publicacion,modeloLikes -> onItemLike(publicacion as Publicacion, modeloLikes as Likes)
        }
        binding.recAutores.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.recAutores.layoutManager = layoutManager

        }


    @SuppressLint("SuspiciousIndentation")

    private fun onItemLike(publicacion: Publicacion, modeloLikes: Likes){


        var users= ArrayList(modeloLikes.idUser)




        if(users.contains(email)){
            var usuarioClone = ArrayList(users)

            if(users.indexOf(email)==1){
                users.removeFirst()
            }else{
                users.removeAt(users.indexOf(email))
            }


            println("----------------2------------"+users+"---------------2------------")


            var modelosLikes = Likes(publicacion.fecha, usuarioClone)
            var user = Publicacion(publicacion.fecha,publicacion.likes-1,publicacion.autor,publicacion.contenido)
            actualizarLike(user,modelosLikes)
        }else{
            var usuarios = ArrayList(users)


            usuarios.add(email)

            println("--------------1--------------"+usuarios+"-------------1--------------")

            var modelosLikes = Likes(publicacion.fecha, usuarios)

            var user = Publicacion(publicacion.fecha,publicacion.likes+1,publicacion.autor,
                publicacion.contenido)
            actualizarLike(user,modelosLikes)
        }



    }

    private fun actualizarLike(autor : Publicacion,likes: Likes) {
        db.getReference("posts").child(autor.fecha.toString()).setValue(autor).addOnSuccessListener {
            db.getReference("likes").child(autor.fecha.toString()).setValue(likes).addOnSuccessListener {
                traerPosts()
                obtenerLikes()
            }
        }
    }





    private fun onItemView(it: String) {
        startActivity(Intent(this, PerfilUsuario::class.java).putExtra("email", it))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.inicio -> {
                startActivity(Intent(this, HomeActivity::class.java))
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRestart() {
        traerPosts()
        super.onRestart()
    }

    private fun mostrarAlerta(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

}
