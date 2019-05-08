package com.example.howse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ListaDeLaCompraActivity extends MenuAbstractActivity {

    @Override
    public int cargarLayout() {
        return R.layout.activity_lista_de_la_compra;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_lista_de_la_compra);
        setActActual(COMPRA);
    }
}
