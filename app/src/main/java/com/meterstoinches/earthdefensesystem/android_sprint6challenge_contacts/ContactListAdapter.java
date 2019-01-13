package com.meterstoinches.earthdefensesystem.android_sprint6challenge_contacts;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder>{
    Bitmap bitmap = null;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView contactName;
        ImageView contactImg;
        ViewGroup parentView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            contactName = itemView.findViewById(R.id.contact_name);
            contactImg = itemView.findViewById(R.id.contact_image);
            parentView = itemView.findViewById(R.id.contact_parent_layout);

        }
    }
    private final ArrayList<Contact> dataList;
    private Context context;
    private Activity activity;
    final AtomicBoolean cancelRequest = new AtomicBoolean(false);

    ContactListAdapter(ArrayList<Contact> dataList, Activity activity){
        this.dataList = dataList;
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       final Contact data= dataList.get(position);


       holder.contactName.setText(data.getFirstName()+ " " + data.getLastName());

       new Thread(new Runnable() {
           @Override
           public void run() {
               bitmap = ContactsApiDao.getImage(data.getImgUrl(), cancelRequest);
           }
       }).start();
       holder.contactImg.setImageBitmap(bitmap);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.contact_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
