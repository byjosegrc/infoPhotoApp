package com.example.app_info_photo_04_03.pref

import android.content.Context


class Prefs(c: Context) {

    val storage = c.getSharedPreferences("APP_1", 0)


    /**
     * Recoger Email
     */
    fun getEmail():String?{
        return  storage.getString("EMAIL","")
    }

    /**
     * Guardar Email
     */
    fun setEmail(email:String){
        storage.edit().putString("EMAIL",email).apply()
    }

    /**
     * deleteAll es para borrar las preferencias cuando se cierra sesion
     */
    fun deleteAll() {
        storage.edit().putString("EMAIL", "").apply()
        storage.edit().clear().apply()
    }
}