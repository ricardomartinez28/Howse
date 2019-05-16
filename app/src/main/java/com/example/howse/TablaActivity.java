package com.example.howse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.example.howse.adapter.TableAdapter;
import com.example.howse.javabean.Tarea;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;



public class TablaActivity extends AppCompatActivity {
    private RecyclerView miRecyclerView;//Llamando al atributo
    private LinearLayoutManager miLayoutManager;
    private TableAdapter miAdapter;
    private ArrayList<String> dias;//Creando una Array de la lista


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tabla );

        miRecyclerView = findViewById( R.id.myRecyclerView );
        dias = new ArrayList<>();
        miLayoutManager = new LinearLayoutManager( this );

        miRecyclerView.setAdapter( miAdapter );
        miRecyclerView.setLayoutManager( miLayoutManager );
        miRecyclerView.setItemAnimator( new DefaultItemAnimator() );

        listaDias();

    }

    public void listaDias() {

        dias.add("Lunes");
        dias.add("Martes");
        dias.add("Miercoles");
        dias.add("Jueves");
        dias.add("Viernes");
        dias.add("Sabado");
        dias.add("Domingo");


}
    public void BOTON(View v){
       /* Intent i = new Intent( TablaActivity.this, CrearEventos.class );
        startActivity(i);*/
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(miAdapter == null) {
            miAdapter = new TableAdapter(dias, this);
            miRecyclerView.setAdapter(miAdapter);
        }
        else {
            dias.clear();

        }
        miAdapter.notifyDataSetChanged();

    }



}