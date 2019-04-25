package com.example.howse;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

public class ActivityCaseroInquilino extends AppCompatActivity {


    private boolean opcion=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casero_inquilino);
        getSupportActionBar().hide();




    }

    public void inquilino(View v){

        Intent i= new Intent(ActivityCaseroInquilino.this, ActivityCodigoCasa.class);
        i.putExtra("tipo", opcion);
        startActivity(i);


    }

    public void arrendador(View v){

        opcion=false;
        Intent i= new Intent(ActivityCaseroInquilino.this, Login.class);
        i.putExtra("tipo", opcion);
        startActivity(i);

    }



}
