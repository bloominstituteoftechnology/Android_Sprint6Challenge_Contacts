package com.example.lambda_school_loaner_47.contactsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView fullName;
    TextView email;
    TextView txtphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Contacts contacts = getIntent().getParcelableExtra(ContactsAdapter.CONTACT_ADAPTER);

        fullName = findViewById(R.id.tvFullName);
        email    = findViewById(R.id.tvEmail);
        txtphone = findViewById(R.id.tvPhone);

        fullName.setText(contacts.getFullName());
        email.setText(contacts.getEmail());
        txtphone.setText(contacts.getPhone());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
