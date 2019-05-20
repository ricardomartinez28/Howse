package com.example.howse;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.howse.javabean.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ActivityCaseroInquilino extends AppCompatActivity {


    private boolean opcion;
    FirebaseUser firebaseUser;
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference( "Usuarios" );
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();;
    private FirebaseUser usuario = firebaseAuth.getCurrentUser();

    private Boolean tipoUs;
    private String emailPersona= usuario.getEmail();;

    private final Usuario[] usr = new Usuario[1];

   @Override
    protected void onStart() {

       Query qq = mDatabaseRef.orderByChild("emailUsuario").equalTo(emailPersona);

       qq.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




               for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                   usr[0] = dataSnapshot1.getValue( Usuario.class);
               }

               if (emailPersona.equals( usr[0].getEmailUsuario() )){

                   tipoUs = usr[0].getTipoUs();

               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {


           }
       });
        super.onStart();

        if(firebaseUser!=null){

            if (tipoUs) {
                Intent i = new Intent( ActivityCaseroInquilino.this, PreviewDelChat.class );
                startActivity( i );
                finish();
            }else{
                Intent i = new Intent( ActivityCaseroInquilino.this, PreviewChatArrendador.class );
                startActivity( i );
                finish();
            }
        }


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casero_inquilino);




    }

    public void inquilino(View v){


        opcion=true;
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
