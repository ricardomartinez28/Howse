package com.example.howse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PruebaPaBorrar extends MenuAbstractActivity {

    TextView tvBienvenida;

    @Override
    public int cargarLayout() {
        return R.layout.activity_prueba_pa_borrar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_prueba_pa_borrar );
        tvBienvenida=findViewById(R.id.tvBIenvenida);

        //tvBienvenida.setText(getIntent().getStringExtra("tipo").toString());
    }
}
