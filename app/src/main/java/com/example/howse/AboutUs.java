package com.example.howse;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AboutUs extends AppCompatActivity {
    FloatingActionButton Twitter;
    FloatingActionButton Instagram;
    FloatingActionButton Email;
    ImageButton salir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        salir=findViewById(R.id.salir);
        Twitter =  findViewById( R.id.btnTwitter );
        Instagram = findViewById( R.id.btnInstagram );
        Email=findViewById(R.id.email);



        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactUs();
            }
        });

        Twitter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTwitterPage();
            }
        } );

        Instagram.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInstagramPage();
            }
        } );
    }

    private void goToTwitterPage() {

        Intent i = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://twitter.com/?lang=en" ) );
        startActivity( i );

    }
    private void goToInstagramPage() {

        Intent i = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://www.instagram.com/howseproject/" ) );
        startActivity( i );

    }

    private void contactUs(){

        Intent i= new Intent(AboutUs.this, ContactUs.class);
        startActivity(i);

    }
}
