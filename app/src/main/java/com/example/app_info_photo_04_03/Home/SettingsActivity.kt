package com.example.app_info_photo_04_03.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.app_info_photo_04_03.PerfilUsuario
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.databinding.ActivitySettingsBinding
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingsBinding
    lateinit var prefs: Prefs

    /**
     *Esta es la funcion on  ejecutas la lógica de arranque básica de la aplicación que debe ocurrir una
     * sola vez en toda la vida de la actividad. Por ejemplo, tu implementación de onCreate() podría vincular
     * datos a listas, asociar la actividad con un ViewModel y crear instancias de algunas variables de alcance de clase.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title="\uD83D\uDD29 \uD835\uDC00\uD835\uDC09\uD835\uDC14\uD835\uDC12\uD835\uDC13\uD835\uDC04\uD835\uDC12"


        //Creo la variable de tema oscuro:
      //  val swDarkMode = findViewById<SwitchMaterial>(R.id.switchDarkMode)

        //SI SE SELECCIONA EL SWITCH:

    /*   binding.switchDarkMode.setOnCheckedChangeListener { _, _ ->
            //Si se cumple que esta seleccionado
            if (binding.switchDarkMode.isChecked){
                //funcion para tema oscuro ON
                //enableDarkMode()
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                delegate.applyDayNight()
            }else{ //si no ...
                //funcion para tema oscuro OFF
               // disableDarkMode()
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                delegate.applyDayNight()
            }


    }*/

        setListener()



        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> startActivity(Intent(this, HomeActivity::class.java))
                R.id.profile -> startActivity(Intent(this, PerfilUsuario::class.java))
                R.id.settings -> startActivity(Intent(this, SettingsActivity::class.java))
                else ->{
                }
            }
            true
        }


    }


    /**
     * Funcion para el listener de los botones  btnOscuro y  btnClaro
     *
     * Donde ...
     *
     * btnClaro -> Habilita el tema claro
     *
     * btnOscuro -> Habilita el tema oscuro
     *
     */
    private fun setListener() {
        //tema oscuro

      binding.btnOscuro.setOnClickListener{
            enableDarkMode()
        }

        //tema claro

        binding.btnClaro.setOnClickListener{
            disableDarkMode()
       }
    }


    /**
     *     ACTIVA EL TEMA OSCURO EN LA APP
     */

    private fun enableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    /**
     *   DESACTIVA EL TEMA OSCURO EN LA APP
     */

    private fun disableDarkMode(){
        Log.d("EMAIL  SITCH", "entre en la funcion")
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        delegate.applyDayNight()
    }


    /**
     * funcion de onCreateOptionsMenu() es el que nos va a permitir inflar nuestro menú, es decir, hacer
     * que las opciones definidas en el fichero XML tengan su propia apariencia dentro de nuestra aplicación Android.
     */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1,menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * En este método, puedes aumentar el recurso de menú (definido en XML) hacia el Menu proporcionado
     * en la devolución de llamada
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.inicio ->{
                startActivity(Intent(this, HomeActivity::class.java))
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}