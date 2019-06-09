package com.example.howse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.howse.javabean.Usuario;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityCodigoCasa extends AppCompatActivity {

    EditText codCasa;

    private boolean tipoUs;
    private  String codigo;
    private boolean esCorrecto;
    private ChildEventListener cel;

    DatabaseReference mDatabaseRefCas;



    private final Usuario[] usr = new Usuario[1];
    ArrayList<Usuario> listaArrendadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_casa);





        codCasa=findViewById(R.id.etCodigoCasa);

        tipoUs=getIntent().getBooleanExtra("tipo", true);

        listaArrendadores= new ArrayList<>();

        mDatabaseRefCas = FirebaseDatabase.getInstance().getReference().child("Usuarios");




    }

    public void atras(View v){
        finish();
    }

    public void siguiente(View v) {

        codigo = codCasa.getText().toString();


        if (codigo.isEmpty()) {
            Toast.makeText(this, "Debes introducir un codigo", Toast.LENGTH_LONG).show();


        } else {


            Query qq = mDatabaseRefCas.orderByChild("codCasa").equalTo(codigo).limitToFirst(1);

            qq.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        usr[0] = dataSnapshot1.getValue(Usuario.class);
                    }

                    if (usr[0] != null) {

                        if(usr[0].getCodCasa().equals(codigo)){
                            Toast.makeText(ActivityCodigoCasa.this, "Codigo correcto", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(ActivityCodigoCasa.this, Login.class);
                            i.putExtra("tipo", tipoUs);
                            i.putExtra("codCasa", codigo);
                            startActivity(i);

                        }else{

                            Toast.makeText(ActivityCodigoCasa.this, "Codigo Incorrecto", Toast.LENGTH_LONG).show();

                        }


                    } else {
                        Toast.makeText(ActivityCodigoCasa.this, "Codigo Incorrecto", Toast.LENGTH_LONG).show();


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ActivityCodigoCasa.this, "Algo salio Mal ahí", Toast.LENGTH_SHORT).show();

                }
            });


        }


    }
}
