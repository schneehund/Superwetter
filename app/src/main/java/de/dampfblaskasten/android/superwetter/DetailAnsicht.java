package de.dampfblaskasten.android.superwetter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class DetailAnsicht extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_ansicht);
            Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
            setSupportActionBar(toolbar2);
            getSupportActionBar().setHomeButtonEnabled(true);
            // getActionBar().setDisplayHomeAsUpEnabled(true);
    }





}