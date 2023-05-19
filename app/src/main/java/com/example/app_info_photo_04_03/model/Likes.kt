package com.example.app_info_photo_04_03.model

data class Likes (
    var idPost : Long?=null,
    var idUser : ArrayList<String> = arrayListOf()
):java.io.Serializable

