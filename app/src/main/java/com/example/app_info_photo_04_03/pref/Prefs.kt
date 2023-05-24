package com.example.app_info_photo_04_03.pref

import android.content.Context
import com.example.app_info_photo_04_03.model.Publicacion


class Prefs(c: Context) {

    val storage = c.getSharedPreferences("APP_1", 0)

    //ARRAYLIST

    //     var listaPublicaciones = mutableListOf<Publicacion>()

    //recoger email

    fun getEmail():String?{
        return  storage.getString("EMAIL","")
    }

    //guardar email
    fun setEmail(email:String){
        storage.edit().putString("EMAIL",email).apply()
    }

    fun deleteAll() {
        storage.edit().putString("EMAIL", "").apply()
        storage.edit().clear().apply()
    }

    fun getLike():Int{

        return storage.getInt("LIKES",0)
    }

    fun setLike(likes:Int){
        storage.edit().putInt("LIKES",likes).apply()
    }



    fun setPublicLikes(Likes:Int){

        storage.edit().putInt("LIKES",0).apply()

        //1 PARTE SETEAR POST POST QUE SE LE HAYA DADO ME GUSTA
        //2 CREAR ARRAYLIST DE TIPO PUBLICACION Y AÑADE EL POST QUE SE HA DADO MG
        // DESDE EL PUTSTRING LO VOY A SERIALIZAR Y .APPLY
        // PARA TAERLO SE TIENE QUE RETORNAR CON UN TIPO ARRAY TIPO PUBLICACIONES Y SE TIENE QUE
        //DESERIALIZAR

    }





}