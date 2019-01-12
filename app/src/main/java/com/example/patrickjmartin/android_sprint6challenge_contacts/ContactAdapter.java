package com.example.patrickjmartin.android_sprint6challenge_contacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import java.util.concurrent.atomic.AtomicReference;

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
    private Activity activity;
    private Context context;
    final AtomicBoolean isCancelled = new AtomicBoolean(false);
    private ContactImageCache imageCache = ContactImageCache.getINSTANCE();

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
        Bitmap fromCache = (Bitmap) imageCache.getObject(contacts.get(i).getCacheKey(true));

        if (fromCache != null) {
            viewHolder.contactImage.setImageBitmap(fromCache);
        } else {

            isCancelled.set(false);
            new Thread(() -> {
                final Bitmap bitmap = NetworkAdapter.httpImageRequest(contacts.get(i),
                        true, imageCache, isCancelled);
                viewHolder.contactImage.setImageBitmap(bitmap);
            }).start();


        }

        viewHolder.constraintLayout.setOnClickListener(v -> {
            Intent detailsIntent = new Intent(activity, ContactDetails.class);
            detailsIntent.putExtra("contact", contacts.get(i));
            activity.startActivity(detailsIntent);
        });
    }



    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        //Add to catch
        int number = holder.getAdapterPosition();

        if (holder.contactImage != null && number >= 0) {
            Contact cachedContact = contacts.get(holder.getAdapterPosition());
            Bitmap bitmap = ((BitmapDrawable)holder.contactImage.getDrawable()).getBitmap();
            imageCache.setObject(cachedContact.getCacheKey(true), bitmap);
        }

        isCancelled.set(true);



    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
