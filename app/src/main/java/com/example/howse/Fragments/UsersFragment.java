package com.example.howse.Fragments;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.howse.Perfil;
import com.example.howse.R;
import com.example.howse.adapter.UserAdapter;
import com.example.howse.javabean.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<Usuario> mUsers;



     FirebaseUser firebaseUser;
    private DatabaseReference reference;

    Usuario  usFin;
    private final Usuario[] usr = new Usuario[1];


    String codCasa;
    String emailPersona;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_users,container,false);


        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers= new ArrayList<>();

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
         emailPersona=firebaseUser.getEmail();

        reference= FirebaseDatabase.getInstance().getReference( "Usuarios" );


        cargarCodCasa();

        readUsers();

        return view;
    }

    private void readUsers(){






        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Usuario usuario;
                    try {
                         usuario = snapshot.getValue(Usuario.class);

                    }catch (DatabaseException de){

                     break;
                    }
                    assert usuario != null;
                    assert firebaseUser != null;


                    if (usuario.getEmailUsuario().equals(firebaseUser.getEmail())){
                         usFin=usuario;





                    }


                    if(!(usuario.getEmailUsuario().equals(firebaseUser.getEmail()))  && usuario.getCodCasa().equals(codCasa)){


                        mUsers.add(usuario);
                    }

                }

                userAdapter= new UserAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
                            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void cargarCodCasa(){

        Query qq = reference.orderByChild("emailUsuario").equalTo(emailPersona);

        qq.addListenerForSingleValueEvent(new ValueEventListener() {
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
}


