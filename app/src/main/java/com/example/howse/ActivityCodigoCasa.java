package com.example.howse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

public class ActivityCodigoCasa extends AppCompatActivity {

    EditText codCasa;

    private boolean tipoUs;
    private  String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_casa);

        getSupportActionBar().hide();

        codCasa=findViewById(R.id.etCodigoCasa);

        tipoUs=getIntent().getBooleanExtra("tipo", true);


    }

    public void atras(View v){
        finish();
    }

    public void siguiente(View v){

        codigo= codCasa.getText().toString();

        if(codigo.isEmpty()){
            Toast.makeText(this, "Debes introducir un codigo", Toast.LENGTH_LONG).show();
        }else {

            Intent i = new Intent(ActivityCodigoCasa.this, Login.class);
            i.putExtra("tipo", tipoUs);
            i.putExtra("codCasa", codigo);
            startActivity(i);
        }
    }
}
