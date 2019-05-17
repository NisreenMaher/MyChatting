package com.example.acer.mychating;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {
private Toolbar mToolbar;
private EditText status;
private Button saveChanges;
 private DatabaseReference changestatusRefernces;
 private FirebaseAuth mAuth;
 private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        loading = new ProgressDialog(StatusActivity.this);

        mToolbar = (Toolbar)findViewById(R.id.status_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Change Status");
        // ashan zer al (back) back to start page ( see mainfest) we select Setting as parent
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        status = (EditText)findViewById(R.id.editText);
        String old_status = getIntent().getExtras().get("user_status").toString();
        status.setText(old_status);
        saveChanges = (Button)findViewById(R.id.save_statu_change_button);
        mAuth = FirebaseAuth.getInstance();
        String userId=mAuth.getCurrentUser().getUid().toString();
        changestatusRefernces = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newStatus = status.getText().toString();
                ChangrProfileStatus(newStatus);
            }
        });
    }

    private void ChangrProfileStatus(String newStatus) {
        if(TextUtils.isEmpty(newStatus)){
            Toast.makeText(StatusActivity.this,"اكتب الحالة يا أعمى",Toast.LENGTH_LONG).show();

        }
        else{
            loading.setTitle("Change status");
            loading.setMessage("please Wait,While we are update your status ....");
            loading.show();

            changestatusRefernces.child("user_status").setValue(newStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
               if(task.isSuccessful()) {
                   loading.dismiss();
                   Intent i = new Intent(StatusActivity.this,Setting.class);
                   startActivity(i);
                   Toast.makeText(StatusActivity.this, "Update Status Succefully", Toast.LENGTH_LONG).show();
               }
               else
                   Toast.makeText(StatusActivity.this,"Update Status Failed",Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
