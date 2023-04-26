package com.example.app_info_photo_04_03.model

import com.google.firebase.database.Exclude


data class Publicacion(
    var fecha: Long? = System.currentTimeMillis(),
    var likes: Int = 0,
    var autor: String? = null,
    var contenido: String? = null
):java.io.Serializable