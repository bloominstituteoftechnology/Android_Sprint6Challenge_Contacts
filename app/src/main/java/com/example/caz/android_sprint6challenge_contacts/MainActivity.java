package com.example.caz.android_sprint6challenge_contacts;

import android.app.LauncherActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<LauncherActivity.ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        //just used to test recyclerview
        for (int i = 0; i < 5; i++) {
            ListItem listItem = new ListItem("heading" + (i + 1));
            listItems.add(listItem);
        }


        mAdapter = new MyAdapter(listItem, this);
        recyclerView.setAdapter(mAdapter);
    }

}


//import android.app.LauncherActivity;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;
//
//    private List<LauncherActivity.ListItem> listItems;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        recyclerView = findViewById(R.id.my_recycler_view);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//
//
//        //just used to test recyclerview
//        for (int i = 0; i < 5; i++) {
//            ListItem listItem = new ListItem("heading" + (i + 1));
//            listItems.add(listItem);
//        }
//
//
//
//
//        mAdapter = new MyAdapter(listItem, this);
//        recyclerView.setAdapter(mAdapter);
//    }
//}