package com.example.howse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.howse.adapter.CardCompraAdapter;
import com.example.howse.javabean.Articulo;
import com.example.howse.javabean.HistorialCompra;
import com.example.howse.javabean.Usuario;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ListaCompraActivity extends MenuAbstractActivity {

    private ArrayList<Articulo> listaCompra;

    private RecyclerView recyclerView;
    private CardCompraAdapter adapter;
    private LinearLayoutManager llManager;

    FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private final Usuario[] usr = new Usuario[1];

    String emailPersona;
    String codCasa;

    String factura="Lista: ";
    ArrayList<String> listaArt;
    ImageButton btnEl;
    FloatingActionsMenu fam;




    @Override
    public int cargarLayout() {
        return R.layout.activity_lista_compra;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setActActual(COMPRA);

        fam=findViewById(R.id.grupoFav);

        final FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab1);
        final FloatingActionButton fab2=(FloatingActionButton)findViewById(R.id.fab2);
        final FloatingActionButton fab4=(FloatingActionButton)findViewById(R.id.fab4);

        btnEl=findViewById(R.id.btnEliminarArt);

        llManager= new LinearLayoutManager(this);

        recyclerView= findViewById(R.id.rvCompra);
        recyclerView.setHasFixedSize(true);

        listaCompra= new ArrayList<>();
        listaArt= new ArrayList<>();

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        emailPersona=firebaseUser.getEmail();
        reference= FirebaseDatabase.getInstance().getReference("Usuarios");

        cargarCodCasa();



        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder= new AlertDialog.Builder(ListaCompraActivity.this);



                builder.setMessage("¿Quieres hacer una nueva lista?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                nuevaLista();

                                hacerFactura();
                            }
                        }).setNegativeButton("Cancelar", null);


                AlertDialog alert=builder.create();
                alert.show();

                v.setFocusable(false);

            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(getApplicationContext(),AgnadirArticuloActivity.class);
                startActivity(i);

            }
        });

        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(getApplicationContext(),HistorialComprasActivity.class);
                startActivity(i);

            }
        });



    }

    public void cargarCodCasa(){

        Query qq = reference.orderByChild("emailUsuario").equalTo(emailPersona);

        qq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    usr[0] = dataSnapshot1.getValue(Usuario.class);
                    cargarArticulos();
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

    private void cargarArticulos() {

        reference= FirebaseDatabase.getInstance().getReference("Articulos");

        recyclerView.setLayoutManager(llManager);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listaCompra.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Articulo art;

                    try {
                        art= snapshot.getValue(Articulo.class);

                        if(art.getUsuario().getCodCasa().equals(codCasa)){
                            listaCompra.add(art);
                        }
                    }catch (DatabaseException de){

                        break;
                    }
                    assert art != null;
                    assert firebaseUser != null;



                }

                for(Articulo art: listaCompra){
                    System.out.println(art.getNombre());
                }
                adapter= new CardCompraAdapter(listaCompra);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void nuevaLista(){


        reference= FirebaseDatabase.getInstance().getReference("Articulos");

        recyclerView.setLayoutManager(llManager);

        System.out.println(codCasa);



        Query qq=  reference.orderByChild("codCasa").equalTo(codCasa);
        qq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    Articulo articulo= snapshot.getValue(Articulo.class);

                    listaArt.add(articulo.getNombre());
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        System.out.println("Flipalo con "+factura);


    }


    public void hacerFactura(){

        DatabaseReference newRefe= FirebaseDatabase.getInstance().getReference("HistorialCompra");
        factura="Lista: ";

        if(listaArt.size()==0){
            Toast.makeText(ListaCompraActivity.this,"Introduce un articulo paracrear una nueva lista",Toast.LENGTH_SHORT).show();

        }else{

            for (String artic: listaArt){
                factura+=artic+", ";
            }

            final String clave= newRefe.push().getKey();

            Calendar calendar= Calendar.getInstance();
            String currentDate= DateFormat.getDateInstance().format(calendar.getTime());

            HistorialCompra historialCompra= new HistorialCompra(factura, codCasa, currentDate);
            newRefe.child(clave).setValue(historialCompra);


            listaArt.clear();
        }



    }








}
