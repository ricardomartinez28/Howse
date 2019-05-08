package com.example.howse.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.howse.R;
import com.example.howse.adapter.UserAdapter;
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
import java.util.List;


public class ChatsFragment extends android.support.v4.app.Fragment {


    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<Usuario> mUsers;


    FirebaseUser fuser;
    DatabaseReference reference;


    private List<String> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        fuser= FirebaseAuth.getInstance().getCurrentUser();

        userList= new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    Chat chat= snapshot.getValue(Chat.class);

                    if(chat.getsender().equals(fuser.getUid())){
                        userList.add(chat.getReciver());
                        //System.out.println("Recibe: "+chat.getReciver()+" ++++++++++++Manda: "+chat.getsender());


                    }
                    if(chat.getReciver().equals(fuser.getUid())){
                        userList.add(chat.getsender());
                        //System.out.println("Recibe: "+chat.getReciver()+" ------Manda: "+chat.getsender());
                    }

                }



                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }


    private void readChats(){
        mUsers= new ArrayList<>();

        reference= FirebaseDatabase.getInstance().getReference("Usuarios");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUsers.clear();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    Usuario usuario= snapshot.getValue(Usuario.class);

                   // String nombre=usuario.getNombreUsuario();

                    /*
                    if(usuario==null){
                        System.out.println("Los Usuariosssss Son Nulos TOyyyyyyy");
                    }else{
                        System.out.println("El usuario se llama  "+nombre+" tratale como se merece");
                    }
                    */

                    for (String id : userList){
                        //System.out.println("-------------UserList-------------"+id);
                        //System.out.println(nombre+"----------------CurrentUser-----------"+usuario.getUid());


                        if(usuario.getUid().equals(id)){
                            if(mUsers.size() != 0 ){
                                for(Usuario usuario1: mUsers){
                                    if(!(usuario.getUid().equals(usuario1.getUid()))){
                                       mUsers.add(usuario);
                                    }
                                }
                            }else {

                                mUsers.add(usuario);
                            }
                        }
                    }
                }
                userAdapter= new UserAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);

                /*
                if(mUsers.size()==0){
                    System.out.println("ESTA Vááááááciaaaaaaaaaaaaaaaaaaaaaaaaa");
                }else{
                    System.out.println("ESTE No ES EL Por el que nO Sale SiGue InTenTando");

                }
                */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
