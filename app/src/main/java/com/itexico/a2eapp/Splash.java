package com.itexico.a2eapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by darkgeat on 1/5/16.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(Splash.this,Url_Setup.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}
