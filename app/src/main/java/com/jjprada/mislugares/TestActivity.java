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
import android.widget.Toast;


public class TestActivity extends ActionBarActivity {

    private static final String TAG = "TestActivity";
    private static final int REQUEST = 10;

    private EditText mTestName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mTestName = (EditText)findViewById(R.id.test_name);
        Button button = (Button)findViewById(R.id.test_button_verificar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTestName.getText().toString().equals("")) {
                    Intent i = new Intent(TestActivity.this, Test2Activity.class);
                    i.putExtra(Test2Activity.EXTRA, mTestName.getText().toString());
                    startActivityForResult(i, REQUEST);
                } else {
                    Toast.makeText(TestActivity.this, "No se ha introducido un nombre", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST){
            TextView view = (TextView)findViewById(R.id.test_resultado);

            if (resultCode == RESULT_OK){
                view.setText("Resultado: Aceptado");
            } else if (resultCode == RESULT_CANCELED){
                view.setText("Resultado: Rechazado");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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
