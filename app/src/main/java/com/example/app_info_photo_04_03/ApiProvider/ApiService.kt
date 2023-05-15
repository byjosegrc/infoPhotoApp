package com.example.app_info_photo_04_03.ApiProvider

import com.example.app_info_photo_04_03.model.Pixabay
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
  @GET("api/")
    suspend fun getImages(@Query("key") key: String, @Query("q") q: String): Pixabay
}