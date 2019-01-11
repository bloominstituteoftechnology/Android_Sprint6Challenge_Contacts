package com.example.lambda_school_loaner_47.contactsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    RecyclerView view;
    LinearLayoutManager manager;
    ArrayList<Contacts> list;
    ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        view = findViewById(R.id.recycleView);
        list = new ArrayList<>();
        manager = new LinearLayoutManager(this);

        findViewById(R.id.btnShowContacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        list = ContactsDao.getContacts();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new ContactsAdapter(list);
                                view.setHasFixedSize(true);
                                view.setLayoutManager(manager);
                                view.setAdapter(adapter);
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
