package com.example.howse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class MenuAbstractActivity extends AppCompatActivity {


    public static final int LISTA= 1;
    public static final int CHAT = 2;
    public static final int COMPRA = 3;
    public static final int PERFIL = 4;

    int actual=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_abstract);

    }
}
