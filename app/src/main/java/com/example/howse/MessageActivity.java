package com.example.howse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.howse.adapter.MessageAdapter;
import com.example.howse.javabean.Chat;
import com.example.howse.javabean.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Intent intent;

    ImageButton btn_send;
    EditText etSend;

    MessageAdapter messageAdapter;
    List<Chat> mChat;

    RecyclerView recyclerView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        profile_image=findViewById(R.id.FotoPersona );
        username=findViewById(R.id.username);
        btn_send=findViewById(R.id.btn_send);
        etSend=findViewById(R.id.text_send);

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        intent=getIntent();
        final String usrid= intent.getStringExtra("userid");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Usuarios").child(usrid);

        System.out.println(firebaseUser.getEmail());
        System.out.println(firebaseUser.getUid());
        System.out.println("EStaaaaaaa en el oncreaaaaaaate de los mensajeeeeeees");



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg= etSend.getText().toString();
                if (!msg.trim().equals("")){
                    sendMessage(firebaseUser.getUid(),usrid,msg);
                }else{
                    Toast.makeText(MessageActivity.this,"No puedes enviar un mensaje vacio",Toast.LENGTH_SHORT).show();

                }

                etSend.setText("");
            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Usuario usuario= dataSnapshot.getValue(Usuario.class);

                username.setText(usuario.getNombreUsuario());
                if(usuario.getFotoUsuario()==null){
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Glide.with(MessageActivity.this).load(usuario.getFotoUsuario()).into(profile_image);
                }


                readMessages(firebaseUser.getUid(), usrid, usuario.getFotoUsuario());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void sendMessage(String  sender,  String reciver, String message){

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap= new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("reciver", reciver);
        hashMap.put("message", message);


        reference.child("Chats").push().setValue(hashMap);


    }

    private void readMessages(final String myid, final String userId, final String imageurl){

        mChat= new ArrayList<>();

        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    Chat chat= snapshot.getValue(Chat.class);
                    if(chat.getReciver().equals(myid) && chat.getsender().equals(userId) ||
                    chat.getReciver().equals(userId) && chat.getsender().equals(myid)){
                        mChat.add(chat);
                    }
                    messageAdapter= new MessageAdapter(MessageActivity.this,mChat,imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
