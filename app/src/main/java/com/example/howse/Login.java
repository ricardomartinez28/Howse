package com.example.howse;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity  {
    private EditText etEmail, etPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnRegistrar, btnLogin, btnRecuperarPs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

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

        //progressBar.setVisibility(View.VISIBLE);

        //AUTENTIFICACION
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText( Login.this, "Bienvenido" +etEmail.getText(), Toast.LENGTH_SHORT ).show();

                            Intent i = new Intent( Login.this, PruebaPaBorrar.class );
                            startActivity(i);
                        } else {
                            Toast.makeText( Login.this, "La JODISTE MAMON", Toast.LENGTH_LONG ).show();
                        }
                    }
                });
    }


}