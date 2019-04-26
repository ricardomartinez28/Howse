package com.example.howse;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.howse.javabean.Inquilino;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

//TODO MIRAR COMO SACAR EL USUARIO SIN TENER A MANO EL CORREO NI NADA
public class Perfil extends AppCompatActivity {
    private ImageView fotoPerfil;
    private TextView Email;
    private EditText Nombre;
    private EditText Apellido;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser usuario;

    private String nombrePersona;
    private String emailPersona;

    private String nom;
    private String apellido;

    private final Inquilino[] user = new Inquilino[0];
    private String eemail;
    private String cod;
    private String noombre;
    private String apeellido;
    private ChildEventListener cel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_perfil );

        Email = (TextView) findViewById( R.id.tvEmail );
        fotoPerfil = (ImageView) findViewById( R.id.imgvFotoPerfil );
        Nombre = (EditText) findViewById( R.id.etNombre );
        Apellido = (EditText) findViewById( R.id.etApellido );

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        firebaseAuth = FirebaseAuth.getInstance();
        usuario = firebaseAuth.getCurrentUser();
        eemail = usuario.getEmail();
        cod = usuario.getUid();
        //noombre = usuario.

        cargarDatos();
         //cargartv();

    }
    public void cargartv(){
        Email.setText( eemail );
        Nombre.setText( noombre );
        Apellido.setText( apeellido );
    }
    private void cargarDatos() {
        Query qq = mDatabaseRef.orderByChild( cod ).equalTo( eemail );

        qq.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot0 : dataSnapshot.getChildren()) {
                    user[0] = dataSnapshot0.getValue( Inquilino.class );
                }


                if (user[0].getNombreUsuario() != null) {
                    nom = user[0].getNombreUsuario();
                    getSupportActionBar().setTitle( nom );
                }else{
                    Toast.makeText( Perfil.this, "FUCK", Toast.LENGTH_SHORT ).show();
                }

                String urlImagen;

                if (user[0].getFotoUsuario() != null) {
                    Glide.with( fotoPerfil.getContext() )
                            .load( user[0].getFotoUsuario() )
                            .into( fotoPerfil );
                }else{
                    Toast.makeText( Perfil.this, "FUCK", Toast.LENGTH_SHORT ).show();
                }

                if (user[0].getApellidosUsuario() != null) {
                    apellido = user[0].getApellidosUsuario();
                }else{
                    Toast.makeText( Perfil.this, "FUCK", Toast.LENGTH_SHORT ).show();
                }

                String email;
                if (user[0].getEmailUsuario() != null) {
                    email = user[0].getEmailUsuario();
                }else{
                    Toast.makeText( Perfil.this, "FUCK", Toast.LENGTH_SHORT ).show();
                }

                Toast.makeText( Perfil.this, nom, Toast.LENGTH_LONG ).show();

                Nombre.setText( "Nombre: " + user[0].getNombreUsuario() );
                Apellido.setText("Apellido: " + apellido);
                Email.setText("Email" + eemail);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }




}



















    /*public void recibirDatosLogin(){
        String caca;
        //getIntent().getStringExtra( "Correo", caca);
    }
    private void addChildEventListener() {
        if (cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    //emailPersona = etEmail.getText().toString();
                    Inquilino inq = dataSnapshot.getValue(Inquilino.class);

                    if (emailPersona.equals( inq.getEmailUsuario() )){

                        nombrePersona = inq.getNombreUsuario();
                    }
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

            //mDatabaseRef.addChildEventListener(cel);
        }
    }*/

