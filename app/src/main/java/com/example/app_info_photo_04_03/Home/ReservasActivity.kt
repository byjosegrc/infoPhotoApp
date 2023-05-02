package com.example.app_info_photo_04_03.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.app_info_photo_04_03.Calendario.Calendario
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.databinding.ActivityReservasBinding
import com.example.app_info_photo_04_03.model.Reservas
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
    var precio = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityReservasBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //REAL TIME:
        db = FirebaseDatabase.getInstance("https://infophoto-2023-default-rtdb.europe-west1.firebasedatabase.app/")

        //LLamada a la funcion que va estar ejecutandose siempre para la escucha de eventos:
        setListeners()
        obtenerReservas()
        //Titulo del activity:
        title = "RESERVA SESIONES"
    }

    private fun obtenerReservas(): LiveData<MutableList<Reservas>> {
        val mutableLista = MutableLiveData<MutableList<Reservas>>()
        val lista = mutableListOf<Reservas>()

            db.getReference("reservas").addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    lista.clear()
                    if (snapshot.exists()) {
                    for (item in snapshot.children){
                        val fechas = item.getValue(Reservas::class.java)
                        if(fechas!=null){
                            lista.add(fechas)
                        }
                    }
                        lista.sortBy { it.diaReservas }
                        mutableLista.value=lista
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        return mutableLista
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

        binding.btnCalcular.setOnClickListener{
            calcularPrecios()
        }



    }

    //esta funcion va a calcular segundo los datos que se han selecciado:
    private fun calcularPrecios() {

        //BAUTIZO:
        println("ENTROOO--------------------------------> $precio")
        binding.tvPrecio.text = precio

        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Bautizo")and binding.spnPack.getSelectedItem().toString().trim().equals("Normal")){
            precio = "250";
            binding.tvPrecio.text = precio
            println("ENTROOO--------------------------------> $precio  <-------------------")
        }
        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Bautizo")and binding.spnPack.getSelectedItem().toString().trim().equals("Medio")){
            precio = "350";
            binding.tvPrecio.text = ("$precio")
        }
        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Bautizo")and binding.spnPack.getSelectedItem().toString().trim().equals("Delux")){
            precio = "450";
            binding.tvPrecio.text = ("$precio")
        }

        //COMUNION:

        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Comunion")and binding.spnPack.getSelectedItem().toString().trim().equals("Normal")){
            precio = "400";
            binding.tvPrecio.text = ("$precio")
        }
        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Comunion")and binding.spnPack.getSelectedItem().toString().trim().equals("Medio")){
            precio = "600";
            binding.tvPrecio.text = ("$precio")
        }
        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Comunion")and binding.spnPack.getSelectedItem().toString().trim().equals("Delux")){
            precio = "800";
            binding.tvPrecio.text = ("$precio")
        }

        //BODA

        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Boda")and binding.spnPack.getSelectedItem().toString().trim().equals("Normal")){
            precio = "1000";
            binding.tvPrecio.text = ("$precio")
        }
        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Boda")and binding.spnPack.getSelectedItem().toString().trim().equals("Medio")){
            precio = "1200"
            binding.tvPrecio.text = ("$precio")
        }
        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Boda")and binding.spnPack.getSelectedItem().toString().trim().equals("Delux")){
            precio = "1500";
            binding.tvPrecio.text = ("$precio")
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
        //precio sesiones

        precio = binding.tvPrecio.text.toString().trim()
        if (precio.isEmpty()) {
            binding.tvPrecio.error = "TIENES QUE DARLE A CALCULAR PRECIO"
            binding.tvPrecio.requestFocus()
            return
        }


        val reservaSesiones = Reservas(nombre, apellidos , telefono, diaCalendario,tipoSesion,tipoPack,precio)


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



