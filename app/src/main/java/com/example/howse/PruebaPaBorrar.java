package com.example.howse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PruebaPaBorrar extends AppCompatActivity {

    TextView tvBienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_prueba_pa_borrar );
        tvBienvenida=findViewById(R.id.tvBIenvenida);

        //tvBienvenida.setText(getIntent().getStringExtra("tipo").toString());
    }
}
