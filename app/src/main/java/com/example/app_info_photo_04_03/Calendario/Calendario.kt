package com.example.app_info_photo_04_03.Calendario

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class Calendario(val listener: (day: Int, month: Int, year: Int) -> Unit): DialogFragment(),
    DatePickerDialog.OnDateSetListener{
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth,month,year)
    }


    /**
     * Funcion para crearDialogo del calendario
     * Para recoger el DIA, MES, AÑO por pantalla para la reserva de sesiones
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val picker = DatePickerDialog(activity as Context, this, year, month+1, day)
        //picker.datePicker.maxDate = c.timeInMillis //para el maximo de dias
        picker.datePicker.minDate = c.timeInMillis //para el minimo de dias
        //con lo anterior lo que hago es limitar el calendario a que solo se puedan hacer reserva en ese mes actual
        return picker
    }

}
