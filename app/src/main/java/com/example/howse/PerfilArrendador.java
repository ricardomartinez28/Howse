package com.example.howse;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;


//TODO TENGO QUE CARGAR EL CODIGO CASA EN EL PERFIL DEL ARRENDADOR CON UN BOTON DE COPIAR EN PORTAPAPELES.

public class PerfilArrendador extends MenuAbstractActivityArrendador {
    private ImageView fotoPerfil;

    private TextView Email;
    private TextView codigo;
    private EditText Nombre;
    private EditText Apellido;

    private Button btnModificar;
    private Button btnClipBoard;

    private FloatingActionButton fba;

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser usuario;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    private String codCasa;

    private String nombrePersona;
    private String emailPersona;
    private String apellidoPersona;
    private String fotoPersona;

    private final Usuario[] usr = new Usuario[1];


    @Override
    public int cargarLayout() {
        return R.layout.activity_perfil_arrendador;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //setContentView( R.layout.activity_perfil_arrendador );
        setActActual(PERFILARR);

        Email = (TextView) findViewById( R.id.tvEmail );
        codigo = (TextView) findViewById( R.id.tvCodCasa );
        fotoPerfil = (ImageView) findViewById( R.id.imgvFotoPerfil );
        Nombre = (EditText) findViewById( R.id.etNombre );
        Apellido = (EditText) findViewById( R.id.etApellido );
        btnModificar = (Button) findViewById( R.id.btnModificarDatosPerfil );
        btnClipBoard = (Button) findViewById( R.id.btnClipBoard );
        fba = (FloatingActionButton) findViewById( R.id.fbaEditar );

        mDatabaseRef = FirebaseDatabase.getInstance().getReference( "Usuarios" );

        storageReference = FirebaseStorage.getInstance().getReference("FotosPerfilCas");

        firebaseAuth = FirebaseAuth.getInstance();
        usuario = firebaseAuth.getCurrentUser();
        emailPersona = usuario.getEmail();


        btnModificar.setVisibility(View.INVISIBLE);
        btnClipBoard.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = codigo.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService( Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text",  text);
                clipboard.setPrimaryClip(clip);
                Toast.makeText( PerfilArrendador.this, "Copiado en el Portapapeles", Toast.LENGTH_LONG ).show();


            }
        } );

        cargarDatos();

        fotoPerfil.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImage();
            }
        } );

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
                        Toast.makeText( PerfilArrendador.this, "Failed!", Toast.LENGTH_SHORT ).show();
                    }

                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( PerfilArrendador.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
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
                    codCasa = usr[0].getCodCasa();

                    Nombre.setText( nombrePersona );

                    Email.setText(  emailPersona );
                    Apellido.setText( apellidoPersona );

                    Glide.with(fotoPerfil.getContext())
                            .load(usr[0].getFotoUsuario())
                            .into(fotoPerfil);

                    codigo.setText( codCasa );

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

            mDatabaseRef.child(usr[0].getKeyUsuario()).setValue(usr[0]);


            deshabilitar();

        }

    }
    private void mensaje(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }


}