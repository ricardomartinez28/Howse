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
import android.widget.ImageView;

public class AboutUs extends AppCompatActivity {
    Button Twitter;
    Button Instagram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_about_us2 );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );

        Twitter = (Button) findViewById( R.id.btnTwitter );
        Instagram = (Button) findViewById( R.id.btnInstagram );

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
}
