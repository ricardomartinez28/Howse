package com.example.howse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public abstract class MenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        navigationView=(BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_chat) {
                startActivity(new Intent(this, PreviewDelChat.class));
            } else if (itemId == R.id.nav_lista) {
                startActivity(new Intent(this, TareasActivity.class));
            } else if (itemId == R.id.nav_compra) {
                startActivity(new Intent(this, ListaCompraActivity.class));
            } else if (itemId == R.id.nav_perfil) {
                startActivity(new Intent(this, Perfil.class));
            }
            finish();
        }, 30);
        return true;
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();
}
