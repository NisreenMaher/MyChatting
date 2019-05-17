package com.example.acer.mychating;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mtoolbar;
    private EditText email_login,pass_login;
    private Button login_signin;
    private FirebaseAuth mAuth;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //------
        mtoolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Sign In");
        // ashan zer al (back) back to start page ( see mainfest) we select start page as parent
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //-----
        mAuth = FirebaseAuth.getInstance();
        loading = new ProgressDialog(Login.this);
        //----
        email_login=(EditText)findViewById(R.id.login_email);
        pass_login=(EditText)findViewById(R.id.login_pass);
        login_signin=(Button)findViewById(R.id.login_signin);
        login_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_login.getText().toString();
                String pass = pass_login.getText().toString();
                LoginUser(email,pass);

            }
        });
    }

    private void LoginUser(String email, String pass) {
        if(TextUtils.isEmpty(email))
            Toast.makeText(Login.this,"please enter your email",Toast.LENGTH_SHORT).show();
        if(TextUtils.isEmpty(pass))
            Toast.makeText(Login.this,"please enter your password",Toast.LENGTH_SHORT).show();
        else
        {
            loading.setTitle("Login");
            loading.setMessage("Please Wait,while we are Loging to your account :) ");
            loading.show();
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent i = new Intent(Login.this,MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();

                    }
                    else
                        Toast.makeText(Login.this,"Wrong login : please cheack your email and password",Toast.LENGTH_SHORT).show();

                    loading.dismiss();
                }

            });



        }


    }
}



