package com.example.howse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.howse.adapter.AdaptadorDiasSemana;
import com.example.howse.adapter.UserAdapter;
import com.example.howse.javabean.DiasTareas;
import com.example.howse.javabean.Tarea;
import com.example.howse.javabean.TareasVisual;
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

import java.util.ArrayList;

public class TareasActivity extends MenuAbstractActivity {


    ArrayList<DiasTareas> tareasDias;

    ArrayList<TareasVisual> tareasLunes;
    ArrayList<TareasVisual> tareasMartes;
    ArrayList<TareasVisual> tareasMiercoles;
    ArrayList<TareasVisual> tareasJueves;
    ArrayList<TareasVisual> tareasViernes;
    ArrayList<TareasVisual> tareasSabado;
    ArrayList<TareasVisual> tareasDomingo;


    RecyclerView rvDiasdelaSemana;
    AdaptadorDiasSemana adapter;

    DatabaseReference reference;
    FirebaseUser firebaseUser;

    ArrayList<Tarea> listaTareas;
    ArrayList<Usuario> mUsers;



    FloatingActionsMenu fam;




    private String emailPersona;
    private final Usuario[] usr = new Usuario[1];
    String codCasa;

    @Override
    public int cargarLayout() {
        return R.layout.activity_tareas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_tareas);


        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        setActActual(TAREAS);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Usuarios");



        emailPersona = firebaseUser.getEmail();
        mUsers= new ArrayList<>();

        tareasDias= new ArrayList<>();
        listaTareas= new ArrayList<>();


        tareasLunes= new ArrayList<>();
        tareasMartes= new ArrayList<>();
        tareasMiercoles= new ArrayList<>();
        tareasJueves= new ArrayList<>();
        tareasViernes= new ArrayList<>();
        tareasSabado= new ArrayList<>();
        tareasDomingo= new ArrayList<>();



        rvDiasdelaSemana = findViewById(R.id.rvDiasdelaSemana);
        rvDiasdelaSemana.setHasFixedSize(true);

        fam=findViewById(R.id.grupoFavTareas);


        final FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab1tarea);
        final FloatingActionButton fab2=(FloatingActionButton)findViewById(R.id.fab2tarea);

        cargarCodCasa();
        rellenarLista();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(getApplicationContext(),AniadirTareasActivity.class);
                startActivity(i);

            }
        });
        listaTareas.clear();

    }


    public void rellenarLista() {

        listaTareas.clear();
        reference = FirebaseDatabase.getInstance().getReference("Tareas");

        rvDiasdelaSemana.setLayoutManager(new LinearLayoutManager(this));


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listaTareas.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Tarea tarea;

                    tarea=snapshot.getValue(Tarea.class);

                    if(tarea.getCodigoCasa().equals(codCasa)){

                        listaTareas.add(tarea);


                    }

                }


                for(Tarea atarea: listaTareas){

                        if(atarea.getDiaSemana().equals("Lunes")){

                            tareasLunes.add(new TareasVisual(atarea.getKeyTarea(),atarea.getPersona().getFotoUsuario(),atarea.getPersona().getNombreUsuario(),atarea.getTipoTarea()));

                        }else if (atarea.getDiaSemana().equals("Martes")){
                            tareasMartes.add(new TareasVisual(atarea.getKeyTarea(),atarea.getPersona().getFotoUsuario(),atarea.getPersona().getNombreUsuario(),atarea.getTipoTarea()));


                        }else if (atarea.getDiaSemana().equals("Miercoles")){
                            tareasMiercoles.add(new TareasVisual(atarea.getKeyTarea(),atarea.getPersona().getFotoUsuario(),atarea.getPersona().getNombreUsuario(),atarea.getTipoTarea()));

                        }else if (atarea.getDiaSemana().equals("Jueves")){

                            tareasJueves.add(new TareasVisual(atarea.getKeyTarea(),atarea.getPersona().getFotoUsuario(),atarea.getPersona().getNombreUsuario(),atarea.getTipoTarea()));

                        }else if (atarea.getDiaSemana().equals("Viernes")){
                            tareasViernes.add(new TareasVisual(atarea.getKeyTarea(),atarea.getPersona().getFotoUsuario(),atarea.getPersona().getNombreUsuario(),atarea.getTipoTarea()));


                        }else if (atarea.getDiaSemana().equals("Sabado")){
                            tareasSabado.add(new TareasVisual(atarea.getKeyTarea(),atarea.getPersona().getFotoUsuario(),atarea.getPersona().getNombreUsuario(),atarea.getTipoTarea()));


                        }else if (atarea.getDiaSemana().equals("Domingo")){

                            tareasDomingo.add(new TareasVisual(atarea.getKeyTarea(),atarea.getPersona().getFotoUsuario(),atarea.getPersona().getNombreUsuario(),atarea.getTipoTarea()));

                        }


                }

                if(tareasLunes.size()!=0){

                    DiasTareas diasTareasL = new DiasTareas("Lunes", tareasLunes);
                    tareasDias.add(diasTareasL);

                }
                if(tareasMartes.size()!=0){

                    DiasTareas diasTareasM = new DiasTareas("Martes", tareasMartes);
                    tareasDias.add(diasTareasM);

                }
                if(tareasMiercoles.size()!=0){

                    DiasTareas diasTareasX = new DiasTareas("Miercoles", tareasMiercoles);
                    tareasDias.add(diasTareasX);


                }
                if(tareasJueves.size()!=0){
                    DiasTareas diasTareasJ = new DiasTareas("Jueves", tareasJueves);
                    tareasDias.add(diasTareasJ);


                }
                if(tareasViernes.size()!=0){
                    DiasTareas diasTareasJ = new DiasTareas("Viernes", tareasViernes);
                    tareasDias.add(diasTareasJ);


                }
                if(tareasSabado.size()!=0){
                    DiasTareas diasTareasS = new DiasTareas("Sabado", tareasSabado);
                    tareasDias.add(diasTareasS);


                }
                if(tareasDomingo.size()!=0){
                    DiasTareas diasTareasD = new DiasTareas("Domingo", tareasDomingo);
                    tareasDias.add(diasTareasD);


                }
                adapter = new AdaptadorDiasSemana(getApplicationContext(), tareasDias);
                rvDiasdelaSemana.setAdapter(adapter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listaTareas.clear();

    }

    public void cargarCodCasa() {


        reference = FirebaseDatabase.getInstance().getReference("Usuarios");

        Query qq = reference.orderByChild("emailUsuario").equalTo(emailPersona);

        qq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    usr[0] = dataSnapshot1.getValue(Usuario.class);
                    listaTareas.clear();


                }

                if (emailPersona.equals(usr[0].getEmailUsuario())) {

                    codCasa = usr[0].getCodCasa();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_chat,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TareasActivity.this, ActivityCaseroInquilino.class));
                finish();
                return true;
        }
        return false;
    }



}