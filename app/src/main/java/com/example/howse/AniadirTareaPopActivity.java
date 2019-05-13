package com.example.howse;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.howse.javabean.Actividad;
import com.example.howse.javabean.Tarea;
import com.example.howse.javabean.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
//TODO ARREGLAR TODO
public class AniadirTareaPopActivity extends Activity {

    private EditText actividad;
    private String nombreActividad;

    private String emailPersona;
    private String codCasa;

    private final Usuario[] usR = new Usuario[1];

    private DatabaseReference mDatabaseRef;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_aniadir_tarea_pop );

        actividad = (EditText) findViewById( R.id.etActividad );

        mDatabaseRef = FirebaseDatabase.getInstance().getReference( "Usuarios" );
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        emailPersona = firebaseUser.getEmail();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics( dm );

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.7), (int)(height*.4) );

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes( params );
        cargarCodCasa();
    }

    public void subirActividad(View v){
        mDatabaseRef = FirebaseDatabase.getInstance().getReference( "Actividades" );
        nombreActividad = actividad.getText().toString().trim();

        final String clave = mDatabaseRef.push().getKey();
        Actividad act = new Actividad( nombreActividad, codCasa );
        mDatabaseRef.child( clave ).setValue( act );
        finish();
    }
    public void cargarCodCasa(){

        Query qq = mDatabaseRef.orderByChild("emailUsuario").equalTo(emailPersona);

        qq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    usR[0] = dataSnapshot1.getValue( Usuario.class);
                }

                if (firebaseUser.getEmail().equals( usR[0].getEmailUsuario() )){

                    codCasa=usR[0].getCodCasa();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

