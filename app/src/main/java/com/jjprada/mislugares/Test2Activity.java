package com.jjprada.mislugares;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Test2Activity extends ActionBarActivity {

    public static final String EXTRA = "Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        String name = getIntent().getStringExtra(EXTRA);
        TextView view = (TextView)findViewById(R.id.test2_mensaje);
        view.setText("Hola " + name + ", ¿Aceptas las condiciones?");

        volver(R.id.test2_button_aceptar);
        volver(R.id.test2_button_rechazar);
    }

    public void volver (final int viewID){
        final Button button = (Button)findViewById(viewID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent i = new Intent(Test2Activity.this, TestActivity.class);

                if (viewID == R.id.test2_button_aceptar){
                    setResult(RESULT_OK);
                } else if (viewID == R.id.test2_button_rechazar){
                    setResult(RESULT_CANCELED);
                }
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test2, menu);
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
