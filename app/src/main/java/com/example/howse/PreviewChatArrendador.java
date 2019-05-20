package com.example.howse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.howse.Fragments.ChatsFragment;
import com.example.howse.Fragments.UsersFragment;
import com.example.howse.javabean.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PreviewChatArrendador extends MenuAbstractActivityArrendador {






    CircleImageView imgperfil;
    TextView tvnombre;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private String emailPersona;
    private final Usuario[] usr = new Usuario[1];




    @Override
    public int cargarLayout() {
        return R.layout.activity_preview_del_chat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_preview_del_chat);
        setActActual( CHATARR );
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");




        imgperfil = findViewById(R.id.profileImage);
        tvnombre= findViewById(R.id.username);


        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference( "Usuarios" );



        if(firebaseUser==null){

            System.out.println("es nuloooooooooooooooo");

        }else{
            emailPersona=firebaseUser.getEmail();
            System.out.println(firebaseUser.getEmail());

        }



        TabLayout tabLayout= findViewById(R.id.tab_layout);
        ViewPager viewPager=findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter= new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragments(new ChatsFragment(),"Chats");
        viewPagerAdapter.addFragments(new UsersFragment(),"Usuarios");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);


        cargarDatos();
    }

    private void cargarDatos() {

        Query qq = reference.orderByChild("emailUsuario").equalTo(emailPersona);

        qq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    usr[0] = dataSnapshot1.getValue(Usuario.class);
                }

                if (emailPersona.equals( usr[0].getEmailUsuario() )){

                    tvnombre.setText(usr[0].getNombreUsuario());

                    if(usr[0].getFotoUsuario()==null){
                        imgperfil.setImageResource(R.drawable.ic_user);

                    }else{
                        Glide.with(PreviewChatArrendador.this)
                                .load(usr[0].getFotoUsuario())
                                .into(imgperfil);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( PreviewChatArrendador.this, "Algo salio Mal ai", Toast.LENGTH_SHORT ).show();

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
                startActivity(new Intent(PreviewChatArrendador.this, ActivityCaseroInquilino.class));
                finish();
                return true;
        }
        return false;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm){

            super(fm);
            this.fragments= new ArrayList<>();
            this.titles= new ArrayList<>();

        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragments(Fragment fragment, String title){

            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
