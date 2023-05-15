package com.example.app_info_photo_04_03.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.app_info_photo_04_03.databinding.RecyclerGaleriaBinding
import com.example.app_info_photo_04_03.model.Hit
import com.squareup.picasso.Picasso

class PixaViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val binding = RecyclerGaleriaBinding.bind(v)

    fun render(hit: Hit) {
        Picasso.get().load(hit.webformatURL).into(binding.ivImagen)
    }

}