package com.example.howse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TareasActivity extends MenuAbstractActivity {

    @Override
    public int cargarLayout() {
        return R.layout.activity_tareas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_tareas);
        setActActual(TAREAS);

    }
}