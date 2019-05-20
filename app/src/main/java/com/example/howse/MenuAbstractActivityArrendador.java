package com.example.howse;

import android.content.Intent;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public abstract class MenuAbstractActivityArrendador extends AppCompatActivity {


    public static final int CHATARR= 1;
    public static final int PERFILARR = 2;

    int actActual=2;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.nav_chat:
                    if(actActual!=1){
                        Intent i1 = new Intent(MenuAbstractActivityArrendador.this, PreviewChatArrendador.class);
                        startActivity(i1);
                    }

                    return true;
                case R.id.nav_perfil:
                    if(actActual!=2){
                        Intent i2 = new Intent(MenuAbstractActivityArrendador.this, PerfilArrendador.class);
                        startActivity(i2);
                    }
                    return true;
            }
            return false;
        }
    };

    public abstract int cargarLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_abstract_arrendador);

        View v= findViewById(R.id.relative);
        RelativeLayout rel= (RelativeLayout) v;
        getLayoutInflater().inflate(cargarLayout(),rel);

        BottomNavigationView navigation=(BottomNavigationView) findViewById(R.id.navigation_arrendador);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }

    /**
     * Se llama al crear la actividad y establecer que actividad es
     * @param actActual
     */
    public void setActActual(int actActual) {
        this.actActual = actActual;
    }

    /**
     * Determina que al dar al botón de atrás, todas las actividades que extienden de esta clase
     * vayan al main activity, excepto el main que termina su actividad.
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if(actActual!=1){
            Intent i1= new Intent(MenuAbstractActivityArrendador.this, PreviewDelChat.class);
            startActivity(i1);

        }else{

            android.os.Process.killProcess( Process.myPid() );
            System.exit(0);
        }
    }
}
