package com.example.howse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Login extends AppCompatActivity  {
    private EditText etEmail, etPassword;
    private TextView tvLogo;

    private FirebaseAuth auth;
    private DatabaseReference mDatabaseRef;

    private boolean tipoUs;
    private TextView tvLogIn;

    private String codCasa="";
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_login );

        etEmail = (EditText) findViewById( R.id.etCorreoLogin );
        etPassword = (EditText) findViewById( R.id.etPasswordLogin );
        tvLogIn= findViewById(R.id.tvLogIn);

        auth = FirebaseAuth.getInstance();

        tvLogo=findViewById(R.id.tvLogoLogIn);

        String logo="Howse";
        SpannableString ss1= new SpannableString(logo);
        ForegroundColorSpan fcsOrange= new ForegroundColorSpan(Color.rgb(242,169,34));

        ss1.setSpan(fcsOrange,1,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLogo.setText(ss1);

        tipoUs=getIntent().getBooleanExtra("tipo",true);
        codCasa=getIntent().getStringExtra("codCasa");


        if (tipoUs) {

            tvLogIn.setText("Inquilino");
        }else{
            tvLogIn.setText("Arrendador");
        }

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Usuarios");

    }

    public void atras(View v){
        finish();
    }
    public void logIn(View v) {
        loguearse();

    }

    public void register(View v) {

        if(tipoUs){

            Intent i= new Intent(Login.this, Register.class);
            i.putExtra("tipo",tipoUs);
            i.putExtra("codCasa", codCasa);
            startActivity(i);

        }else{

            Intent i= new Intent(Login.this, Register.class);
            i.putExtra("tipo",tipoUs);
            startActivity(i);

        }

    }

    public void loguearse(){

         email = etEmail.getText().toString();
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

                            if (tipoUs){
                                Intent i = new Intent( Login.this, TareasActivity.class );
                                startActivity( i );
                            }else{
                                Intent i = new Intent( Login.this, PreviewChatArrendador.class );
                                startActivity( i );
                            }
                        } else {
                            Toast.makeText( Login.this, "El email o la contraseña no es correcta", Toast.LENGTH_LONG ).show();
                        }
                    }
                });

    }

}