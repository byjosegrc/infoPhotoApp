package com.example.app_info_photo_04_03.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.model.Hit

class PixabayAdapter(var lista: MutableList<Hit>): RecyclerView.Adapter<PixaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PixaViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_galeria, parent, false)
        return PixaViewHolder(v)
    }


    /**
     *es una forma de presentar los datos que irán en la vista en una posición determinada.
     * Algo que debemos destacar es que este método se encuentra
     */
    override fun onBindViewHolder(holder: PixaViewHolder, position: Int) {
        holder.render(lista[position])
    }

    /**
     * getItemCount() que nos indica el numero de elementos que tiene la lista.
     */
    override fun getItemCount(): Int {
        return lista.size
    }
}