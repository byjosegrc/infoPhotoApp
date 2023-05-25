package com.example.app_info_photo_04_03.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.model.Publicacion

class PublicacionAdapters(var lista: ArrayList<Publicacion> = ArrayList<Publicacion>(),
                          var onItemView: (Any?) -> Unit,
                          var onItemLike:(Any, Any)-> Unit): RecyclerView.Adapter<PostViewHolder>(){

   class ViewHolder(val layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.layout_publicacion, parent, false)
        return PostViewHolder(inflater)


    }
    /**
     *es una forma de presentar los datos que irán en la vista en una posición determinada.
     * Algo que debemos destacar es que este método se encuentra
     */
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.render(lista[position], onItemView,onItemLike)

    }

    /**
     *getItemCount() que nos indica el numero de elementos que tiene la lista.
     */
    override fun getItemCount(): Int {
        return lista.size
    }
}