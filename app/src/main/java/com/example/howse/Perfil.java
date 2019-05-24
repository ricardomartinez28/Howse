package com.example.howse;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.howse.javabean.Usuario;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;


public class Perfil extends MenuActivity {
    private ImageView fotoPerfil;

    private TextView email;
    private EditText nombre;
    private EditText apellido;
    private TextView casero;

    private Button btnModificar;

    private FloatingActionButton fba;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser usuario;

    private String nombrePersona;
    private String nombreCasero;
    private String emailPersona;
    private String apellidoPersona;
    private String fotoPersona;
    private TextView tvCodCasa;

    private ImageButton btnCopycodCasa;

    private Usuario usu;

    String codCasa;



    private final Usuario[] usr = new Usuario[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        email = (TextView) findViewById( R.id.tvEmail );
        casero = (TextView) findViewById( R.id.tvCodCasa);

        fotoPerfil = (ImageView) findViewById( R.id.imgvFotoPerfil );
        nombre = (EditText) findViewById( R.id.etNombre );
        apellido = (EditText) findViewById( R.id.etApellido );
        btnModificar = (Button) findViewById( R.id.btnModificarDatosPerfil );
        fba = (FloatingActionButton) findViewById( R.id.fbaEditar );
        btnCopycodCasa= findViewById(R.id.btnCopiarCodCasa);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference( "Usuarios" );
        tvCodCasa=findViewById(R.id.tvCodCasa);

        storageReference = FirebaseStorage.getInstance().getReference("FotosPerfilInq");


        firebaseAuth = FirebaseAuth.getInstance();
        usuario = firebaseAuth.getCurrentUser();
        emailPersona = usuario.getEmail();

        cargarCodCasa();


        btnModificar.setVisibility(View.INVISIBLE);

        cargarDatos();
        readUsers();

        fotoPerfil.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImage();
            }
        } );

        btnCopycodCasa.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tvCodCasa.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService( Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text",  text);
                clipboard.setPrimaryClip(clip);
                Toast.makeText( Perfil.this, "Copiado en el Portapapeles", Toast.LENGTH_LONG ).show();


            }
        } );

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_perfil;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_perfil;
    }

    private void openImage() {
        Intent intent = new Intent( );
        intent.setType( "image/*" );
        intent.setAction( Intent.ACTION_GET_CONTENT );
        startActivityForResult( intent, IMAGE_REQUEST );
    }

    private String getFileExtension(Uri uri){

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType( contentResolver.getType( uri ) );
   }

   private void uploadImage(){


        if(imageUri !=null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension( imageUri ));

            uploadTask = fileReference.putFile( imageUri );
            uploadTask.continueWithTask( new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();

                }
            } ).addOnCompleteListener( new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Usuarios" ).child( usuario.getUid() );
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("fotoUsuario", mUri);
                        mDatabaseRef.updateChildren( map );




                    }else{
                        Toast.makeText( Perfil.this, "Failed!", Toast.LENGTH_SHORT ).show();
                    }
                        
                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( Perfil.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            } );

        }else {
            Toast.makeText( this, "Selecciona una imagen ", Toast.LENGTH_SHORT ).show();
        }
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();

            fotoPerfil.setImageURI( imageUri );
            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText( this, "En Proceso", Toast.LENGTH_SHORT ).show();
            }else {
                uploadImage();
            }
        }
    }

    public void deshabilitar(){

        nombre.setEnabled( false );
        apellido.setEnabled( false );
        btnModificar.setEnabled( false );
        btnModificar.setVisibility(View.INVISIBLE);

    }

    public void editable(View v){
        nombre.setEnabled( true );
        apellido.setEnabled( true );
        btnModificar.setEnabled( true );

        btnModificar.setVisibility(View.VISIBLE);
    }
    public void cargarCodCasa(){

        Query qq1 = mDatabaseRef.orderByChild("emailUsuario").equalTo(emailPersona);

        qq1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    usr[0] = dataSnapshot1.getValue(Usuario.class);
                }

                if (emailPersona.equals( usr[0].getEmailUsuario() )){

                    codCasa=usr[0].getCodCasa();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void readUsers() {

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child( "Usuarios" );

        mDatabaseRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    try {
                        usu = snapshot.getValue( Usuario.class );
                    } catch (DatabaseException de) {

                        break;
                    }
                    assert usu != null;
                    assert usuario != null;


                    if ( usu.getCodCasa().equals( codCasa )&&!usu.getTipoUs()) {


                        casero.setText(usu.getCodCasa());
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }
    private void cargarDatos() {
        Query qq3 = mDatabaseRef.orderByChild("emailUsuario").equalTo(emailPersona);

        qq3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    usr[0] = dataSnapshot1.getValue(Usuario.class);
                }

                if (emailPersona.equals( usr[0].getEmailUsuario() )){

                    nombrePersona = usr[0].getNombreUsuario();
                    apellidoPersona = usr[0].getApellidosUsuario();
                    fotoPersona = usr[0].getFotoUsuario();

                    nombre.setText( nombrePersona );

                    email.setText(  emailPersona );
                    apellido.setText( apellidoPersona );

                    Glide.with(fotoPerfil.getContext())
                            .load(usr[0].getFotoUsuario())
                            .into(fotoPerfil);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }

    public void modificarDatos(View view) {

        if(apellido.getText().toString().trim().equalsIgnoreCase("")){
            mensaje("No has agregado ningun Apellido");

        }else{
            usr[0].setApellidosUsuario(apellido.getText().toString().trim());
        }

        if(nombre.getText().toString().trim().equalsIgnoreCase("")){
            mensaje("El campo nombre no puede estar vacio");
            nombre.setText("");

        }else{
            usr[0].setNombreUsuario(nombre.getText().toString().trim());

            mDatabaseRef.child(usr[0].getUid()).setValue(usr[0]);


            deshabilitar();

        }

    }
    private void mensaje(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_chat,menu);
        return true;
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



}