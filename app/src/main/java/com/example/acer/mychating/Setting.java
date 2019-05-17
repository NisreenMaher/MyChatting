package com.example.acer.mychating;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLEngineResult;
import javax.xml.transform.Result;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class Setting extends AppCompatActivity {
private CircleImageView settingDisplayImage;
private TextView settingDisplayusername,settingDisplayuserstatus;
private Button settingchangeimage,settingchangestatus;
private DatabaseReference getUserDataRefernces;
private FirebaseAuth mAuth;
private final static int GALLARY_PICK = 1;
private StorageReference storageimageprofile;
private  StorageReference thumbImageRef;
private Bitmap thumb_bitmap=null;
private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        getUserDataRefernces = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        //ref to storge and creat a folder profile image to store image for every user
        storageimageprofile = FirebaseStorage.getInstance().getReference().child("Profile_Images");
        thumbImageRef=FirebaseStorage.getInstance().getReference().child("thumb_Images");
        progressDialog = new ProgressDialog(Setting.this);
        settingDisplayImage = (CircleImageView)findViewById(R.id.setting_profile_image);
        settingDisplayusername=(TextView)findViewById(R.id.setting_username);
        settingDisplayuserstatus=(TextView)findViewById(R.id.setting_userstatus);
        settingchangeimage = (Button)findViewById(R.id.setting_changeimage_button);
        settingchangestatus=(Button)findViewById(R.id.setting_changestatus_buttons);
        getUserDataRefernces.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // datasnpashot contain object from users
        String name = dataSnapshot.child("user_name").getValue().toString();
        String userimage = dataSnapshot.child("user_image").getValue().toString();
        String userStatus = dataSnapshot.child("user_status").getValue().toString();
        String user_thump_image = dataSnapshot.child("user_thump_image").getValue().toString();
        settingDisplayusername.setText(name);
        settingDisplayuserstatus.setText(userStatus);
        if(!(userimage.equals("defult_profile"))) {
            Picasso.get().load(userimage).into(settingDisplayImage);
        }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
        settingchangeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Gallery
                Intent i =new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,GALLARY_PICK);
            }
        });
settingchangestatus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String OldStatus = settingDisplayuserstatus.getText().toString();
        Intent i = new Intent(Setting.this, StatusActivity.class);
        i.putExtra("user_status",OldStatus);
        startActivity(i);
    }
});
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Crop Image
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLARY_PICK && resultCode== RESULT_OK && data != null){
            Uri ImageUri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                // creat a crop image and put it uri
                progressDialog.setTitle("Loading Image");
                progressDialog.setMessage("plesa wait , While we Updateing your profile image");
                progressDialog.show();
                Uri resultUri = result.getUri();
                File thumb_filepath_uri = new File (resultUri.getPath());
                // convert the photo to bitmap , so we can compressed it
                try{
                    thumb_bitmap=new Compressor(this)
                            .setMaxHeight(200)
                            .setMaxWidth(200)
                            .setQuality(50)
                            .compressToBitmap(thumb_filepath_uri);

                }
                catch (IOException e){
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                final byte[] thumb_byte = byteArrayOutputStream.toByteArray();


                String imageName = mAuth.getCurrentUser().getUid().toString();



                //creat a name to the image stored in firebase , there is a several way : Random fn or
                //give the image namd = userId since the uesrId is unieq
                StorageReference filePath = storageimageprofile.child(imageName+".jpg");
                final StorageReference thumb_filepath = thumbImageRef.child(imageName+".jpg");

                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()) {


                            Toast.makeText(Setting.this, "Load image to Firebase Storge is done", Toast.LENGTH_LONG).show();


                            // Retrieve profile Image from Firebase Database
                           final String downloadUri = task.getResult().getDownloadUrl().toString();
                           UploadTask uploadTask = thumb_filepath.putBytes(thumb_byte);
                           uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                               @Override
                               public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {
                                   String thumb_download_uri = thumb_task.getResult().getDownloadUrl().toString();
                                   if(thumb_task.isSuccessful())
                                   {
                                       Map userUpateData = new HashMap();
                                       userUpateData.put("user_image",downloadUri);
                                       userUpateData.put("user_thump_image",thumb_download_uri);

                                       getUserDataRefernces.updateChildren(userUpateData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               Toast.makeText(Setting.this, "image update succefully", Toast.LENGTH_LONG).show();

                                           progressDialog.dismiss();
                                           }
                                       });
                                   }
                               }
                           });


                        }
                        else {
                            Toast.makeText(Setting.this, "Load image is failed", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
