package com.example.patrickjmartin.android_sprint6challenge_contacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout constraintLayout;
        private ImageView contactImage;
        private TextView contactDetails;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.parent_view_contacts);
            contactImage = itemView.findViewById(R.id.contact_image);
            contactDetails = itemView.findViewById(R.id.contact_textview);
            progressBar = itemView.findViewById(R.id.progressBar);

        }

    }

    private final ArrayList<Contact> contacts;
    private Bitmap image = null;
    private Activity activity;
    private Context context;
    final AtomicBoolean isCancelled = new AtomicBoolean(false);

    ContactAdapter(Activity activity, ArrayList<Contact> contacts) {
        this.activity = activity;
        this.contacts = contacts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout. contact_adapter_layout,
                viewGroup,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        String contactTextViewContents = contacts.get(i).getFullName();
        viewHolder.contactDetails.setText(contactTextViewContents);

        //TODO Image will be handled via cache.

        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsIntent = new Intent(activity, ContactDetails.class);
                detailsIntent.putExtra("contact", contacts.get(i));
                activity.startActivity(detailsIntent);
            }
        });
    }

    

    @Override
    public int getItemCount() {
        return 0;
    }
}
