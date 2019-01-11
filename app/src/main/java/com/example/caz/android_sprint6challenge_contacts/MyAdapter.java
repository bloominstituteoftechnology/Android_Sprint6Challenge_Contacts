package com.example.caz.android_sprint6challenge_contacts;

import java.util.List;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<ListItem> listItems;
    private Context context;


    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPersonName;


        public ViewHolder(View v) {
            super(v);

            tvPersonName = itemView.findViewById(R.id.firstLine);
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.row_layout, null);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ListItem listItemBind = listItems.get(position);
        holder.tvPersonName.setText(listItemBind.getPersonName());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}

//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
//    List<ListItem> listItems;
//    private Context context;
//
//
//    public MyAdapter(List<ListItem> listItems, Context context) {
//        this.listItems = listItems;
//        this.context = context;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView tvPersonName;
//
//
//        public ViewHolder(View v) {
//            super(v);
//
//            tvPersonName = itemView.findViewById(R.id.firstLine);
//        }
//    }
//
//    @Override
//    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View v = inflater.inflate(R.layout.row_layout, null);
//        ViewHolder vh = new ViewHolder(v);
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//
//        ListItem listItemBind = listItems.get(position);
//        holder.tvPersonName.setText(listItemBind.getPersonName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return listItems.size();
//    }
//
//}