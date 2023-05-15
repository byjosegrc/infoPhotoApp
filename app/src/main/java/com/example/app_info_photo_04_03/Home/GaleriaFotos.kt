package com.example.app_info_photo_04_03.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_info_photo_04_03.ApiProvider.ApiClient
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.adapter.PixabayAdapter
import com.example.app_info_photo_04_03.databinding.ActivityGaleriaFotosBinding
import com.example.app_info_photo_04_03.model.Hit
import kotlinx.coroutines.launch

class GaleriaFotos : AppCompatActivity() {
    lateinit var binding: ActivityGaleriaFotosBinding
    var lista = mutableListOf<Hit>()
    lateinit var adapter: PixabayAdapter
    var key = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGaleriaFotosBinding.inflate(layoutInflater)
        key = getString(R.string.api_key)
        setContentView(binding.root)
        setRecyclerView()
        traerImagenes("Almeria")
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.recPix.layoutManager = layoutManager
        adapter = PixabayAdapter(lista)
        binding.recPix.adapter = adapter

    }

    private fun traerImagenes(termino: String) {
        lifecycleScope.launch{
         //   val datos = ApiClient.service.getImages(key, termino)

          //  adapter.lista = datos.hits.toMutableList()
           // adapter.notifyDataSetChanged()
        }
    }
}