package com.example.acer.mychating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Thread thread = new Thread(){
            @Override
            public void run(){
                try
                {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent i = new Intent(Welcome.this,MainActivity.class);
                    startActivity(i);
                }
            }
        };
        thread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
    finish();
    }
}
