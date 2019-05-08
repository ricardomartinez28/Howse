package com.example.howse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.howse.javabean.Arrendador;
import com.example.howse.javabean.Inquilino;
import com.example.howse.javabean.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Register extends AppCompatActivity {
    private EditText etNombre;
    private EditText etApellido;
    private EditText etEmail;
    private EditText etPassword, etPasswordRepeat;
    private ProgressDialog progressDialog;

    private static final int PICK_IMAGE_REQUEST = 1;
    final private int RC_PHOTO_ADJ = 1;
    private Uri mImageUri;

    private StorageReference mStorageRefInq;
    private StorageReference mStorageRefCas;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener cel;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    private String email;
    private String password;
    private String password2;
    private String nombre;
    private String apellido;

    private UploadTask ut;
    private Boolean imagenSubida = false;

    private boolean tipoUs;
    private String codCasa;

    private Usuario usuario;
    ArrayList<Usuario>listaArrendadores;




    private char[] conjunto = new char[6];
    char[] elementos={'0','1','2','3','4','5','6','7','8','9' ,'a',
            'b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t',
            'u','v','w','x','y','z'};
    String codigoCasa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        mStorageRefInq = FirebaseStorage.getInstance().getReference("FotosPerfilInq");
        mStorageRefCas = FirebaseStorage.getInstance().getReference("FotosPerfilCas");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Usuarios");


        etNombre = (EditText) findViewById(R.id.etNombreReg);
        etApellido = (EditText) findViewById(R.id.etApellidoReg);
        etEmail = (EditText) findViewById(R.id.etEmailReg);
        etPassword = (EditText) findViewById(R.id.etPasswordReg);
        etPasswordRepeat = (EditText) findViewById(R.id.etPasswordRepeat);



        progressDialog = new ProgressDialog(this);

        tipoUs=getIntent().getBooleanExtra("tipo",true);

        if(tipoUs==true){

            codCasa= getIntent().getStringExtra("codCasa");
        }

        listaArrendadores= new ArrayList<>();









    }

    public void fotoPerfil(View v) {
        enviarFoto();
    }


    public void enviarFoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent,
                "Complete la acción usando"), RC_PHOTO_ADJ);

    }

    public void tipoRegistro(View v){

       if(tipoUs){
           registroInq();

       }else{
           registroCasero();
       }
    }

    public void registroInq() {
        String warning = validarDatos();


        if (warning == null) {
            progressDialog.setMessage( "Realizando registro en linea. . ." );
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                user = firebaseAuth.getCurrentUser();
                                Toast.makeText(Register.this, getString(R.string.msj_userregistered_register) + ": " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();


                                final String clave = mDatabaseRef.push().getKey();
                                if (imagenSubida) {
                                    Uri selectedUri = mImageUri;

                                    StorageReference fotoRef = mStorageRefInq.child(selectedUri.getLastPathSegment());
                                    ut = fotoRef.putFile(selectedUri);

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {

                                            ut.addOnSuccessListener(Register.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                                                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            Usuario usuario = new Usuario(clave, etNombre.getText().toString(), etApellido.getText().toString(), etEmail.getText().toString().toLowerCase(), uri.toString(), codCasa, tipoUs, user.getUid());
                                                            mDatabaseRef.child(user.getUid()).setValue(usuario);

                                                            Intent i = new Intent(Register.this, Login.class);
                                                            i.putExtra("tipo", tipoUs);
                                                            i.putExtra("codCasa", codCasa);
                                                            startActivity(i);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }, 5000);

                                } else {

                                    Usuario usuario = new Usuario(clave, etNombre.getText().toString(), etApellido.getText().toString(), etEmail.getText().toString().toLowerCase(), null, codCasa,tipoUs, user.getUid());
                                    mDatabaseRef.child(user.getUid()).setValue(usuario);

                                    Intent i = new Intent(Register.this, Login.class);
                                    i.putExtra("tipo", tipoUs);
                                    i.putExtra("codCasa", codCasa);
                                    startActivity(i);

                                }

                            } else {
                                Toast.makeText(Register.this, getString(R.string.msj_no_registrado), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });

        } else {
            Toast.makeText(this, warning,
                    Toast.LENGTH_LONG).show();
        }
    }
    public void registroCasero() {
        String warning = validarDatos();


        if (warning == null) {
            progressDialog.setMessage( "Realizando registro en linea. . ." );
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                codCasa=crearCodCasa();


                                mDatabaseRef=FirebaseDatabase.getInstance().getReference().child("Usuarios");

                                addChildEventListener();

                                for (Usuario arrendadores: listaArrendadores){

                                    if(arrendadores.getCodCasa().equals(codCasa) && !arrendadores.getTipoUs()){

                                        codCasa=crearCodCasa();
                                    }
                                }




                                user = firebaseAuth.getCurrentUser();
                                Toast.makeText(Register.this, getString(R.string.msj_userregistered_register) + ": " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();


                                final String clave = mDatabaseRef.push().getKey();
                                if (imagenSubida) {
                                    Uri selectedUri = mImageUri;

                                    StorageReference fotoRef = mStorageRefCas.child(selectedUri.getLastPathSegment());
                                    ut = fotoRef.putFile(selectedUri);

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {

                                            ut.addOnSuccessListener(Register.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                                                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                              usuario = new Usuario(clave, etNombre.getText().toString(), etApellido.getText().toString(), etEmail.getText().toString().toLowerCase(), uri.toString(), codCasa, tipoUs,user.getUid());
                                                            mDatabaseRef.child(user.getUid()).setValue(usuario);

                                                            Intent i = new Intent(Register.this, Login.class);
                                                            i.putExtra("tipo", tipoUs);

                                                            startActivity(i);


                                                            Query qq=mDatabaseRef.orderByChild("emailUsuario").equalTo(email);



                                                            qq.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                                                    if(tipoUs){

                                                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                            usuario = dataSnapshot1.getValue(Usuario.class);
                                                                        }

                                                                        usuario.setCodCasa(codCasa);

                                                                        mDatabaseRef.child(user.getUid()).setValue(usuario);

                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });


                                                            if(tipoUs){
                                                                mDatabaseRef.child("codCasa").setValue(codCasa);
                                                            }

                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }, 5000);

                                } else {

                                    Usuario usuario= new Usuario(clave,etNombre.getText().toString(),etApellido.getText().toString(),etEmail.getText().toString(), null,codCasa,tipoUs,user.getUid());
                                    mDatabaseRef.child(user.getUid()).setValue(usuario);

                                    Intent i = new Intent(Register.this, Login.class);
                                    i.putExtra("tipo", tipoUs);
                                    startActivity(i);

                                }

                            } else {
                                Toast.makeText(Register.this, getString(R.string.msj_no_registrado), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });

        } else {
            Toast.makeText(this, warning,
                    Toast.LENGTH_LONG).show();
        }
    }
    private String validarDatos() {

        String msj = null;

        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        password2 = etPasswordRepeat.getText().toString().trim();
        nombre = etNombre.getText().toString().trim();
        apellido = etApellido.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            msj = "Debe introducirse el email y la password";
        }else if(nombre.isEmpty() || apellido.isEmpty()) {
            msj = "Debe introducir el nombre y el apellido";
        }else if (password.length() < 6) {
            msj = "La password debe contener al menos 6 caracteres";
        } else if (!password.equals(password2)) {
            msj = "Las contraseñas deben de coincidir";
        }

        return msj;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_ADJ) {
            if (resultCode == RESULT_OK) {


                mImageUri = data.getData();


                InputStream is;

                try {
                    is = getContentResolver().openInputStream(mImageUri);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);

                    //creamos el drawable redondeado
                    RoundedBitmapDrawable roundedDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), bitmap);

                    //asignamos el CornerRadius
                    roundedDrawable.setCornerRadius(bitmap.getHeight());


                    ImageView iv = (ImageView) findViewById(R.id.imgvFotoPerfil);
                    iv.setImageDrawable(roundedDrawable);

                    imagenSubida = true;


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(this, "Error al obtener la foto", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void addChildEventListener(){
        if (cel==null){
            cel= new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Usuario usuario= dataSnapshot.getValue(Usuario.class);


                    if(!usuario.getTipoUs()){
                        listaArrendadores.add(usuario);
                    }







                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mDatabaseRef.addChildEventListener(cel);
        }



    }


    public String crearCodCasa(){

        for(int i=0;i<6;i++){
            int el = (int)(Math.random()*36);
            conjunto[i] = (char)elementos[el];
        }
        return codigoCasa = new String(conjunto);
    }



}
