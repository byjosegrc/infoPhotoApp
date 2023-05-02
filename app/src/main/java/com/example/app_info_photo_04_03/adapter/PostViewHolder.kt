package com.example.app_info_photo_04_03.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.databinding.LayoutPublicacionBinding
import com.example.app_info_photo_04_03.model.Publicacion
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date

class PostViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val binding = LayoutPublicacionBinding.bind(v)
    //preferencias de datos:
    lateinit var prefs: Prefs
    private var likeFav = false
    lateinit var db: FirebaseDatabase
    fun render(posts: Publicacion, onItemView: (Any?) -> Unit) { //,  onItemLike: (Any, Any) -> Unit


        //conexion a la base de datos de real time database de mi proyecto de firebase
        db = FirebaseDatabase.getInstance("https://infophoto-2023-default-rtdb.europe-west1.firebasedatabase.app/")

        prefs = Prefs(binding.tvEmail.context)

        val email = prefs.getEmail()

        binding.tvEmail.text = posts.autor
        binding.tvPost.text = posts.contenido
        binding.tvLikes.text = posts.likes.toString()
        binding.tvFecha.text = convertirFecha(posts.fecha!!)
     //   val likes = Publicacion.likes!!
      //  val liked = likes.contains()
        itemView.setOnClickListener {
            onItemView(posts.autor)
        }

        binding.btnLike.setOnClickListener{
            cambiarImgLike()
        }
    }

    private fun convertirFecha(fecha: Long): String {
        val date = Date(fecha)
        val format = SimpleDateFormat("dd/MM/yyyy h:m:ss")
        return format.format(date)
    }

    private fun cambiarImgLike() {
        likeFav = !likeFav
        if(likeFav){
            binding.btnLike.setImageResource(R.drawable.ic_like_dos)
        }else{
            binding.btnLike.setImageResource(R.drawable.ic_like_uno)
        }
    }




}
