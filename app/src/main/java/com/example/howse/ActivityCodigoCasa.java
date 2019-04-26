package com.example.howse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.howse.javabean.Arrendador;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ActivityCodigoCasa extends AppCompatActivity {

    EditText codCasa;

    private boolean tipoUs;
    private  String codigo;
    private boolean esCorrecto;
    private ChildEventListener cel;

    DatabaseReference mDatabaseRefCas;

    ArrayList<Arrendador> listaArrendadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_casa);

        getSupportActionBar().hide();

        codCasa=findViewById(R.id.etCodigoCasa);

        tipoUs=getIntent().getBooleanExtra("tipo", true);

        listaArrendadores= new ArrayList<>();

        mDatabaseRefCas = FirebaseDatabase.getInstance().getReference().child("Arrendadores");

        addChildEventListener();

    }

    public void atras(View v){
        finish();
    }

    public void siguiente(View v){

        codigo = codCasa.getText().toString();

        if(codigo.isEmpty()){
            Toast.makeText(this, "Debes introducir un codigo", Toast.LENGTH_LONG).show();


        }else {



            for (Arrendador arrendador: listaArrendadores){
                if(codigo.trim().equals(arrendador.getCodCasa())){
                    esCorrecto=true;

                }

            }

            if(esCorrecto){
                Toast.makeText(this, "Codigo correcto",Toast.LENGTH_LONG).show();

                Intent i = new Intent(ActivityCodigoCasa.this, Login.class);
                i.putExtra("tipo", tipoUs);
                i.putExtra("codCasa", codigo);
                startActivity(i);


            }else{

                Toast.makeText(this, "El codigo no es correcto",Toast.LENGTH_LONG).show();


            }


        }
    }

    public void addChildEventListener(){
        if (cel==null){
            cel= new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Arrendador arren= dataSnapshot.getValue(Arrendador.class);


                    listaArrendadores.add(arren);





                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mDatabaseRefCas.addChildEventListener(cel);
        }



    }

}
