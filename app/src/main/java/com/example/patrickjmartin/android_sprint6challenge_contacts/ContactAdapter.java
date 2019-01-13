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
import android.widget.TextView;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout constraintLayout;
        private ImageView contactImage;
        private TextView contactDetails;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.parent_view_contacts);
            contactImage = itemView.findViewById(R.id.contact_image);
            contactDetails = itemView.findViewById(R.id.contact_textview);

        }

    }

    private final ArrayList<Contact> contacts;
    private Activity activity;
    private Context context;
    final AtomicBoolean isCancelled = new AtomicBoolean(false);
    private ContactImageCache imageCache = ContactImageCache.getINSTANCE();
    private Bitmap bitmap;


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
        final Contact c = contacts.get(i);
        String key = c.getCacheKey();
        String url = c.getPictureThumb();
        String contactTextViewContents = c.getFullName();
        viewHolder.contactDetails.setText(contactTextViewContents);


        Bitmap fromCache = (Bitmap) imageCache.getObject(key);

        if (fromCache != null) {
            viewHolder.contactImage.setImageBitmap(fromCache);
        } else {
            isCancelled.set(false);
            new Thread(() -> bitmap = NetworkAdapter.httpImageRequest(url,
                    key, isCancelled)).start();


            viewHolder.contactImage.setImageBitmap(bitmap);


        }

        viewHolder.constraintLayout.setOnClickListener(v -> {
            Intent detailsIntent = new Intent(activity, ContactDetails.class);
            detailsIntent.putExtra("contact", c);
            activity.startActivity(detailsIntent);
        });
    }



    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        isCancelled.set(true);
        bitmap = null;
        super.onViewDetachedFromWindow(holder);


    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
