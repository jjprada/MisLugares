package com.jjprada.mislugares;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements LocationListener{

    private final static String TAG = "MainActivity";

    private static final long DOS_MINUTOS = 2 * 60 * 1000;

    private MediaPlayer mPlayer;
    private LocationManager mLocationManager;
    private Location mBestLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* lanzarActividad(R.id.button_acerca_de, AcercaDeActivity.class);
        lanzarActividad(R.id.button_exit, null);
        lanzarActividad(R.id.button_settings, Preferences.class);
        lanzarActividad(R.id.button_show, VistaLugarActivity.class);*/

        /*Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();*/

        /*mPlayer = MediaPlayer.create(this, R.raw.leanon);
        if (savedInstanceState != null){
            int pos = savedInstanceState.getInt("posicion");
            mPlayer.seekTo(pos);
        } else {
            mPlayer.start();
        }*/

        mPlayer = MediaPlayer.create(this, R.raw.leanon);
        mPlayer.start();

        AdaptadorLugares adaptadorLugares = new AdaptadorLugares(this);
        ListView listView = (ListView)findViewById(R.id.main_listView);
        listView.setAdapter(adaptadorLugares);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, VistaLugarActivity.class);
                i.putExtra(VistaLugarActivity.EXTRA, position);
                startActivity(i);
            }
        });

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Antes de nada comprobamos que alguno de los sistemas de localización este activado. Si no mostramos un mensaje para que el usuaro active alguno
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Obtenemos la última localización conocida
            if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                actualizaBestLocation(mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
            }
            if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                actualizaBestLocation(mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
            }
        } else {
            mostrarAlerta();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i;

        switch (id){
            case R.id.action_settings:
                i = new Intent(MainActivity.this, Preferences.class);
                startActivity(i);
                return true;
            case R.id.action_about:
                i = new Intent(MainActivity.this, AcercaDeActivity.class);
                startActivity(i);
                return true;
            case R.id.action_search:
                /*i = new Intent(MainActivity.this, VistaLugarActivity.class);
                i.putExtra(VistaLugarActivity.EXTRA, 0);
                startActivity(i);*/
                mostrarLugar();
                return true;
            case R.id.action_mapa:
                i = new Intent(MainActivity.this, MapaActivity.class);
                startActivity(i);
                return true;
            case R.id.action_test:
                i = new Intent(MainActivity.this, TESTActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void lanzarActividad(final int viewID, final Class actividad){
        Button button = (Button)findViewById(viewID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, actividad);

                switch (viewID){
                    case (R.id.button_show):
                        // i.putExtra(VistaLugarActivity.EXTRA, 0);
                        // startActivity(i);
                        mostrarLugar();
                        break;
                    case (R.id.button_acerca_de):case (R.id.button_settings):
                        startActivity(i);
                        break;
                    case (R.id.button_exit):
                        finish();
                        break;
                    default:
                        startActivity(i);
                        break;
                }

            }
        });
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
                        Intent i2 = new Intent(MainActivity.this, VistaLugarActivity.class);
                        i2.putExtra(VistaLugarActivity.EXTRA, id);
                        startActivity(i2);

                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
/* CODIGO PARA MOSTRAR LAS PREFERENCIAS
    public void mostrarPreferencias(View view){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String notificaciones = "Notificaciones: "+ pref.getBoolean("notificaciones",true);
        String distancia = "Distancia mínima: " + pref.getString("distancia","?");
        Toast.makeText(this, notificaciones+" / "+distancia, Toast.LENGTH_SHORT).show();
    }
*/
/* CODIFGO PARA UNO DE LOS EJEMPLOS
    public void sePulsa(View view){
        Toast.makeText(this, "Pulsado", Toast.LENGTH_SHORT).show();
    }
*/

    // Métodos del ciclo de vida de la actividad
    @Override protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
        mPlayer.start();
    }

    @Override protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();

        activarUpdates();                               // Activa la actualizacion de localizaciones
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();

        mLocationManager.removeUpdates(this);           // Detiene la actualizacion de localizaciones
    }

    @Override protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
        mPlayer.pause();
    }

    @Override protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }
    // Métodos del ciclo de vida de la actividad

    // Métodos de guardado del estado de la actividad
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mPlayer != null){
            int pos = mPlayer.getCurrentPosition();
            outState.putInt("posicion", pos);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (mPlayer != null){
            int pos = savedInstanceState.getInt("posicion");
            mPlayer.seekTo(pos);
        }
    }
    // Métodos de guardado del estado de la actividad

    // Métodos necesarios para implementar la interfaz "LocationListener"
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Nueva localización: " + location);
        actualizaBestLocation(location);
    }

    @Override
    public void onProviderDisabled(String proveedor) {
        Log.d(TAG, "Se deshabilita: "+ proveedor);
        activarUpdates();
    }

    @Override
    public void onProviderEnabled(String proveedor) {
        Log.d(TAG, "Se habilita: "+ proveedor);
        activarUpdates();
    }

    @Override
    public void onStatusChanged(String proveedor, int estado, Bundle extras) {
        Log.d(TAG, "Cambia estado: "+ proveedor);
        activarUpdates();
    }
    // Métodos necesarios para implementar la interfaz "LocationListener"


    // Activa la actualizacion de posiciones para el proveedor o proveedors que esten habilitados
    private void activarUpdates() {
        if(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20 * 1000, 5, this);         // Cada 20 segundos y con una distancia mínima de 5 m
        }

        if(mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10 * 1000, 10, this);     // Cada 10 segundos y con una distancia mínima de 10 m
        }
    }

    // Cada vez que hay una nueva localizacion, comprueba si es mejor que la localizacion que ya tenemos
    private void actualizaBestLocation(Location location) {
        if (location == null) { return; }
        if ((mBestLocation == null) || (location.getAccuracy() < 2*mBestLocation.getAccuracy()) || (location.getTime() - mBestLocation.getTime() > DOS_MINUTOS)) {
            Log.d(TAG, "Nueva mejor localización");
            mBestLocation = location;
            Lugares.posicionActual.setLatitud(location.getLatitude());
            Lugares.posicionActual.setLongitud(location.getLongitude());
        }
    }

    private void mostrarAlerta () {
        new AlertDialog.Builder(this)
                .setTitle("Configuración de localización")
                .setMessage("Ningún proveedor de localización activo ¿Quiere activar alguno?")
                .setPositiveButton("Configuración", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(i);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}


