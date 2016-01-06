package com.itexico.a2eapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by darkgeat on 1/5/16.
 */
public class Url_Setup extends AppCompatActivity {

    private EditText videoAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.url_setup);

        videoAddress = (EditText) findViewById(R.id.url);

    }

    public void watch(View view) {
        Intent intent = new Intent(Url_Setup.this,MainActivity.class);
        String url = videoAddress.getText().toString();
        if (url.length() > 0){
            intent.putExtra("url",url);
            startActivity(intent);
        }
    }
}
