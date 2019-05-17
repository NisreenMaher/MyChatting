package com.example.acer.mychating;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.Thread.sleep;

public class Sign extends AppCompatActivity {
private Toolbar mtoolbar;
private FirebaseAuth mAuth;
// ref to root database (we need to store user data ( status,image,name...) to thie root(refrencess)
private DatabaseReference storeUserDataRefernce;

private EditText user,pass,signup_email;
private Button signup;
private ProgressDialog loading;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        mAuth = FirebaseAuth.getInstance();
        loading = new ProgressDialog(Sign.this);
        mtoolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.signup_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Sign Up");
        // ashan zer al (back) back to start page ( see mainfest) we select start page as parent
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = (EditText)findViewById(R.id.signup_name);
        pass=(EditText)findViewById(R.id.signup_password);
        signup_email = (EditText)findViewById(R.id.signup_email);
        signup = (Button)findViewById(R.id.creat_account);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = user.getText().toString();
                String password = pass.getText().toString();
                String email = signup_email.getText().toString();
                RegisterAccount(name,email,password);


            }
        });

    }

    private void RegisterAccount(final String name, String email, String password)  {
        //check if field is empty
        if(TextUtils.isEmpty(name))
            Toast.makeText(Sign.this,"please enter your name",Toast.LENGTH_LONG).show();
         if(TextUtils.isEmpty(email))
            Toast.makeText(Sign.this,"please enter your email",Toast.LENGTH_LONG).show();
        if(TextUtils.isEmpty(password))
            Toast.makeText(Sign.this,"please enter your password",Toast.LENGTH_LONG).show();
        else {

            loading.setTitle("Creat New Account");
            loading.setMessage("Please Wait,while we are creating a new account for you :) ");
            loading.show();


            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     Log.d("hhhhh", "onComplete: n");
                     String currentUserID = mAuth.getCurrentUser().getUid();
                     storeUserDataRefernce= FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
                     storeUserDataRefernce.child("user_name").setValue(name);
                     storeUserDataRefernce.child("user_status").setValue("Hey there,Iam using myChAt , developed by Nisreen Maher");
                     storeUserDataRefernce.child("user_image").setValue("defult_profile");
                     storeUserDataRefernce.child("user_thump_image").setValue("defult_image")
                     //image : original image , and thump : image after crop the original
                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful()){
                                 Intent i = new Intent(Sign.this,MainActivity.class);
                                 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                 startActivity(i);
                                 finish();
                             } }}); }
                 else
                     Toast.makeText(Sign.this,"Error Occured , Try Again",Toast.LENGTH_SHORT).show(); }});
            loading.dismiss();

        }
    }
}
