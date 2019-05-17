package com.example.acer.mychating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartPage extends AppCompatActivity {
private Button login,sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        login = (Button)findViewById(R.id.login);
        sign=(Button)findViewById(R.id.sign);
login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(StartPage.this,Login.class);
        startActivity(i);
    }
});
sign.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(StartPage.this,Sign.class);
        startActivity(i);
    }
});
    }
}
