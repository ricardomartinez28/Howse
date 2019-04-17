package com.example.howse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText Email, Password;
    private FirebaseAuth auth;
    private Button btnRegistrar, btnRecuperar, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        auth = FirebaseAuth.getInstance();

        Email = (EditText) findViewById( R.id.etCorreoLogin );
    }
}
