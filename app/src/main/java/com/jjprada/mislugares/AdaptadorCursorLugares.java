package com.jjprada.mislugares;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Dr4ckO on 22/05/2015.
 */
public class AdaptadorCursorLugares extends CursorAdapter {

    private LayoutInflater mInflador;                       // Crea Layouts a partir del XML
    private TextView mNombre, mDireccion, mDistancia;
    private ImageView mFoto;
    private RatingBar mValoracion;

    public AdaptadorCursorLugares (Context context, Cursor cursor){
        super(context, cursor, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        mInflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflador.inflate(R.layout.elemento_lista, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        mNombre = (TextView) view.findViewById(R.id.lista_nombre);
        mNombre.setText(cursor.getString(cursor.getColumnIndex("nombre")));

        mDireccion = (TextView) view.findViewById(R.id.lista_direccion);
        mDireccion.setText(cursor.getString(cursor.getColumnIndex("direccion")));

        mFoto = (ImageView) view.findViewById(R.id.lista_foto);
        int tipo = cursor.getInt(cursor.getColumnIndex("tipo"));
        mFoto.setImageResource(TipoLugar.values()[tipo].getRecurso());
        mFoto.setScaleType(ImageView.ScaleType.FIT_END);

        mValoracion = (RatingBar) view.findViewById(R.id.lista_valoracion);
        mValoracion.setRating(cursor.getFloat(cursor.getColumnIndex("valoracion")));

        mDistancia = (TextView) view.findViewById(R.id.lista_distancia);
        GeoPunto posicion = new GeoPunto(
                cursor.getDouble(cursor.getColumnIndex("longitud")),
                cursor.getDouble(cursor.getColumnIndex("latitud")));
        if ((Lugares.posicionActual != null) && (posicion != null) && (posicion.getLatitud() != 0)) {
            int d = (int) Lugares.posicionActual.distancia(posicion);
            if (d < 2000) {
                mDistancia.setText(d + " m");
            } else {
                mDistancia.setText(d / 1000 + "Km");
            }
        }
    }
}
