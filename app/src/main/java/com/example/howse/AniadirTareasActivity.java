package com.example.howse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.howse.javabean.Actividad;
import com.example.howse.javabean.Tarea;
import com.example.howse.javabean.Usuario;
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
import java.util.List;

public class AniadirTareasActivity extends AppCompatActivity {
    private Spinner spDia;
    private Spinner spPersona;
    private Spinner spTarea;

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private List<Usuario> mUsers;
    private List<Actividad> mAct;

    private ArrayList<String> nombreUsuarios;
    private ArrayList<String> nombreActividades;


    private final Usuario[] usR = new Usuario[1];

    private String emailPersona;
    private String codCasa;

    private String dia;
    private String persona;

    private String tarea;
    private Usuario usuario;

    private Actividad actividad;

    private String nombres;

    private String actividades;
    Usuario usuarioTarea;

    private String porDefectoPersona="--Selecciona una Persona--";
    private String porDefectoDia="--Selecciona un dia--";
    private String porDefectoActividad="--Selecciona una Actividad--";

    private String imail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_aniadir_tareas );

        spDia = (Spinner) findViewById( R.id.spDia );
        spPersona = (Spinner) findViewById( R.id.spPersona );
        spTarea = (Spinner) findViewById( R.id.spTarea );

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        emailPersona = firebaseUser.getEmail();
        cargarCodCasa();
        mUsers = new ArrayList<>();
        mAct = new ArrayList<>();

        ArrayList<String> dias = new ArrayList<String>();
        nombreUsuarios = new ArrayList<String>();
        nombreActividades = new ArrayList<String>();

        dias.add( porDefectoDia);
        dias.add( "Lunes" );
        dias.add( "Martes" );
        dias.add( "Miercoles" );
        dias.add( "Jueves" );
        dias.add( "Viernes" );
        dias.add( "Sabado" );
        dias.add( "Domingo" );


        readActividad();

        nombreActividades.add( porDefectoActividad );
        nombreActividades.add( "Lavar" );
        nombreActividades.add( "Cocinar" );



        nombreUsuarios.add( porDefectoPersona );

        readUsers();

        ArrayAdapter adpDias = new ArrayAdapter(
                AniadirTareasActivity.this, android.R.layout.simple_spinner_dropdown_item, dias );

        ArrayAdapter adpPersonas = new ArrayAdapter(
                AniadirTareasActivity.this, android.R.layout.simple_spinner_dropdown_item, nombreUsuarios);

        ArrayAdapter adpTareas = new ArrayAdapter(
                AniadirTareasActivity.this, android.R.layout.simple_spinner_dropdown_item, nombreActividades );

        spDia.setAdapter( adpDias );
        spPersona.setAdapter( adpPersonas );
        spTarea.setAdapter( adpTareas );

        spDia.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dia = (String) spDia.getAdapter().getItem( position );

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        spPersona.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                persona = (String) spPersona.getAdapter().getItem( position );

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        spTarea.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tarea = (String) spTarea.getAdapter().getItem( position );

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
    }

    public void abrirPop(View v){
        nombreActividades.clear();
        nombreActividades.add( porDefectoActividad );
        nombreActividades.add( "Lavar" );
        nombreActividades.add( "Cocinar" );

        Intent i = new Intent( AniadirTareasActivity.this, AniadirActividadPopActivity.class );
        startActivity(i);
    }

    public void cargarDatos(View v) {
        if(persona.equals( porDefectoPersona )){
            Toast.makeText( this, "Elige una Persona para continuar", Toast.LENGTH_LONG ).show();

        }else if (dia.equals( porDefectoDia )){
            Toast.makeText( this, "Elige un Dia para continuar", Toast.LENGTH_LONG ).show();

        }else if (tarea.equals( porDefectoActividad )){
            Toast.makeText( this, "Elige una Actividad para continuar", Toast.LENGTH_LONG ).show();

        }else {
            mDatabaseRef = FirebaseDatabase.getInstance().getReference().child( "Tareas" );

            for(Usuario usuarioFin: mUsers){
                if(usuarioFin.getNombreUsuario().equals(persona)){
                    usuarioTarea=usuarioFin;

                }
            }

            final String clave = mDatabaseRef.push().getKey();
            Tarea tar = new Tarea( usuarioTarea, tarea, dia, codCasa );
            mDatabaseRef.child( clave ).setValue( tar );

            Toast.makeText( AniadirTareasActivity.this, persona + " Tiene que " + tarea + " el dia " + dia, Toast.LENGTH_LONG ).show();

            Toast.makeText( AniadirTareasActivity.this, "Tarea Añadida para "+persona, Toast.LENGTH_LONG ).show();
        }
    }

    public void cargarCodCasa(){
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child( "Usuarios" );
        Query qq = mDatabaseRef.orderByChild("emailUsuario").equalTo(emailPersona);

        qq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    usR[0] = dataSnapshot1.getValue(Usuario.class);
                }

                if (firebaseUser.getEmail().equals( usR[0].getEmailUsuario() )){

                    codCasa=usR[0].getCodCasa();
                }
                if (codCasa.equals( usR[0].getCodCasa() )){

                    imail=usR[0].getEmailUsuario();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readUsers() {

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child( "Usuarios" );

        mDatabaseRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    try {
                        usuario = snapshot.getValue( Usuario.class );
                    } catch (DatabaseException de) {

                        break;
                    }
                    assert usuario != null;
                    assert firebaseUser != null;


                    if ( usuario.getCodCasa().equals( codCasa )&&usuario.getTipoUs()) {

                        mUsers.add( usuario );
                    }

                }
                for (Usuario usr : mUsers){
                    nombres=usr.getNombreUsuario();

                    nombreUsuarios.add( nombres );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    private void readActividad(){

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child( "Actividades" );

        mDatabaseRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mAct.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    try {
                        actividad = snapshot.getValue( Actividad.class );
                    } catch (DatabaseException de) {

                        break;
                    }
                    assert actividad != null;
                    assert firebaseUser != null;

                    if ( actividad.getCodCasa().equals( codCasa )) {

                        mAct.add( actividad );
                    }

                }
                for (Actividad act : mAct){
                    actividades=act.getNombre();

                    nombreActividades.add( actividades );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    public void abrirTabla(View v){

    }

}
