package com.jjprada.mislugares;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Dr4ckO on 04/06/2015.
 */
public class DialogoSelectorFecha extends DialogFragment {

    private DatePickerDialog.OnDateSetListener escuchador;

    // SETTER PARA PODER ASIGNAR EL "LISTNER" A LA CLASE
    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener escuchador){
        this.escuchador = escuchador;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendario = Calendar.getInstance();           // Creamos objeto "Calendar" con la fecha actual

        Bundle args = this.getArguments();                      // Obtenemos los datos pasado por el "savedInstanceState"
        if (args != null) {                                     // Comprobamos si estan vacios o no
            long fecha = args.getLong("fecha");                 // Si no estan vacios, obtenemos la nueva fecha
            calendario.setTimeInMillis(fecha);                  // Aplicamos la nueva fecha a "Calendar" (sobrescribiendo la fecha actual)
        }

        int dia = calendario.get(Calendar.DAY_OF_MONTH);        // Obtenemos el "Dia" de la fecha indicada
        int mes = calendario.get(Calendar.MONTH);               // Obtnemos el "Mes" de la fecha indicada
        int ano = calendario.get(Calendar.YEAR);                // Obtnemos el "Ano" de la fecha indicada

        // Creamos el "Dialog" y lo devolvemos para que se muestre
        return new DatePickerDialog(getActivity(), escuchador, ano, mes, dia);
    }
}
