package com.joshuahalvorson.android_sprint6challenge_contacts;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {
    private UsersListAdapter adapter;
    private List<User> users;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        activity = this;
        users = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.users_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new UsersListAdapter(activity, users);
        recyclerView.setAdapter(adapter);
        UsersDbDao.getAllUsers(new UsersDbDao.ObjectCallback<List<User>>() {
            @Override
            public void usersResult(List<User> result) {
                for(final User u : result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            users.add(u);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }

            }
        });
    }

}
