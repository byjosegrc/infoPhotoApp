package com.example.app_info_photo_04_03.pref

import android.content.Context
import com.example.app_info_photo_04_03.SocialMedia.addPublicacion

class Prefs(c: Context) {
    val storage = c.getSharedPreferences("APP_1", 0)

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
}