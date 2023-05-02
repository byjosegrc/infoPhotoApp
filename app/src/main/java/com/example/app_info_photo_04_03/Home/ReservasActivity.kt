package com.example.app_info_photo_04_03.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.app_info_photo_04_03.Calendario.Calendario
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.databinding.ActivityReservasBinding
import com.example.app_info_photo_04_03.model.Publicacion
import com.example.app_info_photo_04_03.model.Reservas
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.firebase.database.FirebaseDatabase

class ReservasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReservasBinding


    //base de datos:

    lateinit var db: FirebaseDatabase

    //Preferencias
    lateinit var prefs: Prefs

    var nombre = ""
    var apellidos = ""
    var telefono = ""
    var diaCalendario = ""
    var tipoSesion = ""
    var tipoPack = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityReservasBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //REAL TIME:
        db = FirebaseDatabase.getInstance("https://infophoto-2023-default-rtdb.europe-west1.firebasedatabase.app/")

        //LLamada a la funcion que va estar ejecutandose siempre para la escucha de eventos:
        setListeners()
        //Titulo del activity:
        title = "RESERVA SESIONES"
    }



    private fun setListeners() {
/*
        //Escucha para la seleccion de la hora inicio de la reserva de sesion
        binding.etHoraIni.setOnClickListener(){
            showTimeInicio()
        }

        //Escucha para la seleccion de la hora fin de la reserva de sesion
        binding.etHoraFin.setOnClickListener(){
            showTimeFin()
        }

 */



        //Escucha para la seleccion del dia de la sesion de fotos:
        binding.etCalendario.setOnClickListener{
            showDatePickerDialog()
        }

        //boton para realizar la reserva

        binding.btnReserva.setOnClickListener{
            guardarReserva()
        }



    }

    private fun guardarReserva() {
        //Nombre
        nombre = binding.etNombre.text.toString().trim()
        if (nombre.isEmpty()) {
            binding.etNombre.error = "Este campo no puede estar vacio"
            binding.etNombre.requestFocus()
            return
        }
        //apellido
        apellidos = binding.etApellido.text.toString().trim()
        if (apellidos.isEmpty()) {
            binding.etApellido.error = "Este campo no puede estar vacio"
            binding.etApellido.requestFocus()
            return
        }
        //telefono
        telefono = binding.etTelefono.text.toString().trim()
        if (apellidos.isEmpty()) {
            binding.etTelefono.error = "Este campo no puede estar vacio"
            binding.etTelefono.requestFocus()
            return
        }
        //dia Calendario

        diaCalendario = binding.etCalendario.text.toString().trim()

        //spinner tipo sesion
        tipoSesion = binding.spnTipo.selectedItem.toString()

        //spinner tipo pack
        tipoPack = binding.spnPack.selectedItem.toString()

        val reservaSesiones = Reservas(nombre, apellidos , telefono, diaCalendario,tipoSesion,tipoPack)


        //  db.getReference("posts").child(post.fecha.toString()).setValue(post).addOnSuccessListener {

        reservaSesiones.nombre?.let {
            db.getReference("reservas").child(it).setValue(reservaSesiones).addOnSuccessListener {
                startActivity(Intent(this, HomeActivity::class.java))
                Toast.makeText(this,"SESIÓN RESERVADA CORRECTAMENTE", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }



    /*
    //Funcion hora fin de la sesión:
    //esta funcion carga el fragment del timePicker, para ello creo la clase reservaHoraFin

    private fun showTimeFin() {
        val timePicker = reservaHoraInicio { finSelected(it) }
        timePicker.show(supportFragmentManager, "horaFin")

    }

    //Funcion hora inicio de la sesión :

    //esta funcion carga el fragment del timePicker, para ello creo la clase reservaHoraInicio

    private fun showTimeInicio() {

        val timePicker = reservaHoraInicio { inicioSelected(it) }
        timePicker.show(supportFragmentManager, "Hora")

    }

//esta funcion nos dira la hora y minutos seleccionados al recoger la hora de inicio de la sesion
    private fun inicioSelected(timeIni: String) {
        binding.etHoraIni.setText("Reserva para las $timeIni")
    }

    private fun finSelected(timeFin: String) {
        binding.etHoraFin.setText("Reserva para las $timeFin")
    }


     */

    //----------------------------------------------------------------------------------------------



    //CALENDARIO:

    private fun showDatePickerDialog() {
        val datePicker = Calendario { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.etCalendario.setText(" $day / $month / $year")
    }












    //----------------------------------------------------------------------------------------------
    // MENU 3 PUNTOS:

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return super.onCreateOptionsMenu(menu)
    }

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



