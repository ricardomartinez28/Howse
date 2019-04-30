package com.example.howse;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class PruebaPonerFoto extends AppCompatActivity {
    ImageView img;
    Uri uri;
    String emailPersona;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser usuario;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_prueba_poner_foto );

        img =(ImageView) findViewById( R.id.Img );

        usuario = firebaseAuth.getCurrentUser();
        uri = usuario.getPhotoUrl();
        cargarImg();



    }
    public void cargarImg(){
        img.setImageURI( uri );
    }
}
