package com.example.patrickjmartin.android_sprint6challenge_contacts;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class ContactDetails extends AppCompatActivity {

    private ImageView contactImageView;
    private TextView contactDetails;
    private Bitmap bitmap;
    final AtomicBoolean isCancelled = new AtomicBoolean(false);
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_deails);

        contactImageView = findViewById(R.id.contact_large_image);
        contactDetails = findViewById(R.id.contact_details_textView);

        intent = getIntent();

        final Contact selectedContact = (Contact) intent.getSerializableExtra("contact");

        new Thread(() -> {
            bitmap = NetworkAdapter.httpImageRequest(selectedContact.getPictureLarge(),
                    selectedContact.getCacheKey(), isCancelled);
            runOnUiThread(() -> contactImageView.setImageBitmap(bitmap));

        }).start();

        String contactTextViewContents = "Name: " + selectedContact.getFullName() +
                "\nPhone Number: " + selectedContact.getPhoneNumber();

        contactDetails.setText(contactTextViewContents);
    }
}
