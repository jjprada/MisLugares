package com.jjprada.mislugares;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

    private int mID;
    private Lugar mLugar;
    private EditText mNombre;
    private EditText mDireccion;
    private EditText mTelefono;
    private EditText mUrl;
    private EditText mComentario;
    private Spinner mTipoLugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_lugar);

        mID = getIntent().getIntExtra(EXTRA, -1);

        mLugar = Lugares.elemento(mID);

        //Nombre
        mNombre = (EditText) findViewById(R.id.edit_name);
        mNombre.setText(mLugar.getNombre());

        // Direccion
        mDireccion = (EditText) findViewById(R.id.edit_direccion);
        if (!mLugar.getDireccion().equals("")){
            mDireccion.setText(mLugar.getDireccion());
        }

        // Telefono
        mTelefono = (EditText) findViewById(R.id.edit_phone);
        if (mLugar.getTelefono() != 0){
            mTelefono.setText(Integer.toString(mLugar.getTelefono()));
        }

        // URL
        mUrl = (EditText) findViewById(R.id.edit_web);
        if (!mLugar.getUrl().equals("")){
            mUrl.setText(mLugar.getUrl());
        }

        // Comentario
        mComentario = (EditText) findViewById(R.id.edit_coment);
        if (!mLugar.getComentario().equals("")){
            mComentario.setText(mLugar.getComentario());
        }

        // Tipo Lugar
        mTipoLugar = (Spinner) findViewById(R.id.edit_tipo_lugar);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, TipoLugar.getNombres());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTipoLugar.setAdapter(adapter);
        mTipoLugar.setSelection(mLugar.getTipoLugar().ordinal());
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
            mLugar.setNombre(mNombre.getText().toString());
            mLugar.setDireccion(mDireccion.getText().toString());
            mLugar.setTelefono(Integer.parseInt(mTelefono.getText().toString()));
            mLugar.setUrl(mUrl.getText().toString());
            mLugar.setComentario(mComentario.getText().toString());
            mLugar.setTipoLugar(TipoLugar.values()[mTipoLugar.getSelectedItemPosition()]);
            setResult(RESULT_OK);
            finish();
            return true;
        } else if (id == R.id.edit_cancel){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
