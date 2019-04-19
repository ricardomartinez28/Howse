package com.example.howse;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        firebaseAuth = FirebaseAuth.getInstance();

        etEmail = (EditText) findViewById( R.id.etEmailReg );
        etPassword = (EditText) findViewById( R.id.etPasswordReg );

        btnRegistrar = (Button) findViewById( R.id.btnRegistrar );

        progressDialog = new ProgressDialog( this );

        btnRegistrar.setOnClickListener( this );
    }

    private void registrarUsuario(){

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty( email )){
            Toast.makeText( this, "Se debe ingresar un email", Toast.LENGTH_LONG ).show();
            return;
        }

        if(TextUtils.isEmpty( password )){
            Toast.makeText( this, "Falta ingresar la contraseña", Toast.LENGTH_LONG ).show();
        }

        progressDialog.setMessage( "Realizando registro en linea. . ." );
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword( email, password ).addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText( Register.this, "Se ha registrado el email", Toast.LENGTH_LONG ).show();
                    finish();
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText( Register.this, "Este usuario ya existe", Toast.LENGTH_SHORT ).show();
                    }else {
                        Toast.makeText( Register.this, "No se pudo registrar el usuario", Toast.LENGTH_LONG ).show();
                    }
                }
                progressDialog.dismiss();
            }
        } );

    }

    @Override
    public void onClick(View v) {
        registrarUsuario();

    }
}
