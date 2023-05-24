package com.example.app_info_photo_04_03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.app_info_photo_04_03.Home.HomeActivity
import com.example.app_info_photo_04_03.databinding.LoginMainBinding
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider



class LoginActivity : AppCompatActivity() {
    lateinit var binding: LoginMainBinding
    lateinit var prefs: Prefs
    private var responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            //recogemos los datos de la activity de gogle
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val  cuenta = task.getResult(ApiException::class.java)//aqui tengo la cuenta me valido con ella
                if (cuenta!=null){
                    //cogemos las credenciales
                    val credenciales= GoogleAuthProvider.getCredential(cuenta.idToken,null)
                    FirebaseAuth.getInstance().signInWithCredential(credenciales).addOnCompleteListener {
                        if (it.isSuccessful){
                            prefs.setEmail(cuenta.email?:"")
                            Log.d("LOGIN EMAIL", cuenta.email.toString())
                            Toast.makeText(this, "INICIO CORRECTAMENTE EN LA APP", Toast.LENGTH_LONG).show()
                            irHome()
                        }else{
                            Toast.makeText(this,"ERROR EN EL INICIO DE SESIÓN",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }catch (e: ApiException){
                println(e.message)
            }
        }
    }
    /**
     *Esta es la funcion on  ejecutas la lógica de arranque básica de la aplicación que debe ocurrir una
     * sola vez en toda la vida de la actividad. Por ejemplo, tu implementación de onCreate() podría vincular
     * datos a listas, asociar la actividad con un ViewModel y crear instancias de algunas variables de alcance de clase.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= LoginMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //TITULO
        title="AUTENTICACIÓN"

        //PONER EN EL CENTRO

       // supportActionBar?.setDisplayOptions((androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM))
        //supportActionBar?.setCustomView(R.layout.login_main)

        prefs= Prefs(this)
        comprobarSesion()
        setListener()
    }


    /**
     *Esta es la funcion de listener para el boton de login abrir el dialogo de cuentas de google y
     * asi iniciar sesion en la app
     */
    private fun setListener() {

        binding.btnGoogle.setOnClickListener{
            login()
        }
    }
    /**
     *Esta es la funcion login la cual realiza el login del user en la app
     */
    private fun login() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //lo encontramos en el json de la aplicación
            .requestIdToken("425372330535-pbeh3ackctikjq4nm4msgq2pc3pgjtjl.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)

        //para que si cierro sesion me de a elegir un usuario y no me valide con el ultimo
        googleClient.signOut()

        //responseLauncher
        responseLauncher.launch(googleClient.signInIntent)
    }

    /**
     * Esta funcion hace que mande al usuario a la venta de HOME cuando inicia sesión correctamente
     */
    private fun irHome(){
        startActivity(Intent(this, HomeActivity::class.java))
    }

    /**
     * Esta funcion va a comprobar que si la sesion esta inicia en vez de volver de preguntar al usuario sus datos de acceso
     * gracias a las preferencia en las que esta guarda la sesion de tal usuario por el identificardor de correo
     */
    private fun comprobarSesion() {
        val email= prefs.getEmail()
        Log.d("------------------------->EMAIL", email.toString())
        if (!email.isNullOrEmpty()){
            irHome()
        }
    }
}