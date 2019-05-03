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

import com.example.howse.javabean.Usuario;
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


    private final Usuario[] usr = new Usuario[1];
    



    @Override
    public int cargarLayout() {
        return R.layout.activity_perfil;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //setContentView( R.layout.activity_perfil );
        setActActual(4);

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
                        usr[0] = dataSnapshot1.getValue(Usuario.class);
                    }

                    if (emailPersona.equals( usr[0].getEmailUsuario() )){

                        nombrePersona = usr[0].getNombreUsuario();
                        apellidoPersona = usr[0].getApellidosUsuario();
                        fotoPersona = usr[0].getFotoUsuario();

                        Nombre.setText( nombrePersona );
                        Email.setText( emailPersona );
                        Apellido.setText( apellidoPersona );

                        Glide.with(fotoPerfil.getContext())
                                .load(usr[0].getFotoUsuario())
                                .into(fotoPerfil);

                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( Perfil.this, "Algo salio Mal ai", Toast.LENGTH_SHORT ).show();

            }
        });

    }
    public void modificarDatos(View view) {



        if(Apellido.getText().toString().trim().equalsIgnoreCase("")){
            mensaje("No has agregado ningun Apellido");

        }else{
            usr[0].setApellidosUsuario(Apellido.getText().toString().trim());
        }

        if(Nombre.getText().toString().trim().equalsIgnoreCase("")){
            mensaje("El campo nombre no puede estar vacio");
            Nombre.setText("");

        }else{
            usr[0].setNombreUsuario(Nombre.getText().toString().trim());

            mDatabaseRef.child(usr[0].getKeyUsuario()).setValue(usr[0]);

            cargarDatos();

        }

    }
    private void mensaje(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }


}





