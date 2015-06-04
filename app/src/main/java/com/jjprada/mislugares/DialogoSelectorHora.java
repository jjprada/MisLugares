package com.jjprada.mislugares;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by Dr4ckO on 02/06/2015.
 */
public class DialogoSelectorHora extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener escuchador;

    // SETTER PARA PODER ASIGNAR EL "LISTNER" A LA CLASE
    public void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener escuchador){
        this.escuchador = escuchador;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendario = Calendar.getInstance();           // Creamos objeto "Calendar" con la fecha actual

        Bundle args = this.getArguments();                      // Obtenemos los datos pasado por el "savedInstanceState"
        if (args != null) {                                     // Comprobamos si estan vacios o no
            long fecha = args.getLong("fecha");                 // Si no están vacios, obtenemos la nueva fecha
            calendario.setTimeInMillis(fecha);                  // Aplicamos la nueva fecha a "Calendar" (sobrescribiendo la fecha actual)
        }

        int hora = calendario.get(Calendar.HOUR_OF_DAY);        // Obtenemos la "Hora" de la fecha indicada
        int minuto = calendario.get(Calendar.MINUTE);           // Obtnemos el "Minuto" de la fecha indicada

        // Creamos el "Dialog" y lo devolvemos para que se muestre
        return new TimePickerDialog(getActivity(), escuchador, hora, minuto, DateFormat.is24HourFormat(getActivity()));
    }
}
