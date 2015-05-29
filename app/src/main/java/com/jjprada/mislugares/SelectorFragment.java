package com.jjprada.mislugares;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Dr4ckO on 29/05/2015.
 */
public class SelectorFragment extends Fragment /*implements AdapterView.OnItemClickListener*/ {

    private final static int REQUEST = 10;
    private AdaptadorCursorLugares mAdaptadorLugares;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_selector, container, false);
        return vista;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*BaseAdapter adaptador = new AdaptadorCursorLugares(getActivity(), Lugares.listado());
        ListView listView = (ListView) getView().findViewById(R.id.fragment_listView);
        listView.setAdapter(adaptador);
        listView.setOnItemClickListener(this);*/
        mAdaptadorLugares = new AdaptadorCursorLugares(getActivity(), Lugares.listado());
        ListView listView = (ListView) getView().findViewById(R.id.fragment_listView);
        listView.setAdapter(mAdaptadorLugares);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idBD = Integer.parseInt(((TextView) view.findViewById(R.id.lista_idBD)).getText().toString());
                /*Intent i = new Intent(getActivity(), VistaLugarActivity.class);
                i.putExtra(VistaLugarActivity.EXTRA, idBD);
                startActivityForResult(i, REQUEST);*/

                ((MainActivity) getActivity()).muestraLugar(idBD);

            }
        });
    }

    /*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int idBD = Integer.parseInt(((TextView) view.findViewById(R.id.lista_idBD)).getText().toString());
        Intent i = new Intent(getActivity(), VistaLugarActivity.class);
        i.putExtra(VistaLugarActivity.EXTRA, idBD);
        startActivityForResult(i, REQUEST);
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST){        // No chequeamos el "resultCode", porque si volvemos de "VistaLugarActivity" con el boton de volver del dispositivo no actualizaría la lista al no considerarse como "RESULT_OK"
            mAdaptadorLugares.changeCursor(Lugares.listado());  // Actualizamos los datos por si han sido modificados
        }
    }
}
