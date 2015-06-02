package com.jjprada.mislugares;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dr4ckO on 20/05/2015.
 */
public class BaseDatos extends SQLiteOpenHelper {

    public BaseDatos(Context context){
        super(context, "lugares", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE lugares (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "direccion TEXT, " +
                "longitud REAL,  " +
                "latitud REAL,  " +
                "tipo INTEGER, " +
                "foto TEXT, " +
                "telefono INTEGER, " +
                "url TEXT, " +
                "comentario TEXT, " +
                "fecha LONG, " +
                "valoracion REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


}
