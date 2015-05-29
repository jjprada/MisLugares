package com.jjprada.mislugares;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;


public class VistaLugarFragment extends Fragment {

    private final static String TAG = "VistaLugarFragment";


    private final static int REQUEST_EDITAR = 1;
    private final static int REQUEST_GALERIA = 2;
    private final static int REQUEST_FOTO = 3;

    private int mID;        // Estar atento porque el curso usa un long, igual tengo que cambirlo despues. Si es asi cambiar el Extra trambien
    private View mView;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_vista_lugar, container, false);
        setHasOptionsMenu(true);

        // LISTNER PARA MOSTRAR EL MAPA
        LinearLayout mapa = (LinearLayout) view.findViewById(R.id.elemento_mapa);
        mapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                verMapa();
            }
        });
        // LISTNER PARA LLAMAR AL TELEFONO
        LinearLayout phone = (LinearLayout) view.findViewById(R.id.elemento_phone);
        phone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                llamadaTelefono();
            }
        });
        // LISTNER PARA MOSTRAR LA WEB
        LinearLayout url = (LinearLayout) view.findViewById(R.id.elemento_url);
        url.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                verWeb();
            }
        });
        // LISTNER PARA TOMAR UN FOTO
        ImageView ivTomaFoto = (ImageView) view.findViewById(R.id.tomar_Foto);
        ivTomaFoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                tomarFoto();
            }
        });
        // LISTNER PARA ABRIR LA GALERIA
        ImageView ivAbrirGaleria = (ImageView) view.findViewById(R.id.abrir_galeria);
        ivAbrirGaleria.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                abrirGaleria();
            }
        });
        // LISTNER PARA ELIMINAR LA FOTO
        ImageView ivEliminarFoto = (ImageView) view.findViewById(R.id.eliminar_foto);
        ivEliminarFoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                eliminarFoto();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Obtenemos los extras pasados a la actividad
        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null) {            // Si los extras no están vacios
            // Obtenemos la ID del Lugar mostrado
            mID = extras.getInt(VistaLugarActivity.EXTRA, -1);
            if(mID != -1) {             // Si no hay error, actualizamos los datos del Lugar
                mostrarLugarFragment(mID);
            }
        }
    }

    public void mostrarLugarFragment(int id) {
        // Actualizamos el valor de ID, ya que se puede llamar a este método desde diferentes sitios que modifiquen este valor
        mID = id;

        // Obtenemos la Vista del "layout" que muestra el "fragment"
        mView = getView();

        // Datos Editables
        mNombreLugar = (TextView) mView.findViewById(R.id.lista_nombre);
        mLogoTipo = (ImageView) mView.findViewById(R.id.logo_tipo);
        mTipoLugar = (TextView) mView.findViewById(R.id.tipo_lugar);
        mDireccion = (TextView) mView.findViewById(R.id.lista_direccion);
        mTelefono = (TextView) mView.findViewById(R.id.phone);
        mUrl = (TextView) mView.findViewById(R.id.url);
        mComentario = (TextView) mView.findViewById(R.id.comentario);
        mFoto = (ImageView) mView.findViewById(R.id.lista_foto);
        actualizarDatos();

        // Fecha
        mFecha = (TextView) mView.findViewById(R.id.fecha);
        mFecha.setText(DateFormat.getDateInstance().format(new Date(mLugar.getFecha())));
        // Hora
        mHora = (TextView) mView.findViewById(R.id.hora);
        mHora.setText(DateFormat.getTimeInstance().format(new Date(mLugar.getFecha())));
        // Valoracion
        mValoracion = (RatingBar) mView.findViewById(R.id.lista_valoracion);
        mValoracion.setRating(mLugar.getValoracion());
        mValoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float valor, boolean fromUser) {
                mLugar.setValoracion(valor);
                Lugares.actualizarLugar(mID, mLugar);             // Guardar datos en la BBDD
            }
        });
    }

    // MUESTRA LOS DATOS DEL LUGAR QUE PUEDEN SER EDITABLES, POR ESO SE METEN EN UN MÉTODO APARTE
    private void actualizarDatos() {
        // Buscar el Lugar en función de la ID indicada
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
            mView.findViewById(R.id.logo_direccion).setVisibility(View.GONE);
        } else { mDireccion.setText(mLugar.getDireccion());}
        // Telefono
        if (mLugar.getTelefono() == 0){
            mTelefono.setVisibility(View.GONE);
            mView.findViewById(R.id.logo_phone).setVisibility(View.GONE);
        } else { mTelefono.setText(Integer.toString(mLugar.getTelefono()));}
        // URL
        if ((mLugar.getUrl() == null) || (mLugar.getUrl().equals(""))){
            mUrl.setVisibility(View.GONE);
            mView.findViewById(R.id.logo_url).setVisibility(View.GONE);
        } else { mUrl.setText(mLugar.getUrl());}
        // Comentario
        if ((mLugar.getComentario() == null) || (mLugar.getComentario().equals(""))){
            mComentario.setVisibility(View.GONE);
            mView.findViewById(R.id.logo_comentarios).setVisibility(View.GONE);
        } else { mComentario.setText(mLugar.getComentario());}
        // Foto
        actualizarFoto(mLugar.getFoto());
    }

    // MUESTRA LA FOTO DEL LUGAR // SE METE EN UN MÉTODO A PARTE PORQUE SE USA TAMBIEN CUANDO SE EDITA LA FOTO
    private void actualizarFoto(String uri) {
        if (uri != null) {
            mFoto.setImageURI(Uri.parse(uri));            // Actualizamos la foto en la Vista
        } else {
            mFoto.setImageBitmap(null);                   // No foto o Uri no valido, no mostramos imagen
        }
        Lugares.actualizarLugar(mID, mLugar);             // Guardar datos en la BBDD
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_vista_lugar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

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
                verMapa();
                return true;
            case R.id.accion_editar:
                i = new Intent(getActivity(), EdicionLugarActivity.class);
                i.putExtra(EdicionLugarActivity.EXTRA, mID);
                startActivityForResult(i, REQUEST_EDITAR);
                return true;
            case R.id.accion_buscar:
                //mostrarLugar();
                return true;
            case R.id.accion_borrar:
                confirmarBorrar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_EDITAR:
                    actualizarDatos();
                    mView.findViewById(R.id.vista_activity).invalidate();
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

    // Muestra un Lugar en función de la ID indicada por el usuario
    /* AHORA NO FUNCIONA BIEN PORQUE NO CHEQUEA SI EXISTE UN LUGAR CON ESA ID POR LO QUE ROMPE LA APP
    ** NO LO SOLUCIONADO PORQUE UNA VEZ METIDA LA BD NO TIENE SENTIDO BUSCAR POR ID */
    public void mostrarLugar(){
        final EditText mensaje = new EditText(getActivity());
        mensaje.setText("1");   // Lugar para mostrar por defecto, ya que ahora mismo solo tenemos defino uno

        new AlertDialog.Builder(getActivity())
                .setTitle("Seleccion de lugar")
                .setMessage("Indica su ID:")
                .setView(mensaje)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = Integer.parseInt(mensaje.getText().toString()); // Transforma el String en un Long
                        Intent i = new Intent(getActivity(), VistaLugarActivity.class);
                        i.putExtra(VistaLugarActivity.EXTRA, id);
                        startActivity(i);

                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // BORRAR EL LUGAR EN EL QUE NOS ENCONTRAMOS
    public void confirmarBorrar(){
        TextView mensaje = new TextView(getActivity());

        new AlertDialog.Builder(getActivity())
                .setTitle("Borrado de lugar")
                .setMessage("¿Estas seguro que quieres eliminar este lugar?")
                .setView(mensaje)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Lugares.borrar(mID);
                        getActivity().setResult(Activity.RESULT_OK);

                        SelectorFragment selectorFragment = (SelectorFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.selector_fragment);
                        if (selectorFragment == null) {
                            getActivity().finish();
                        } else {
                            ((MainActivity) getActivity()).muestraLugar(Lugares.primerId());
                            ((MainActivity) getActivity()).actualizarLista();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // MUESTRA EL LUGAR EN UN MAPA
    private void verMapa() {
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

    // LLAMA AL TELEFONO INDICADO EN EL LUGAR
    private void llamadaTelefono() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mLugar.getTelefono())));
    }

    // VER WEB DEL LUGAR
    private void verWeb() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mLugar.getUrl())));
    }

    // METODOS PARA EL CAMBIO DE FOTO DEL LUGAR
    private void tomarFoto (){
        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
        File directorio = new File(Environment.getExternalStorageDirectory() + File.separator + "img_" + (System.currentTimeMillis()/1000) + ".jpg");
        mUriFotoCamara = Uri.fromFile(directorio);
        i.putExtra(MediaStore.EXTRA_OUTPUT, mUriFotoCamara);
        startActivityForResult(i, REQUEST_FOTO);
    }
    private void abrirGaleria (){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(i, REQUEST_GALERIA);
    }
    private void eliminarFoto () {
        mLugar.setFoto(null);
        actualizarFoto(mLugar.getFoto());
    }
}

