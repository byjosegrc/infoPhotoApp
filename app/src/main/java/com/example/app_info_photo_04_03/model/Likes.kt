package com.example.app_info_photo_04_03.model

data class Likes (
    var idPost : Long?,
    var idUser : ArrayList<String> = arrayListOf()
)

