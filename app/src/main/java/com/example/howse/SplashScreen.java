package com.example.howse;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
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

public class SplashScreen extends AppCompatActivity {


    TextView tvLogo;
    TextView tvDescInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);

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
            getSupportActionBar().hide();
        }

        tvDescInicio=findViewById(R.id.tvDescricionInicio);

        String desIni="Organize. Make it simple!";
        SpannableString ss= new SpannableString(desIni);

        ForegroundColorSpan fcsOrange= new ForegroundColorSpan(Color.rgb(242,169,34));


        ss.setSpan(fcsOrange,18,24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
                startActivity(i);
                finish();
            }
        },5000);
    }

}
