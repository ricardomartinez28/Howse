package com.example.howse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.howse.javabean.Arrendador;
import com.example.howse.javabean.Inquilino;
import com.example.howse.javabean.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity  {
    private EditText etEmail, etPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private DatabaseReference mDatabaseRef;

    private ChildEventListener cel;
    private ArrayList<Inquilino> datos;
    private String id;
    private String email;

    private final Usuario[] usuario= new Usuario[1];

    private String nombrePersona;
    private String emailPersona="";

    private TextView tvLogo;

    private boolean tipoUs;
    private String codCasa="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_login );




        etEmail = (EditText) findViewById( R.id.etCorreoLogin );
        etPassword = (EditText) findViewById( R.id.etPasswordLogin );

        auth = FirebaseAuth.getInstance();


        tvLogo=findViewById(R.id.tvLogoLogIn);





        String logo="Howse";
        SpannableString ss1= new SpannableString(logo);
        ForegroundColorSpan fcsOrange= new ForegroundColorSpan(Color.rgb(242,169,34));


        ss1.setSpan(fcsOrange,1,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLogo.setText(ss1);



        tipoUs=getIntent().getBooleanExtra("tipo",true);
        codCasa=getIntent().getStringExtra("codCasa");


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Usuarios");





    }

    public void logIn(View v) {
        loguearse();
        System.out.println( tipoUs+"BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB" );

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
                            System.out.println( tipoUs+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" );
                            if (tipoUs){
                                Intent i = new Intent( Login.this, PreviewDelChat.class );//TODO AQUI TE LLEVA A LA VENTANA DEL CHAT
                                startActivity( i );
                                System.out.println( tipoUs+"CCCCCCCCCCCCCCCCCCCCCCCCCCC" );
                            }else{
                                Intent i = new Intent( Login.this, PreviewChatArrendador.class );//TODO AQUI TE LLEVA A LA VENTANA DEL CHAT
                                startActivity( i );
                                System.out.println( tipoUs+"HHHHHHHHHHHHHHHHHHHHHHHHHH" );
                            }
                        } else {
                            Toast.makeText( Login.this, "El email o la contraseña no es correcta", Toast.LENGTH_LONG ).show();
                        }
                    }
                });



    }




    }