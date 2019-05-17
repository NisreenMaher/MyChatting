package com.example.acer.mychating;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUserActivity extends AppCompatActivity {

        private android.support.v7.widget.Toolbar mToolbar;
        private RecyclerView recyclerView;
        private DatabaseReference allUserDatabaseRefernces;
        private List<AllUsers> list;
        private LinearLayoutManager layoutManager;
        private DatabaseReference mUsersDatabase;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_all_user);
            mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.all_user_bar_layout);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("All User");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            recyclerView = (RecyclerView) findViewById(R.id.all_user_list);

            mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

            layoutManager = new LinearLayoutManager(this);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);

        }
        @Override
        protected void onStart() {
            super.onStart();

            FirebaseRecyclerAdapter<AllUsers, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AllUsers, UsersViewHolder>(

                    AllUsers.class,
                    R.layout.all_user_diplay_layout,
                    UsersViewHolder.class,
                    mUsersDatabase

            ) {
                @Override
                protected void populateViewHolder(UsersViewHolder usersViewHolder, AllUsers users, int position) {

                    usersViewHolder.setDisplayName(users.getUser_name());
                    usersViewHolder.setUserStatus(users.getUser_status());
                    usersViewHolder.setUserImage(users.getThumb_image(), getApplicationContext());

                    final String user_id = getRef(position).getKey();

                    usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            /// when user click to item
                            //// Action Here Nissreen

                        }
                    });

                }
            };


            recyclerView.setAdapter(firebaseRecyclerAdapter);

        }


        public static class UsersViewHolder extends RecyclerView.ViewHolder {

            View mView;

            public UsersViewHolder(View itemView) {
                super(itemView);

                mView = itemView;

            }

            public void setDisplayName(String name){

                TextView userNameView = (TextView) mView.findViewById(R.id.ALLUser_username);
                userNameView.setText(name);

            }

            public void setUserStatus(String status){

                TextView userStatusView = (TextView) mView.findViewById(R.id.ALLUser_userstatus);
                userStatusView.setText(status);


            }

            public void setUserImage(String thumb_image, Context ctx){

                CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.ALLUser_image);

                Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.user).into(userImageView);

            }


        }

    }

/*
        recyclerView = (RecyclerView) findViewById(R.id.all_user_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(AllUserActivity.this));

        allUserDatabaseRefernces = FirebaseDatabase.getInstance().getReference().child("Users");
        options =
                new FirebaseRecyclerOptions.Builder<AllUsers>()
                        .setQuery(allUserDatabaseRefernces, AllUsers.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<AllUsers, AllUserViewHolder>(options) {
            @NonNull
            @Override
            public AllUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_user_diplay_layout ,parent, false);
                AllUserViewHolder r= new AllUserViewHolder(v);
                return r;
            }
            @Override
            protected void onBindViewHolder(@NonNull AllUserViewHolder holder, int position, @NonNull AllUsers model) {
                holder.setUser_name(model.getUser_name());
                holder.setUser_status(model.getUser_status());
                holder.setUser_thump_image(getApplicationContext(),model.getUser_thump_image());
            }

        };
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapter);

        Toast.makeText(AllUserActivity.this,allUserDatabaseRefernces.child("user_status").toString(),Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onStart() {
        super.onStart();
           adapter.startListening();
    }


    public class AllUserViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public AllUserViewHolder(View itemView) {
            super(itemView);
        }
        public void setUser_name(String user_name) {
            TextView name = (TextView)mView.findViewById(R.id.ALLUser_username);
            name.setText(user_name);
        }
        public void setUser_status(String user_status) {
            TextView status = (TextView)mView.findViewById(R.id.ALLUser_userstatus);
            status.setText(user_status);

        }
        public void  setUser_thump_image(Context t ,String user_thump_image) {
            CircleImageView thump_image = (CircleImageView)mView.findViewById(R.id.ALLUser_image);
            Picasso.get().load(user_thump_image).into(thump_image);
        }
*/