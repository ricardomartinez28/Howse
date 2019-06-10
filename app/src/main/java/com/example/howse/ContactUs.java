package com.example.howse;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactUs extends AppCompatActivity {

    private EditText subject;
    private EditText message;
    private Button enviar;

    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_contact_us );

        subject= (EditText) findViewById( R.id.etSubject );
        message= (EditText) findViewById( R.id.etMensaje );

        enviar= (Button) findViewById( R.id.btnEnviarMensaje );
        email = "danisom1b@gmail.com";

        enviar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( Intent.ACTION_VIEW, Uri.parse( "mailto:" + email ) );
                i.putExtra(Intent.EXTRA_SUBJECT,subject.getText().toString());
                i.putExtra(Intent.EXTRA_TEXT,message.getText().toString());
                startActivity( i );
            }
        } );
    }
}
