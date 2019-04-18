package com.example.howse;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class ActivityCaseroInquilino extends AppCompatActivity {

    TextView tvDescInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casero_inquilino);
        getSupportActionBar().hide();



        tvDescInicio=findViewById(R.id.tvDescricionInicio);

        String desIni="Good start. Best stay!";
        SpannableString ss= new SpannableString(desIni);

        ForegroundColorSpan fcsOrange= new ForegroundColorSpan(Color.rgb(242,169,34));


        ss.setSpan(fcsOrange,12,22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDescInicio.setText(ss);

    }



}
