package com.example.howse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.FirebaseApp;

public class ActivityCodigoCasa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_casa);
        getSupportActionBar().hide();
    }

    public void atras(View v){
        finish();
    }

    public void siguiente(View v){

        Intent i = new Intent( ActivityCodigoCasa.this, Login.class );
        startActivity(i);
    }
}
