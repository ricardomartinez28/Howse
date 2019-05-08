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




    private final Usuario[] usR = new Usuario[1];
    Usuario usFin;

    private String emailPersona;
    private String codCasa;

    private String dia;
    private String persona;
    private String tarea;

    private String nombres;

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
        final ArrayList<String> nombreUsuarios = new ArrayList<String>();
        ArrayList<String> tareas = new ArrayList<String>();

        dias.add( "Lunes" );
        dias.add( "Martes" );
        dias.add( "Miercoles" );
        dias.add( "Jueves" );
        dias.add( "Viernes" );
        dias.add( "Sabado" );
        dias.add( "Domingo" );

        tareas.add( "Lavar" );
        tareas.add( "Cocinar" );

        readUsers();

        ArrayAdapter adpDias = new ArrayAdapter(
                TareasActivity.this, android.R.layout.simple_spinner_dropdown_item, dias );

        ArrayAdapter adpPersonas = new ArrayAdapter(
                TareasActivity.this, android.R.layout.simple_spinner_dropdown_item, nombreUsuarios );

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

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child( "Tareas" );
        
        final String clave = mDatabaseRef.push().getKey();
        Tarea tar = new Tarea( persona, tarea, dia );
        mDatabaseRef.child( clave ).setValue( tar );

        Toast.makeText( TareasActivity.this, persona + " Tiene que " + tarea + " el dia " + dia, Toast.LENGTH_LONG ).show();

        Toast.makeText( TareasActivity.this, "Tarea Añadida", Toast.LENGTH_SHORT ).show();

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

                if(usR[0]==null){
                    System.out.println(usR[0].getApellidosUsuario()+"Estooooooooooooooooo es nuloooooooooooooooooooooooooooooo");

                }else {
                    System.out.println( usR[0].getApellidosUsuario() + "Hasdddddddddddddd es el email" );
                }
                if (firebaseUser.getEmail().equals( usR[0].getEmailUsuario() )){

                    codCasa=usR[0].getCodCasa();
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
                    Usuario usuario;
                    try {
                        usuario = snapshot.getValue( Usuario.class );
                    } catch (DatabaseException de) {

                        break;
                    }
                    assert usuario != null;
                    assert firebaseUser != null;


                    if (usuario.getEmailUsuario().equals( firebaseUser.getEmail() )) {
                        usFin = usuario;


                    }


                    if (!(usuario.getEmailUsuario().equals( firebaseUser.getEmail() )) && usuario.getCodCasa().equals( codCasa )) {


                        mUsers.add( usuario );
                        System.out.println(usuario.getApellidosUsuario()+"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
                    }

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }
}
