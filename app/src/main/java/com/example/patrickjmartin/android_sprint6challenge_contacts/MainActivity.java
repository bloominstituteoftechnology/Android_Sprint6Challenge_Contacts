package com.example.patrickjmartin.android_sprint6challenge_contacts;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ContactAdapter adapter;
    private ArrayList<Contact> contacts;
    private Activity activity;
    private Context context;
    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        context = this;
        contacts = new ArrayList<>();

        recyclerView = findViewById(R.id.contact_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new ContactAdapter(activity, contacts);
        recyclerView.setAdapter(adapter);
        ContactDao.getAllUsers(object -> {
            object.stream().<Runnable>map(contact -> () -> {
                contacts.add(contact);
                adapter.notifyDataSetChanged();
            }).forEach(this::runOnUiThread);
        });





    }
}


/*
        ContactDao.getAllUsers(new ContactDao.ObjectCallback<ArrayList<Contact>>() {
            @Override
            public void returnContacts(ArrayList<Contact> object) {
                for (final Contact contact : object) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            contacts.add(contact);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
 */