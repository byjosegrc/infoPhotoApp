package com.example.app_info_photo_04_03.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.databinding.LayoutPublicacionBinding
import com.example.app_info_photo_04_03.model.Likes
import com.example.app_info_photo_04_03.model.Publicacion
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date

class PostViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val binding = LayoutPublicacionBinding.bind(v)
    //preferencias de datos:
    lateinit var prefs: Prefs
    lateinit var db: FirebaseDatabase

    /**
     * funcion para rellenar el contenido de datos de cada post el autor, fecha publicacion, numeros de likes y contenido del post
     */
    fun render(posts: Publicacion, onItemView: (Any?) -> Unit
               , onItemLike: (Any, Any) -> Unit) {


        //conexion a la base de datos de real time database de mi proyecto de firebase
        db = FirebaseDatabase.getInstance("https://infophoto-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        prefs = Prefs(binding.tvEmail.context)
        val email:String? = prefs.getEmail()

        binding.tvEmail.text = posts.autor
        binding.tvPost.text = posts.contenido
        binding.tvLikes.text = posts.likes.toString()
        binding.tvFecha.text = convertirFecha(posts.fecha!!)

        comprobarLikes(email,posts)


        itemView.setOnClickListener {
            onItemView(posts.autor)
        }


        binding.btnLike.setOnClickListener{
            if (email != null) {
                onItemLike(email,posts)
            }
            comprobarLikes(email,posts)
        }


    }


    /**
     * Funcion para comprar si se ha dado like cambiar el corazon a rojo de lo contrario estara vacio
     *
     * Para ello tengo que leer en firebase si se ha dado like al post la "variable encontrado se podria a true"
     *
     * si encontrado es true se pondria a corazon vacio
     *
     * si en caso de no ser encontrado se podria a rojo
     *
     */
    private fun comprobarLikes(email: String?, posts: Publicacion) {

        var encontrado = false


        db.getReference("likes").child(posts.fecha.toString()).child("idUser").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val dataSnapshot = task.result
                    for (childSnapshot in dataSnapshot.children) {
                        val data = childSnapshot.getValue(String::class.java)
                        if(data.equals(email.toString().replace(".","-"))){
                            encontrado=true
                        }
                    }
                    // Perform actions based on the retrieved data

                    if (!encontrado) {
                        binding.btnLike.setImageResource(R.drawable.ic_like_uno)
                    } else {
                        binding.btnLike.setImageResource(R.drawable.ic_like_dos)
                    }
                } else {
                }
            }


    }


    /**
     * Funcion para convertir la fecha a milisegundos
     */
    private fun convertirFecha(fecha: Long): String {
        val date = Date(fecha)
        val format = SimpleDateFormat("dd/MM/yyyy h:m:ss")
        return format.format(date)
    }








}
