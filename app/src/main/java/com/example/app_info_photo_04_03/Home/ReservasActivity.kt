package com.example.app_info_photo_04_03.Home

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View.inflate
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ComplexColorCompat.inflate
import com.example.app_info_photo_04_03.Calendario.Calendario
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.databinding.ActivityReservasBinding
import com.example.app_info_photo_04_03.model.Reservas
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.imaginativeworld.whynotimagecarousel.databinding.ItemCarouselBinding.inflate


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


    //Lista Fechas:
    var listaFechas = mutableListOf<String>()

    /**
     *Esta es la funcion on  ejecutas la lógica de arranque básica de la aplicación que debe ocurrir una
     * sola vez en toda la vida de la actividad. Por ejemplo, tu implementación de onCreate() podría vincular
     * datos a listas, asociar la actividad con un ViewModel y crear instancias de algunas variables de alcance de clase.
     */
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
        title =" \uD83D\uDCF7 𝐑𝐄𝐒𝐄𝐑𝐕𝐀 𝐃𝐄 𝐒𝐄𝐒𝐈𝐎𝐍𝐄𝐒"


    }


    /**
     * Funcion para obtener las reservar
     */
    private fun obtenerReservas(){
        db.getReference("reservas").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //  lista.clear()
                if (snapshot.exists()) {
                    for (item in snapshot.children){
                        val reservas = item.getValue(Reservas::class.java)
                        if(reservas!=null){
                            listaFechas.add(reservas.diaReservas!!)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    /**
     * Funcion para los listener de la clase reserva
     *
     *
     */
    private fun setListeners() {

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

        binding.ibtInfo.setOnClickListener{
            abrirDialgoPack()
        }





    }


    /**
     * Esta funcion cuando se llama abre un cuadro de dialogo
     * esto muestra la información sobre los pack de las sesiones
     */
    private fun abrirDialgoPack() {
        println("---------------------------")
        val builder = AlertDialog.Builder(this, R.style.TransparentDialog)
        val inflate = layoutInflater.inflate(R.layout.activity_dialogo_tipo_pack, null)
        builder.setView(inflate)
        builder.show() //monstrarlo
    }


    /**
     * esta funcion va a calcular segundo los datos que se han selecciado:
     * En el siguiente código muestro que cuando se llama a la función por cada
     * condición if se va a comprobar por las opciones seleccionadas por pantalla y
     * se devolverá un precio dependiendo de lo seleccionado.
     * - Las opciones seleccionas son el tipo de sesión y tipo de pack seleccionado:
     */

    private fun calcularPrecios() {

        //BAUTIZO:
        println("ENTROOO--------------------------------> $precio")
        binding.tvPrecio.text = precio

        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Bautizo")and binding.spnPack.getSelectedItem().toString().trim().equals("Estándar")){
            precio = "150€";
            binding.tvPrecio.text = precio
            println("ENTROOO--------------------------------> $precio  <-------------------")
        }
        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Bautizo")and binding.spnPack.getSelectedItem().toString().trim().equals("Medio")){
            precio = "250€";
            binding.tvPrecio.text = ("$precio")
        }
        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Bautizo")and binding.spnPack.getSelectedItem().toString().trim().equals("Delux")){
            precio = "350€";
            binding.tvPrecio.text = ("$precio")
        }

        //COMUNION:

        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Comunion")and binding.spnPack.getSelectedItem().toString().trim().equals("Estándar")){
            precio = "350€";
            binding.tvPrecio.text = ("$precio")
        }
        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Comunion")and binding.spnPack.getSelectedItem().toString().trim().equals("Medio")){
            precio = "450€";
            binding.tvPrecio.text = ("$precio")
        }
        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Comunion")and binding.spnPack.getSelectedItem().toString().trim().equals("Delux")){
            precio = "480€";
            binding.tvPrecio.text = ("$precio")
        }

        //BODA

        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Boda")and binding.spnPack.getSelectedItem().toString().trim().equals("Estándar")){
            precio = "300€";
            binding.tvPrecio.text = ("$precio")
        }
        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Boda")and binding.spnPack.getSelectedItem().toString().trim().equals("Medio")){
            precio = "500€"
            binding.tvPrecio.text = ("$precio")
        }
        if (binding.spnTipo.getSelectedItem().toString().trim().equals("Boda")and binding.spnPack.getSelectedItem().toString().trim().equals("Delux")){
            precio = "600€";
            binding.tvPrecio.text = ("$precio")
        }

    }

    /**
     * Funcion para guardar la reserva de la sesión datos personales, dia de la sesion,
     * ubicación, tipo de sesión y tipo de pack
     *
     */
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
        if (telefono.isEmpty() &&  telefono.length>8 && telefono.length<10) {
            binding.etTelefono.error = "Introduce un numero de telefono valido"
            binding.etTelefono.requestFocus()
            return
        }


        //calendario:
        diaCalendario = binding.etCalendario.text.toString().trim()
        if(listaFechas.contains(diaCalendario)){
            Toast.makeText(this,"SELECCIONE OTRO DIA, EL SELECCIONADO NO ESTA DISPONIBLE", Toast.LENGTH_SHORT).show()
            return
        }

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
                Toast.makeText(this,"SU RESERVA HA SIDO TRAMITADA", Toast.LENGTH_SHORT).show()
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








