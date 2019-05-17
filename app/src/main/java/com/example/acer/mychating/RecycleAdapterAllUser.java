package com.example.acer.mychating;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleAdapterAllUser extends RecyclerView.Adapter<RecycleAdapterAllUser.MyHolder>
{
    List<AllUsers> list;
    Context context;
    public  RecycleAdapterAllUser(List<AllUsers> list,Context context){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_user_diplay_layout ,parent, false);
       MyHolder r= new MyHolder(v);
        return r;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        AllUsers mylist = list.get(position);
        holder.user_name.setText(mylist.getUser_name());
        holder.user_status.setText(mylist.getUser_status());
        Picasso.get().load(mylist.user_image).into(holder.user_image);


    }

    @Override
    public int getItemCount() {
        int arr=0;
        try {
            if(list.size()==0)
                arr=0;
            else
                arr=list.size();
        }
        catch (Exception e){

        }
        return arr;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView user_name,user_status;
        CircleImageView user_image;

        public MyHolder(View itemView) {
            super(itemView);
            user_name=(TextView)itemView.findViewById(R.id.ALLUser_username);
            user_status=(TextView)itemView.findViewById(R.id.ALLUser_userstatus);
            user_image=(CircleImageView)itemView.findViewById(R.id.ALLUser_image);


        }

    }
}
