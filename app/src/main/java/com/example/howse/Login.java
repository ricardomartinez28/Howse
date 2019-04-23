package com.example.howse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.howse.javabean.Inquilino;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity  {
    private EditText etEmail, etPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnRegistrar, btnLogin, btnRecuperarPs;
    private DatabaseReference mDatabaseRef;

    private ChildEventListener cel;
    private ArrayList<Inquilino> datos;
    private String id;

    private String nombrePersona;
    private String emailPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Usuarios");

        btnRegistrar = (Button) findViewById( R.id.btnRegistrarte );
        btnLogin = (Button) findViewById( R.id.btnIniciarSesion );

        etEmail = (EditText) findViewById( R.id.etCorreoLogin );
        etPassword = (EditText) findViewById( R.id.etPasswordLogin );

        auth = FirebaseAuth.getInstance();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loguearse();

            }
        });

    }
    private void addChildEventListener() {
        if (cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    emailPersona = etEmail.getText().toString();
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

            mDatabaseRef.addChildEventListener(cel);
        }
    }
    public void loguearse(){

        String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Introduce el Email, por favor", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Introduce la Contraseña, por favor", Toast.LENGTH_LONG).show();
            return;
        }

        //AUTENTIFICACION
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText( Login.this, "Bienvenido " +nombrePersona, Toast.LENGTH_SHORT ).show();

                            Intent i = new Intent( Login.this, PruebaPaBorrar.class );//TODO AQUI TE LLEVA A LA VENTANA DEL CHAT
                            startActivity(i);
                        } else {
                            Toast.makeText( Login.this, "La JODISTE MAMON", Toast.LENGTH_LONG ).show();
                        }
                    }
                });

        addChildEventListener();

    }



}