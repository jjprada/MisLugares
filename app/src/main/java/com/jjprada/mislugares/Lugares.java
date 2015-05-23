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

    public static void iniciarBD(Context contexto){
        sBaseDatos = new BaseDatos(contexto);
    }

    public static Cursor listado() {
        SQLiteDatabase bd = sBaseDatos.getReadableDatabase();
        return bd.rawQuery("SELECT * FROM lugares", null);
    }

    public static Lugar elemento(int id) {
        //Log.d(TAG, "ID es: " +id);
        Lugar lugar = null;
        SQLiteDatabase bd = sBaseDatos.getReadableDatabase();
        // Hay que usar (id+1), porque la primer posicion de un "cursor" tiene indice "1" mientras que el primer elemento de un "ListView" tiene indice "0"
        Cursor cursor = bd.rawQuery("SELECT * FROM lugares WHERE _id = " + (id+1), null);

        // Log.d(TAG, "Dato #1 es: " +cursor.getPosition());
        // Log.d(TAG, "Dato #1 es: " +cursor.getCount());
        // Si existe un registro "moveToFirst" devuelve "true", a parte de realizar su función de posicionar el cursor en el primer registro válido
        if (cursor.moveToFirst()){
            // Log.d(TAG, "Dato #2 es: " +cursor.getPosition());
            // Log.d(TAG, "Dato #2 es: " +cursor.getCount());
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
/*
    public static void modificarBBDD (){
        SQLiteDatabase db = sBaseDatos.getWritableDatabase();

        db.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Escuela Polit&eacute;cnica Superior de Gand&iacute;a', " +
                "'C/ Paranimf, 1 46730 Gand&iacute;a (SPAIN)', -0.166093, 38.995656, " +
                TipoLugar.EDUCACION.ordinal() + ", '', 962849300, 'http://www.epsg.upv.es', " +
                "'Uno de los mejores lugares para formarse.', " +
                System.currentTimeMillis() + ", 3.0)");
    }*/






    protected static GeoPunto posicionActual = new GeoPunto(0,0);

    protected static List<Lugar> listaLugares = ejemploLugares();

    public Lugares() {
        listaLugares = ejemploLugares();
    }

    /* Version anterior a introducir la BBDD
    static Lugar elemento(int id){
        return listaLugares.get(id);
    }
    */

    static void anyade(Lugar lugar){
        listaLugares.add(lugar);
    }

    static int nuevo(){
        Lugar lugar = new Lugar();
        listaLugares.add(lugar);
        return listaLugares.size()-1;
    }

    static void borrar(int id){
        listaLugares.remove(id);
    }

    public static int size() {
        return listaLugares.size();
    }

    static List listaNombres(){
        ArrayList resultado = new ArrayList();
        for (Lugar lugar: listaLugares){
            resultado.add(lugar.getNombre());
        }
        return resultado;
    }

    public static ArrayList<Lugar> ejemploLugares() {
        ArrayList<Lugar> lugares = new ArrayList<Lugar>();
        lugares.add(new Lugar("Escuela Politécnica Superior de Gandía",
                "C/ Paranimf, 1 46730 Gandia (SPAIN)", -0.166093, 38.995656,
                TipoLugar.EDUCACION,962849300, "http://www.epsg.upv.es",
                "Uno de los mejores lugares para formarse.", 3));

        lugares.add(new Lugar("Al de siempre",
                "P.Industrial Junto Molí Nou - 46722, Benifla (Valencia)",
                -0.190642, 38.925857, TipoLugar.BAR, 636472405, "",
                "No te pierdas el arroz en calabaza.", 3));

        lugares.add(new Lugar("androidcurso.com",
                "ciberespacio", 0.0, 0.0, TipoLugar.EDUCACION,
                962849300, "http://androidcurso.com",
                "Amplia tus conocimientos sobre Android.", 5));

        lugares.add(new Lugar("Barranco del Infierno",
                "Vía Verde del río Serpis. Villalonga (Valencia)",
                -0.295058, 38.867180, TipoLugar.NATURALEZA,

                0, "http://sosegaos.blogspot.com.es/2009/02/lorcha-villalonga-via-verde-del-rio.html",
                "Espectacular ruta para bici o andar", 4));

        lugares.add(new Lugar("La Vital",
                "Avda. de La Vital, 0 46701 Gandía (Valencia)",
                -0.1720092, 38.9705949, TipoLugar.COMPRAS,
                962881070, "http://www.lavital.es/",
                "El típico centro comercial", 2));

        return lugares;

    }
}
