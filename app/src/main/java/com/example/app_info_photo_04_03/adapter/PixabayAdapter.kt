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

    override fun onBindViewHolder(holder: PixaViewHolder, position: Int) {
        holder.render(lista[position])
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}