package com.example.howse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.howse.javabean.Inquilino;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private Button btnRegistrar;
    private ImageView ImgvFotoPefil;
    private ProgressDialog progressDialog;

    private static final int PICK_IMAGE_REQUEST = 1;
    final private int RC_PHOTO_ADJ = 1;
    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    private String email;
    private String password;
    private String password2;
    private String nombre;
    private String apellido;

    private UploadTask ut;
    private Boolean imagenSubida = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        firebaseAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("FotosPerfil");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Usuarios");

        etNombre = (EditText) findViewById( R.id.etNombreReg );
        etApellido = (EditText) findViewById( R.id.etApellidoReg );
        etEmail = (EditText) findViewById( R.id.etEmailReg );
        etPassword = (EditText) findViewById( R.id.etPasswordReg );
        etPasswordRepeat = (EditText) findViewById( R.id.etPasswordRepeat ) ;

        ImgvFotoPefil = (ImageView) findViewById( R.id.imgvFotoPerfil);

        btnRegistrar = (Button) findViewById( R.id.btnRegistrar );

        progressDialog = new ProgressDialog( this );

        ImgvFotoPefil.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarFoto();
            }
        } );
    }
    public void enviarFoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent,
                "Complete la acción usando"), RC_PHOTO_ADJ);

    }

    public void registro(View v) {
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

                                    StorageReference fotoRef = mStorageRef.child(selectedUri.getLastPathSegment());
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
                                                            Inquilino inq = new Inquilino(clave, etNombre.getText().toString(), etApellido.getText().toString(), etEmail.getText().toString().toLowerCase(), uri.toString(), null);
                                                            mDatabaseRef.child(clave).setValue(inq);

                                                            Intent i = new Intent(Register.this, Login.class);

                                                            startActivity(i);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }, 5000);

                                } else {

                                    Inquilino inq = new Inquilino(clave, etNombre.getText().toString(), null, etEmail.getText().toString().toLowerCase(), null, null);
                                    mDatabaseRef.child(clave).setValue(inq);

                                    Intent i = new Intent(Register.this, Login.class);

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

}
