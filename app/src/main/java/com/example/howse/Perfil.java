package com.example.howse;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.howse.javabean.Arrendador;
import com.example.howse.javabean.Inquilino;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

//TODO MIRAR COMO SACAR EL USUARIO SIN TENER A MANO EL CORREO NI NADA
public class Perfil extends MenuAbstractActivity {
    private ImageView fotoPerfil;
    private TextView Email;
    private EditText Nombre;
    private EditText Apellido;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser usuario;

    private String nombrePersona;
    private String emailPersona;
    private String apellidoPersona;
    private String fotoPersona;


    private final Inquilino[] inq = new Inquilino[1];
    private final Arrendador[] arren = new Arrendador[1];



    @Override
    public int cargarLayout() {
        return R.layout.activity_perfil;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //setContentView( R.layout.activity_perfil );
        setActActual(PERFIL);

        Email = (TextView) findViewById( R.id.tvEmail );
        fotoPerfil = (ImageView) findViewById( R.id.imgvFotoPerfil );
        Nombre = (EditText) findViewById( R.id.etNombre );
        Apellido = (EditText) findViewById( R.id.etApellido );

        mDatabaseRef = FirebaseDatabase.getInstance().getReference( "Usuarios" );

        firebaseAuth = FirebaseAuth.getInstance();
        usuario = firebaseAuth.getCurrentUser();
        emailPersona = usuario.getEmail();

        cargarDatos();

    }

    private void cargarDatos() {
        Query qq = mDatabaseRef.orderByChild("emailUsuario").equalTo(emailPersona);

        qq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                        inq[0] = dataSnapshot1.getValue(Inquilino.class);
                    }

                    if (emailPersona.equals( inq[0].getEmailUsuario() )){

                        nombrePersona = inq[0].getNombreUsuario();
                        apellidoPersona = inq[0].getApellidosUsuario();
                        fotoPersona = inq[0].getFotoUsuario();

                        Nombre.setText( nombrePersona );
                        Email.setText( emailPersona );
                        Apellido.setText( apellidoPersona );

                        Glide.with(fotoPerfil.getContext())
                                .load(inq[0].getFotoUsuario())
                                .into(fotoPerfil);

                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( Perfil.this, "Algo salio Mal", Toast.LENGTH_SHORT ).show();

            }
        });

    }
    public void modificarDatos(View view) {



        if(Apellido.getText().toString().trim().equalsIgnoreCase("")){
            mensaje("No has agregado ningun Apellido");

        }else{
            inq[0].setApellidosUsuario(Apellido.getText().toString().trim());
        }

        if(Nombre.getText().toString().trim().equalsIgnoreCase("")){
            mensaje("El campo nombre no puede estar vacio");
            Nombre.setText("");

        }else{
            inq[0].setNombreUsuario(Nombre.getText().toString().trim());

            mDatabaseRef.child(inq[0].getKeyUsuario()).setValue(inq[0]);

            cargarDatos();

        }

    }
    private void mensaje(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }


}






