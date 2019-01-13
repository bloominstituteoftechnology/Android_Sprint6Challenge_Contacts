package com.meterstoinches.earthdefensesystem.android_sprint6challenge_contacts;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.VERTICAL;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Activity activity;

    private ContactListAdapter listAdapter;
    private ArrayList<Contact> contacts;

    private RecyclerView listView;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts = new ArrayList<>();

        listView = findViewById(R.id.contact_recycler_view);
        listView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(context);
        listView.setLayoutManager(layoutManager);

        listAdapter = new ContactListAdapter(contacts, activity);
        listView.setAdapter(listAdapter);

        ContactsApiDao.getAllContacts(new ContactsApiDao.ObjectCallback<ArrayList<Contact>>() {
            @Override
            public void returnObjects(ArrayList<Contact> result) {
                for (final Contact i : result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            contacts.add(i);
                            listAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}