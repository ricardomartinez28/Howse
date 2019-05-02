package com.example.howse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class PreviewDelChat extends MenuAbstractActivity {






    CircleImageView imgperfil;
    TextView tvnombre;

    FirebaseUser firebaseUser;
    DatabaseReference dbr;
    FirebaseAuth fa;



    @Override
    public int cargarLayout() {
        return R.layout.activity_preview_del_chat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_preview_del_chat);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");




        imgperfil = findViewById(R.id.profileImage);
        tvnombre= findViewById(R.id.username);


        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser==null){

            System.out.println("es nuloooooooooooooooo");

        }else{
            System.out.println(firebaseUser.getEmail());

        }
        dbr= FirebaseDatabase.getInstance().getReference("Usuarios").child(firebaseUser.getUid());


        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Usuario usuario= dataSnapshot.getValue(Usuario.class);
                tvnombre.setText(usuario.getNombreUsuario());


                if(usuario.getFotoUsuario()==null){
                    imgperfil.setImageResource(R.mipmap.ic_launcher);
                }else {
                    Glide.with(PreviewDelChat.this).load(usuario.getFotoUsuario()).into(imgperfil);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
                startActivity(new Intent(PreviewDelChat.this, Login.class));
                finish();
                return true;
        }
        return false;
    }
}
