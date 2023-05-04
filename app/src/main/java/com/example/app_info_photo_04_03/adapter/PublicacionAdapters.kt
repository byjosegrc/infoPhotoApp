package com.example.app_info_photo_04_03.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.model.Publicacion
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.firebase.database.FirebaseDatabase

class PublicacionAdapters(var lista: ArrayList<Publicacion> = ArrayList<Publicacion>(),
                          var onItemView: (Any?) -> Unit,
                          var onItemLike:(Any, Any)-> Unit): RecyclerView.Adapter<PostViewHolder>(){

    // var onItemLike:(Any, Any)-> Unit

    //preferencias de datos:
    lateinit var prefs: Prefs

    private val db = FirebaseDatabase.getInstance("https://infophoto-2023-default-rtdb.europe-west1.firebasedatabase.app/")

   class ViewHolder(val layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.layout_publicacion, parent, false)
        return PostViewHolder(inflater)


    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        val post = lista[position]
        val likes = post.likes!!.toString().toMutableList()
//        val liked = likes.contains(Prefs.getEmail)

        holder.render(lista[position], onItemView,onItemLike) //, onItemLike


        /* holder.binding.btnLikes.setOnClickListener{
         //    liked
         }*/


        holder.binding.btnCompartir.setOnClickListener{
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.contenido)
                type = "text/plain"

            }


            val shareIntent = Intent.createChooser(sendIntent, null)


            startActivity(shareIntent)

        }



    }

    private fun startActivity(shareIntent: Intent?) {

    }
}