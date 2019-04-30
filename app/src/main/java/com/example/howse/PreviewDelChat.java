package com.example.howse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PreviewDelChat extends MenuAbstractActivity {
    @Override
    public int cargarLayout() {
        return R.layout.activity_preview_del_chat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_preview_del_chat);
        setActActual(CHAT);

        getSupportActionBar().hide();

    }


}
