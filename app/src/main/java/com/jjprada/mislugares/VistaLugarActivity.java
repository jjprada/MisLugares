package com.jjprada.mislugares;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;


public class VistaLugarActivity extends ActionBarActivity {

    private final static String TAG = "VistaLugarActivity";

    public final static String EXTRA = "ID";

    /*
    private final static int REQUEST_EDITAR = 1;
    private final static int REQUEST_GALERIA = 2;
    private final static int REQUEST_FOTO = 3;

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
    private ImageView mFoto;
    private Uri mUriFotoCamara;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_vista_lugar);

        /* VERSION CURSO PARA OBTENER EL EXTRA
        Bundle extras = getIntent().getExtras();
        mID = extras.getLong("id", -1);*/
/*        mID = getIntent().getIntExtra(EXTRA, -1);

        // Datos Editables
        mNombreLugar = (TextView) findViewById(R.id.lista_nombre);
        mLogoTipo = (ImageView) findViewById(R.id.logo_tipo);
        mTipoLugar = (TextView) findViewById(R.id.tipo_lugar);
        mDireccion = (TextView) findViewById(R.id.lista_direccion);
        mTelefono = (TextView) findViewById(R.id.phone);
        mUrl = (TextView) findViewById(R.id.url);
        mComentario = (TextView) findViewById(R.id.comentario);
        mFoto = (ImageView) findViewById(R.id.lista_foto);
        actualizarDatos();

        // Fecha
        mFecha = (TextView) findViewById(R.id.fecha);
        mFecha.setText(DateFormat.getDateInstance().format(new Date(mLugar.getFecha())));
        // Hora
        mHora = (TextView) findViewById(R.id.hora);
        mHora.setText(DateFormat.getTimeInstance().format(new Date(mLugar.getFecha())));
        // Valoracion
        mValoracion = (RatingBar) findViewById(R.id.lista_valoracion);
        mValoracion.setRating(mLugar.getValoracion());
        mValoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float valor, boolean fromUser) {
                mLugar.setValoracion(valor);
                Lugares.actualizarLugar(mID, mLugar);             // Guardar datos en la BBDD
            }
        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vista_lugar, menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent i;

        switch (id) {
            case R.id.accion_compartir:
                i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, mLugar.getNombre() + " - "+ mLugar.getUrl());
                startActivity(i);
                return true;
            case R.id.accion_llegar:
                verMapa(null);
                return true;
            case R.id.accion_editar:
                i = new Intent(VistaLugarActivity.this, EdicionLugarActivity.class);
                i.putExtra(EdicionLugarActivity.EXTRA, mID);
                startActivityForResult(i, REQUEST_EDITAR);
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
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_EDITAR:
                    actualizarDatos();
                    findViewById(R.id.vista_activity).invalidate();
                    break;
                case REQUEST_GALERIA:
                    String uri = data.getDataString();
                    mLugar.setFoto(uri);                            // Actualizamos la foto del objeto Lugar
                    actualizarFoto(mLugar.getFoto());               // Actualizamos la foto en la Vista
                    break;
                case REQUEST_FOTO:
                    mLugar.setFoto(mUriFotoCamara.toString());      // Actualizamos la foto del objeto Lugar
                    actualizarFoto(mLugar.getFoto());               // Actualizamos la foto en la Vista
                    break;
            }
        }
    }

    private void actualizarDatos() {
        // Buscar Lugar en función de la ID indicada
        mLugar = Lugares.elemento(mID);                 // Se pone aquí para cuando editemos, actualice de nuevo los datos del Lugar en mLugar

        //Nombre
        mNombreLugar.setText(mLugar.getNombre());
        // Logo Tipo Lugar
        mLogoTipo.setImageResource(mLugar.getTipoLugar().getRecurso());
        // Tipo Lugar
        mTipoLugar.setText(mLugar.getTipoLugar().getTexto());
        // Direccion
        if ((mLugar.getDireccion() == null) || (mLugar.getDireccion().equals(""))){
            mDireccion.setVisibility(View.GONE);
            findViewById(R.id.logo_direccion).setVisibility(View.GONE);
        } else { mDireccion.setText(mLugar.getDireccion());}
        // Telefono
        if (mLugar.getTelefono() == 0){
            mTelefono.setVisibility(View.GONE);
            findViewById(R.id.logo_phone).setVisibility(View.GONE);
        } else { mTelefono.setText(Integer.toString(mLugar.getTelefono()));}
        // URL
        if ((mLugar.getUrl() == null) || (mLugar.getUrl().equals(""))){
            mUrl.setVisibility(View.GONE);
            findViewById(R.id.logo_url).setVisibility(View.GONE);
        } else { mUrl.setText(mLugar.getUrl());}
        // Comentario
        if ((mLugar.getComentario() == null) || (mLugar.getComentario().equals(""))){
            mComentario.setVisibility(View.GONE);
            findViewById(R.id.logo_comentarios).setVisibility(View.GONE);
        } else { mComentario.setText(mLugar.getComentario());}
        // Foto
        actualizarFoto(mLugar.getFoto());
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
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    public void verMapa(View view) {
        Uri uri;
        double lat = mLugar.getPosicion().getLatitud();
        double lon = mLugar.getPosicion().getLongitud();
        if (lat != 0 || lon != 0) {
            uri = Uri.parse("geo:" + lat + "," + lon);
        } else {
            uri = Uri.parse("geo:0,0?q=" + mLugar.getDireccion());
        }
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
    }

    public void llamadaTelefono(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mLugar.getTelefono())));
    }

    public void verWeb(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mLugar.getUrl())));
    }

    private void actualizarFoto(String uri) {
        if (uri != null) {
            mFoto.setImageURI(Uri.parse(uri));            // Actualizamos la foto en la Vista
        } else {
            mFoto.setImageBitmap(null);                   // No foto o Uri no valido, no mostramos imagen
        }
        Lugares.actualizarLugar(mID, mLugar);             // Guardar datos en la BBDD
    }

    public void abrirGaleria (View view){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(i, REQUEST_GALERIA);
    }

    public void tomarFoto (View view){
        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
        File directorio = new File(Environment.getExternalStorageDirectory() + File.separator + "img_" + (System.currentTimeMillis()/1000) + ".jpg");
        mUriFotoCamara = Uri.fromFile(directorio);
        i.putExtra(MediaStore.EXTRA_OUTPUT, mUriFotoCamara);
        startActivityForResult(i, REQUEST_FOTO);
    }

    public void eliminarFoto (View view) {
        mLugar.setFoto(null);
        actualizarFoto(mLugar.getFoto());
    }
    */
}

