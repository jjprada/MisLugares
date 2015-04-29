package com.jjprada.mislugares;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;


public class VistaLugarActivity extends ActionBarActivity {

    public static final String EXTRA = "EXTRA";
    public static final int REQUEST = 10;


    private int mID;        // Estar atento porque el curso usa un long, igual tengo que cambirlo despues. Si es asi cambiar el Extra trambien
    private Lugar mLugar;
    private TextView mNombreLugar;
    private ImageView mLogoTipo;
    private TextView mTipoLugar;
    private TextView mDireccion;
    private TextView mTelefono;
    private TextView mUrl;
    private TextView mComentario;
    private TextView mFecha;
    private TextView mHora;
    private RatingBar mValoracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_lugar);

        /* VERSION CURSO PARA OBTENER EL EXTRA
        Bundle extras = getIntent().getExtras();
        mID = extras.getLong("id", -1);*/
        mID = getIntent().getIntExtra(EXTRA, -1);

        mLugar = Lugares.elemento(mID);
        // Datos Editables
        mNombreLugar = (TextView) findViewById(R.id.nombre);
        mLogoTipo = (ImageView) findViewById(R.id.logo_tipo);
        mTipoLugar = (TextView) findViewById(R.id.tipo_lugar);
        mDireccion = (TextView) findViewById(R.id.direccion);
        mTelefono = (TextView) findViewById(R.id.phone);
        mUrl = (TextView) findViewById(R.id.url);
        mComentario = (TextView) findViewById(R.id.comentario);
        actualizarDatos();

        // Fecha
        mFecha = (TextView) findViewById(R.id.fecha);
        mFecha.setText(DateFormat.getDateInstance().format(new Date(mLugar.getFecha())));
        // Hora
        mHora = (TextView) findViewById(R.id.hora);
        mHora.setText(DateFormat.getTimeInstance().format(new Date(mLugar.getFecha())));
        // Valoracion
        mValoracion = (RatingBar) findViewById(R.id.valoracion);
        mValoracion.setRating(mLugar.getValoracion());
        mValoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float valor, boolean fromUser) {
                mLugar.setValoracion(valor);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vista_lugar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.accion_compartir:
                return true;
            case R.id.accion_llegar:
                return true;
            case R.id.accion_editar:
                Intent i = new Intent(VistaLugarActivity.this, EdicionLugarActivity.class);
                i.putExtra(EdicionLugarActivity.EXTRA, mID);
                startActivityForResult(i, REQUEST);
                return true;
            case R.id.accion_buscar:
                mostrarLugar();
                return true;
            case R.id.accion_borrar:
                confirmarBorrar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST) &&  (resultCode == RESULT_OK)){
            actualizarDatos();
            findViewById(R.id.vista_activity).invalidate();
        }
    }

    private void actualizarDatos() {
        //Nombre
        mNombreLugar.setText(mLugar.getNombre());
        // Logo Tipo Lugar
        mLogoTipo.setImageResource(mLugar.getTipoLugar().getRecurso());
        // Tipo Lugar
        mTipoLugar.setText(mLugar.getTipoLugar().getTexto());
        // Direccion
        if (mLugar.getDireccion().equals("")){
            mDireccion.setVisibility(View.GONE);
            findViewById(R.id.logo_direccion).setVisibility(View.GONE);
        } else { mDireccion.setText(mLugar.getDireccion());}
        // Telefono
        if (mLugar.getTelefono() == 0){
            mTelefono.setVisibility(View.GONE);
            findViewById(R.id.logo_phone).setVisibility(View.GONE);
        } else { mTelefono.setText(Integer.toString(mLugar.getTelefono()));}
        // URL
        if (mLugar.getUrl().equals("")){
            mUrl.setVisibility(View.GONE);
            findViewById(R.id.logo_url).setVisibility(View.GONE);
        } else { mUrl.setText(mLugar.getUrl());}
        // Comentario
        if (mLugar.getComentario().equals("")){
            mComentario.setVisibility(View.GONE);
            findViewById(R.id.logo_comentarios).setVisibility(View.GONE);
        } else { mComentario.setText(mLugar.getComentario());}
    }

    public void mostrarLugar(){
        final EditText mensaje = new EditText(this);
        mensaje.setText("1");   // Lugar para mostrar por defecto, ya que ahora mismo solo tenemos defino uno

        new AlertDialog.Builder(this)
                .setTitle("Seleccion de lugar")
                .setMessage("Indica su ID:")
                .setView(mensaje)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = Integer.parseInt(mensaje.getText().toString()); // Transforma el String en un Long
                        Intent i = new Intent(VistaLugarActivity.this, VistaLugarActivity.class);
                        i.putExtra(VistaLugarActivity.EXTRA, id);
                        startActivity(i);

                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    public void confirmarBorrar(){
        TextView mensaje = new TextView(this);

        new AlertDialog.Builder(this)
                .setTitle("Borrado de lugar")
                .setMessage("¿Estas seguro que quieres eliminar este lugar?")
                .setView(mensaje)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Lugares.borrar(mID);
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}

