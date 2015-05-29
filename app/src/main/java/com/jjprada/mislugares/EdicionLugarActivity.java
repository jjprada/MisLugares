package com.jjprada.mislugares;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


public class EdicionLugarActivity extends ActionBarActivity {

    public static final String EXTRA = "EXTRA";
    public final static String EXTRA_NEW = "EXTRA NEW";
    private final static String TAG = "EdicionLugarActivity";

    private int mID;
    private Lugar mLugar;
    private EditText mNombre;
    private EditText mDireccion;
    private EditText mTelefono;
    private EditText mUrl;
    private EditText mComentario;
    private Spinner mTipoLugar;
    private boolean mIsNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_lugar);

        Log.d(TAG, "Entre en Actividad");
        // Obtener EXXTRAS
        mID = getIntent().getIntExtra(EXTRA, -1);
        mIsNew = getIntent().getBooleanExtra(EXTRA_NEW, false);
        Log.d(TAG, "Extras: "+mID+" - "+ mIsNew);

        mLugar = Lugares.elemento(mID);

        //Nombre
        mNombre = (EditText) findViewById(R.id.edit_name);
        if ((mLugar.getNombre()!= null) && (!mLugar.getNombre().equals(""))){
            mNombre.setText(mLugar.getNombre());
        }

        // Direccion
        mDireccion = (EditText) findViewById(R.id.edit_direccion);
        if ((mLugar.getDireccion()!= null) && (!mLugar.getDireccion().equals(""))){
            mDireccion.setText(mLugar.getDireccion());
        }

        // Telefono
        mTelefono = (EditText) findViewById(R.id.edit_phone);
        if (mLugar.getTelefono() != 0){
            mTelefono.setText(Integer.toString(mLugar.getTelefono()));
        }

        // URL
        mUrl = (EditText) findViewById(R.id.edit_web);
        if ((mLugar.getUrl()!= null) && (!mLugar.getUrl().equals(""))){
            mUrl.setText(mLugar.getUrl());
        }

        // Comentario
        mComentario = (EditText) findViewById(R.id.edit_coment);
        if ((mLugar.getComentario()!= null) && (!mLugar.getComentario().equals(""))){
            mComentario.setText(mLugar.getComentario());
        }

        // Tipo Lugar
        mTipoLugar = (Spinner) findViewById(R.id.edit_tipo_lugar);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, TipoLugar.getNombres());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTipoLugar.setAdapter(adapter);
        mTipoLugar.setSelection(mLugar.getTipoLugar().ordinal());
        Log.d(TAG, "Fin");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edicion_lugar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.edit_save) {
            Log.d(TAG, "Save: INICIO");
            // Chequeamos que no se intente acceder a valores que sean "null" o esten vacios, porque rompe la app
            if ((mNombre.getText() != null) && (!mNombre.getText().toString().equals(""))) {
                mLugar.setNombre(mNombre.getText().toString());
                Log.d(TAG, "Save: Grabar nombre");
            } else {  mLugar.setNombre("None"); }
            if ((mDireccion.getText() != null) && (!mDireccion.getText().toString().equals(""))) {
                mLugar.setDireccion(mDireccion.getText().toString());
                Log.d(TAG, "Save: Grabar Direccion");
            }
            if ((mTelefono.getText() != null) && (!mTelefono.getText().toString().equals(""))) {
                mLugar.setTelefono(Integer.parseInt(mTelefono.getText().toString()));
                Log.d(TAG, "Save: Grabar Telefono");
            }
            if ((mUrl.getText() != null) && (!mUrl.getText().toString().equals(""))) {
                mLugar.setUrl(mUrl.getText().toString());
                Log.d(TAG, "Save: Grabar URL");
            }
            if ((mComentario.getText() != null) && (!mComentario.getText().toString().equals(""))) {
                mLugar.setComentario(mComentario.getText().toString());
                Log.d(TAG, "Save: Grabar Comentario");
            }
            // No es necesario chequeo, porque por defecto ya tiene un valor; "Otros"
            mLugar.setTipoLugar(TipoLugar.values()[mTipoLugar.getSelectedItemPosition()]);
            Lugares.actualizarLugar(mID, mLugar);           // Guardar datos en la BBDD
            setResult(RESULT_OK);
            finish();
            return true;
        } else if (id == R.id.edit_cancel){
            if (mIsNew){    // Si estamos en Edición a traves de crear un Lugar nuevo // El ID que tenemos es el de la BBDD, pero como en "borrar" usamos un "id+1" aqui para igualar usamos un "id-1"
                Lugares.borrar(mID);
            }
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
