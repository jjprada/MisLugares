package com.jjprada.mislugares;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Dr4ckO on 30/04/2015.
 */
public class AdaptadorLugares extends BaseAdapter {

    private LayoutInflater mInflater; // Crea Layouts a partir del XML
    private TextView mNombre, mDireccion;
    private ImageView mFoto;
    private RatingBar mValoracion;

    public AdaptadorLugares(Context contexto) {
        mInflater =(LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int posicion, View vistaReciclada, ViewGroup padre) {
        Lugar lugar = Lugares.elemento(posicion);

        if (vistaReciclada == null) {
            vistaReciclada= mInflater.inflate(R.layout.elemento_lista, null);
        }

        mNombre = (TextView) vistaReciclada.findViewById(R.id.lista_nombre);
        mNombre.setText(lugar.getNombre());

        mDireccion = (TextView) vistaReciclada.findViewById(R.id.lista_direccion);
        mDireccion.setText(lugar.getDireccion());

        int id;
        switch(lugar.getTipoLugar()) {
            case RESTAURANTE:   id = R.drawable.restaurante;    break;
            case BAR:           id = R.drawable.bar;            break;
            case COPAS:         id = R.drawable.copas;          break;
            case ESPECTACULO:   id = R.drawable.espectaculos;   break;
            case HOTEL:         id = R.drawable.hotel;          break;
            case COMPRAS:       id = R.drawable.compras;        break;
            case EDUCACION:     id = R.drawable.educacion;      break;
            case DEPORTE:       id = R.drawable.deporte;        break;
            case NATURALEZA:    id = R.drawable.naturaleza;     break;
            case GASOLINERA:    id = R.drawable.gasolinera;     break;
            default:            id = R.drawable.otros;          break;
        }

        mFoto = (ImageView) vistaReciclada.findViewById(R.id.lista_foto);
        mFoto.setImageResource(id);
        mFoto.setScaleType(ImageView.ScaleType.FIT_END);

        mValoracion = (RatingBar) vistaReciclada.findViewById(R.id.lista_valoracion);
        mValoracion.setRating(lugar.getValoracion());

        return vistaReciclada;
    }

    public int getCount() {
        return Lugares.size();
    }

    public Object getItem(int posicion) {
        return Lugares.elemento(posicion);
    }

    public long getItemId(int posicion) {
        return posicion;
    }
}
