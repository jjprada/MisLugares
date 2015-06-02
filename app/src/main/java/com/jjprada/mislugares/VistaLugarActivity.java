package com.jjprada.mislugares;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class VistaLugarActivity extends ActionBarActivity {

    private final static String TAG = "VistaLugarActivity";
    public final static String EXTRA = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_vista_lugar);
    }
}

