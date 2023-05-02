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
import com.example.app_info_photo_04_03.model.Publicacion
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
    //adapter del foro:
    lateinit var adapter: PublicacionAdapters

    var lista = ArrayList<Publicacion>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForoPublicacionesBinding.inflate(layoutInflater)

        //conexion a la base de datos de real time database de mi proyecto de firebase
        db = FirebaseDatabase.getInstance("https://infophoto-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        prefs = Prefs(this)
        setContentView(binding.root)
        setRecycler()
        traerPosts()
        setListeners()

        //URL DE LA BASE DE DATOS EN STORAGE:

        storage = FirebaseStorage.getInstance("gs://infophoto-2023.appspot.com")

        //funcion para configurar el Swipe del chat
        configSwipe()
        title="SOCIAL MEDIA"
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
        adapter = PublicacionAdapters(lista) {  post -> onItemView(post as String) }
      //   adapter =     PublicacionAdapters(lista,{onItemLike(it as String,it as Boolean)})

            //like,boton -> onItemLike(like as Int, boton as Boolean)}
        // {  post -> onItemView(post as String) }
        binding.recAutores.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.recAutores.layoutManager = layoutManager

        }

   /* private fun onItemLike(i: String, likeFav: Boolean){
    //logica de los likes:
        if(likeFav){
         i+1
        }
        else{
         i-1
        }


    }

    */

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
