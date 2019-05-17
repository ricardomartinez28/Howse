package com.example.howse;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.howse.javabean.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


//TODO MIRAR COMO SACAR EL USUARIO SIN TENER A MANO EL CORREO NI NADA
public class Perfil extends MenuAbstractActivity {
    private ImageView fotoPerfil;

    private TextView Email;
    private EditText Nombre;
    private EditText Apellido;

    private Button btnModificar;

    private FloatingActionButton fba;

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser usuario;

    private String nombrePersona;
    private String emailPersona;
    private String apellidoPersona;
    private String fotoPersona;
    private FloatingActionButton fab;
    private final Usuario[] usr = new Usuario[1];

    CircleImageView imgperfil;
    TextView tvnombre;

    FirebaseUser firebaseUser;
    DatabaseReference reference;


    @Override
    public int cargarLayout() {
        return R.layout.activity_perfil;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //setContentView( R.layout.activity_perfil );


        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        setActActual(PERFIL);


        Email = (TextView) findViewById( R.id.tvEmail );
        fotoPerfil = (ImageView) findViewById( R.id.imgvFotoPerfil );
        Nombre = (EditText) findViewById( R.id.etNombre );
        Apellido = (EditText) findViewById( R.id.etApellido );
        btnModificar = (Button) findViewById( R.id.btnModificarDatosPerfil );
        fba = (FloatingActionButton) findViewById( R.id.fbaEditar );

        mDatabaseRef = FirebaseDatabase.getInstance().getReference( "Usuarios" );

        firebaseAuth = FirebaseAuth.getInstance();
        usuario = firebaseAuth.getCurrentUser();
        emailPersona = usuario.getEmail();


        btnModificar.setVisibility(View.INVISIBLE);




        cargarDatos();


    }



    public void deshabilitar(){

        Nombre.setEnabled( false );
        Apellido.setEnabled( false );
        btnModificar.setEnabled( false );
        btnModificar.setVisibility(View.INVISIBLE);


    }

    public void editable(View v){
        Nombre.setEnabled( true );
        Apellido.setEnabled( true );
        btnModificar.setEnabled( true );

        btnModificar.setVisibility(View.VISIBLE);
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

                    Email.setText(  emailPersona );
                    Apellido.setText( apellidoPersona );

                    if(usr[0].getFotoUsuario()==null){

                        fotoPerfil.setImageResource(R.mipmap.ic_launcher_round);
                    }else{

                        Glide.with(fotoPerfil.getContext())
                                .load(usr[0].getFotoUsuario())
                                .into(fotoPerfil);

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


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

            mDatabaseRef.child(usr[0].getUid()).setValue(usr[0]);


            deshabilitar();

        }

    }
    private void mensaje(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Perfil.this, ActivityCaseroInquilino.class));
                finish();
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_chat,menu);
        return true;
    }
}