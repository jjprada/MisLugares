package com.jjprada.mislugares;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseAdapter baseAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Lugares.listaNombres());
        ListView listView = (ListView)findViewById(R.id.main_listView);
        listView.setAdapter(baseAdapter);

/*        lanzarActividad(R.id.button_acerca_de, AcercaDeActivity.class);
        lanzarActividad(R.id.button_exit, null);
        lanzarActividad(R.id.button_settings, Preferences.class);
        lanzarActividad(R.id.button_show, VistaLugarActivity.class);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent i;

        //noinspection SimplifiableIfStatement
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
}
