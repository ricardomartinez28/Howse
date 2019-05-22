package com.example.howse;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.howse.adapter.AdaptadorHistorial;
import com.example.howse.javabean.HistorialCompra;
import com.example.howse.javabean.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class HistorialComprasActivity extends AppCompatActivity {


    FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private final Usuario[] usr = new Usuario[1];

    String emailPersona;
    String codCasa;

    private RecyclerView recyclerView;
    private LinearLayoutManager llManager;
    private AdaptadorHistorial hAdapter;

    private  ArrayList<HistorialCompra> hLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_compras);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        emailPersona=firebaseUser.getEmail();
        reference= FirebaseDatabase.getInstance().getReference("Usuarios");

        hLista=new ArrayList<>();

        cargarCodCasa();

        llManager= new LinearLayoutManager(this);

        recyclerView=findViewById(R.id.rvHistCompra);
        recyclerView.setHasFixedSize(true);





    }

    public void cargarCodCasa(){

        Query qq = reference.orderByChild("emailUsuario").equalTo(emailPersona);

        qq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    usr[0] = dataSnapshot1.getValue(Usuario.class);
                    cargarHistorialCompra();
                }

                if (emailPersona.equals( usr[0].getEmailUsuario() )){

                    codCasa=usr[0].getCodCasa();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void cargarHistorialCompra() {

        reference= FirebaseDatabase.getInstance().getReference("HistorialCompra");
        recyclerView.setLayoutManager(llManager);

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                hLista.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    HistorialCompra hcomp= snapshot.getValue(HistorialCompra.class);

                    if(hcomp.getCodCasa().equals(codCasa)){
                        hLista.add(hcomp);
                    }
                }

                hAdapter= new AdaptadorHistorial(hLista);
                recyclerView.setAdapter(hAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
