package com.example.howse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityCaseroInquilino extends AppCompatActivity {
    private Button btnInquilino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casero_inquilino);
        getSupportActionBar().hide();

        btnInquilino = findViewById( R.id.btnInquilino );

        btnInquilino.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( ActivityCaseroInquilino.this, Register.class );
                startActivity(i);


            }
        } );
    }




}
