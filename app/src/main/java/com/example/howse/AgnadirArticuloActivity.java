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
import android.widget.Toast;

import com.example.howse.javabean.Articulo;
import com.example.howse.javabean.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.file.FileVisitResult;
import java.text.DateFormat;
import java.util.Calendar;

public class AgnadirArticuloActivity extends Activity {


    private EditText etArt;
    private EditText etPrecio;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    String emailPersona;

    private final Usuario[] usr = new Usuario[1];
    Usuario usuario;

    String newArt;
    String newPrc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agnadir_articulo);

        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height= dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int)(height*.5));

        WindowManager.LayoutParams params=getWindow().getAttributes();

        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;

        getWindow().setAttributes(params);

        etArt=findViewById(R.id.etNewArticulo);
        etPrecio=findViewById(R.id.etNewPrecio);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        emailPersona=firebaseUser.getEmail();
        reference=  FirebaseDatabase.getInstance().getReference( "Usuarios" );

        caragarUsuario();



    }
    public void caragarUsuario(){

        Query qq = reference.orderByChild("emailUsuario").equalTo(emailPersona);

        qq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    usr[0] = dataSnapshot1.getValue(Usuario.class);
                }

                if (emailPersona.equals( usr[0].getEmailUsuario() )){

                    usuario=usr[0];
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void agnadirArt(View v){

        reference=  FirebaseDatabase.getInstance().getReference( "Articulos" );

        final String clave= reference.push().getKey();
        newArt=etArt.getText().toString();
        newPrc=etPrecio.getText().toString();


        if(newArt.trim().equals("")){
            Toast.makeText(getBaseContext(),"Introduce un nombre para el articulo",Toast.LENGTH_SHORT).show();
        }else{
            Articulo art= new Articulo(newArt,newPrc,usuario,clave,usuario.getCodCasa());

            reference.child(clave).setValue(art);
            reference.push();
            finish();
        }



    }
}
