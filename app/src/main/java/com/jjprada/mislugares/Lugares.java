package com.jjprada.mislugares;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dr4ckO on 18/04/2015.
 */
public class Lugares {
    private final static String TAG = "Lugares";

    private static BaseDatos sBaseDatos;

    // CONSTRUCTOR
    public static void iniciarBD(Context contexto){
        sBaseDatos = new BaseDatos(contexto);
    }

    // DEVUELVE UN "CURSOR" CON TODOS LOS REGISTRO DE LA BD
    public static Cursor listado() {
        SQLiteDatabase bd = sBaseDatos.getReadableDatabase();
        return bd.rawQuery("SELECT * FROM lugares", null);
    }

    // DEVUELVE UN LUGAR DE LA BD EN FUNCION DE LA ID
    public static Lugar elemento(int id) {
        Lugar lugar = null;     // Valor devuelto en caso de error
        SQLiteDatabase bd = sBaseDatos.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM lugares WHERE _id = " + id, null);
        // Si existe un registro "moveToFirst" devuelve "true", a parte de realizar su función de posicionar el cursor en el primer registro válido
        if (cursor.moveToFirst()){
            lugar = new Lugar();
            lugar.setNombre(cursor.getString(1));
            lugar.setDireccion(cursor.getString(2));
            lugar.setPosicion(new GeoPunto(cursor.getDouble(3), cursor.getDouble(4)));
            lugar.setTipoLugar(TipoLugar.values()[cursor.getInt(5)]);
            lugar.setFoto(cursor.getString(6));
            lugar.setTelefono(cursor.getInt(7));
            lugar.setUrl(cursor.getString(8));
            lugar.setComentario(cursor.getString(9));
            lugar.setFecha(cursor.getLong(10));
            lugar.setValoracion(cursor.getFloat(11));
        }
        cursor.close();
        bd.close();
        return lugar;
    }

    // ACTUALIZA LOS DATOS DE UN LUGAR EN LA BD
    public static void actualizarLugar(int id, Lugar lugar){
        SQLiteDatabase bd = sBaseDatos.getWritableDatabase();
        if (lugar.getNombre() != null) { bd.execSQL("UPDATE lugares SET nombre = '" + lugar.getNombre() + "' WHERE _id = " + id); }
        if (lugar.getDireccion() != null) { bd.execSQL("UPDATE lugares SET direccion = '" + lugar.getDireccion() + "' WHERE _id = " + id); }
        if (lugar.getPosicion().getLongitud() != 0) { bd.execSQL("UPDATE lugares SET longitud = '" + lugar.getPosicion().getLongitud() + "' WHERE _id = " + id); }
        if (lugar.getPosicion().getLatitud() != 0) { bd.execSQL("UPDATE lugares SET latitud = '" + lugar.getPosicion().getLatitud() + "' WHERE _id = " + id); }
        if (lugar.getTipoLugar() != null) { bd.execSQL("UPDATE lugares SET tipo = '" + lugar.getTipoLugar().ordinal() + "' WHERE _id = " + id); }
        if (lugar.getFoto() != null) { bd.execSQL("UPDATE lugares SET foto = '" + lugar.getFoto() + "' WHERE _id = " + id); }
        if (lugar.getTelefono() != 0) { bd.execSQL("UPDATE lugares SET telefono = '" + lugar.getTelefono() + "' WHERE _id = " + id); }
        if (lugar.getUrl() != null) { bd.execSQL("UPDATE lugares SET url = '" + lugar.getUrl() + "' WHERE _id = " + id); }
        if (lugar.getComentario() != null) { bd.execSQL("UPDATE lugares SET comentario = '" + lugar.getComentario() + "' WHERE _id = " + id); }
        if (lugar.getFecha() != 0) { bd.execSQL("UPDATE lugares SET fecha = '" + lugar.getFecha() + "' WHERE _id = " + id); }
        if (lugar.getValoracion() != 0) { bd.execSQL("UPDATE lugares SET valoracion = '" + lugar.getValoracion() + "' WHERE _id = " + id); }
        bd.close();
    }

    // CREAR UN NUEVO LUGAR EN LA BD
    public static int nuevo() {
        int id = -1;                    // Valor devuelto en caso de error
        Lugar lugar = new Lugar();
        SQLiteDatabase bd = sBaseDatos.getWritableDatabase();
        bd.execSQL("INSERT INTO lugares (longitud, latitud, tipo, fecha) VALUES ( "+
                lugar.getPosicion().getLongitud()+", "+ lugar.getPosicion().getLatitud()+", "+
                lugar.getTipoLugar().ordinal() + ", " + lugar.getFecha() + ")");
        Cursor cursor = bd.rawQuery("SELECT _id FROM lugares WHERE fecha = " + lugar.getFecha(), null);
        if (cursor.moveToFirst()){      // Chequeamos que haya algun registro que conicida con la busqueda realizada
            id = cursor.getInt(0);      // Obtenemos la ID de la BD para el Lugar que buscabamos
        }
        cursor.close();
        bd.close();
        return id;
    }

    // BORRA UN LUGAR DE LA BD
    public static void borrar(int id) {
        SQLiteDatabase bd = sBaseDatos.getWritableDatabase();
        bd.execSQL("DELETE FROM lugares WHERE _id = " + id);
        bd.close();
    }

    // BUSCAR UN LUGAR EN FUNCION DE SU NOMBRE EN LA BD
    public static int buscarNombre(String nombre) {
        int id = -1;                // Valor devuelto en caso de error
        SQLiteDatabase bd = sBaseDatos.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM lugares WHERE nombre = '" + nombre + "'", null);
        if (cursor.moveToFirst()){      // Chequeamos que haya algun registro que conicida con la busqueda realizada
            id = cursor.getInt(0);      // Obtenemos la ID de la BD para el Lugar que buscabamos
        }
        cursor.close();
        bd.close();
        return id;
    }

    // SE USA PARA LAS OPCIONES DEL MAPA
    protected static GeoPunto posicionActual = new GeoPunto(0,0);
}
