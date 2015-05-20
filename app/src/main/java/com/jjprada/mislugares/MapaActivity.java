package com.jjprada.mislugares;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapaActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMapa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        mMapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa)).getMap();
        mMapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMapa.setMyLocationEnabled(true);
        mMapa.getUiSettings().setZoomControlsEnabled(true);
        mMapa.getUiSettings().setCompassEnabled(true);
        mMapa.setOnInfoWindowClickListener(this);

        // El centro del mapa es situado en el primer lugar de la "Lista de Lugares" siempre que tenga algún elemento
        if (Lugares.listaLugares.size() > 0) {
            GeoPunto punto = Lugares.listaLugares.get(0).getPosicion();
            mMapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(punto.getLatitud(), punto.getLongitud()), 12));
        }

        // Añadimos un marcador para cada lugar de la "Lista de Lugares"
        for (Lugar lugar : Lugares.listaLugares) {
            GeoPunto punto = lugar.getPosicion();
            if ((punto != null) && (punto.getLatitud() != 0)) {
                BitmapDrawable iconoDrawable = (BitmapDrawable) getResources().getDrawable(lugar.getTipoLugar().getRecurso());
                Bitmap iGrande = iconoDrawable.getBitmap();
                Bitmap icono = Bitmap.createScaledBitmap(iGrande, iGrande.getWidth() / 7, iGrande.getHeight() / 7, false);
                mMapa.addMarker(new MarkerOptions()
                        .position(new LatLng(punto.getLatitud(), punto.getLongitud()))
                        .title(lugar.getNombre())
                        .snippet(lugar.getDireccion())
                        .icon(BitmapDescriptorFactory.fromBitmap(icono)));
            }
        }
    }

    // Al pulsar sobre una ventana de información se abre la actividad que muestra información detallada de ese lugar
    @Override
    public void onInfoWindowClick(Marker marker) {
        /* Es necesario averiguar a que lugar corresponde el marcador que se ha pulsado. */
        /* Para ello buscamos un lugar cuyo nombre coincida con el título del marcador. */
        /* Cuando se encuentre una coincidencia, se lanza la actividad indicando la "id" del lugar a mostrar */
        for (int id = 0; id < Lugares.listaLugares.size(); id++){
            Lugar lugar = Lugares.elemento(id);

            if (lugar.getNombre().equals(marker.getTitle())) {
                Intent i = new Intent(MapaActivity.this, VistaLugarActivity.class);
                i.putExtra(VistaLugarActivity.EXTRA, id);
                startActivity(i);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
