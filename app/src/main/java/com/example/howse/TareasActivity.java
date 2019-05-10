package com.example.howse;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import java.util.Collections;
import java.util.List;

import static java.security.AccessController.getContext;

public class TareasActivity extends AppCompatActivity {
    private Spinner spDia;
    private Spinner spPersona;
    private Spinner spTarea;

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private List<Usuario> mUsers;

    private ArrayList<String> nombreUsuarios;


    private final Usuario[] usR = new Usuario[1];

    private String emailPersona;
    private String codCasa;

    private String dia;
    private String persona;

    private String tarea;
    private Usuario usuario;

    private String nombres;

    private String porDefectoPersona="--Selecciona una Persona--";
    private String porDefectoDia="--Selecciona un dia--";
    private String porDefectoActividad="--Selecciona una Actividad--";

    private String imail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tareas );

        spDia = (Spinner) findViewById( R.id.spDia );
        spPersona = (Spinner) findViewById( R.id.spPersona );
        spTarea = (Spinner) findViewById( R.id.spTarea );

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        emailPersona = firebaseUser.getEmail();
        cargarCodCasa();
        mUsers = new ArrayList<>();

        ArrayList<String> dias = new ArrayList<String>();
        nombreUsuarios = new ArrayList<String>();
        ArrayList<String> tareas = new ArrayList<String>();
        dias.add( porDefectoDia);
        dias.add( "Lunes" );
        dias.add( "Martes" );
        dias.add( "Miercoles" );
        dias.add( "Jueves" );
        dias.add( "Viernes" );
        dias.add( "Sabado" );
        dias.add( "Domingo" );

        tareas.add( porDefectoActividad );
        tareas.add( "Lavar" );
        tareas.add( "Cocinar" );
        nombreUsuarios.add( porDefectoPersona );

        readUsers();



        ArrayAdapter adpDias = new ArrayAdapter(
                TareasActivity.this, android.R.layout.simple_spinner_dropdown_item, dias );

        ArrayAdapter adpPersonas = new ArrayAdapter(
                TareasActivity.this, android.R.layout.simple_spinner_dropdown_item, nombreUsuarios);

        ArrayAdapter adpTareas = new ArrayAdapter(
                TareasActivity.this, android.R.layout.simple_spinner_dropdown_item, tareas );

        spDia.setAdapter( adpDias );
        spPersona.setAdapter( adpPersonas );
        spTarea.setAdapter( adpTareas );

        spDia.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dia = (String) spDia.getAdapter().getItem( position );

                //Toast.makeText( TareasActivity.this, dia+" "+tarea+" "+persona, Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        spPersona.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                persona = (String) spPersona.getAdapter().getItem( position );



                //String selected = spPersona.getItemAtPosition(position).toString();

                //Toast.makeText( TareasActivity.this, dia+" "+tarea+" "+persona, Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        spTarea.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tarea = (String) spTarea.getAdapter().getItem( position );

                //Toast.makeText( TareasActivity.this, dia+" "+tarea+" "+persona, Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
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

        final String clave = mDatabaseRef.push().getKey();
        Tarea tar = new Tarea( persona, tarea, dia );
        mDatabaseRef.child( clave ).setValue( tar );

        Toast.makeText( TareasActivity.this, persona + " Tiene que " + tarea + " el dia " + dia, Toast.LENGTH_LONG ).show();

        Toast.makeText( TareasActivity.this, "Tarea Añadida", Toast.LENGTH_SHORT ).show();
    }
//TODO PRUEBA
        for (String r : nombreUsuarios){
            System.out.println("\n"+r+"\n");
        }

        System.out.println(persona);
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
}
