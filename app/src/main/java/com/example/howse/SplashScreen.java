package com.example.howse;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.howse.javabean.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {


    TextView tvLogo;
    TextView tvDescInicio;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private String emailPersona="vaya vaya vaya ";
    private final Usuario[] usr = new Usuario[1];

    Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            reference = FirebaseDatabase.getInstance().getReference( "Usuarios" );

            emailPersona=firebaseUser.getEmail();
            usuario=cargarDatos();
            System.out.println(emailPersona);
            System.out.println(firebaseUser.getDisplayName()+" esto es un nombre como dios manda ");
        }

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            /*Esconder la AppBar*/

        }

        tvDescInicio=findViewById(R.id.tvDescricionInicio);

        String desIni="Make the living easy!";
        SpannableString ss= new SpannableString(desIni);

        ForegroundColorSpan fcsOrange= new ForegroundColorSpan(Color.rgb(242,169,34));


        ss.setSpan(fcsOrange,16,21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDescInicio.setText(ss);


        tvLogo=findViewById(R.id.tvLogo);

        String logo="Howse";
        SpannableString ss1= new SpannableString(logo);



        ss1.setSpan(fcsOrange,1,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLogo.setText(ss1);




        Animation animTitulo = AnimationUtils.loadAnimation(this, R.anim.fadein);
        tvLogo.setAnimation(animTitulo);

        siguienteActivity();
           }
    private void siguienteActivity() {
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i= new Intent(SplashScreen.this, ActivityCaseroInquilino.class);
                    if( firebaseUser!=null && usuario!=null){
                        i.putExtra("tipoUs",usuario.getTipoUs());

                }
                startActivity(i);
                finish();
            }
        },5000);
    }

    private Usuario cargarDatos() {

        Query qq = reference.orderByChild("emailUsuario").equalTo(emailPersona);

        qq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    usr[0] = dataSnapshot1.getValue(Usuario.class);
                    System.out.println(usr[0].getEmailUsuario()+" efskejfegfbmsbesjvkesjbf");


                }
                if (usr[0]!=null) {
                    if (emailPersona.equals( usr[0].getEmailUsuario() )) {
                        usuario = usr[0];
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return usuario;
    }

}
