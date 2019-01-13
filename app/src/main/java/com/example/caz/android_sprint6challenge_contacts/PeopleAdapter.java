package com.example.caz.android_sprint6challenge_contacts;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PeopleAdapter extends RecyclerView.Adapter{
    List<Person> personList;
    private Context context;

    private View view;


    public PeopleAdapter(List<Person> personList, Context context) {

        this.personList = personList;
        this.context = context;

    }
    public class PersonViewHolder extends RecyclerView.ViewHolder {
        public TextView personText;
        public ImageView ivPersonThumb;

        public PersonViewHolder(View v) {
            super(v);

            personText = itemView.findViewById(R.id.tvPersonText);
            ivPersonThumb = itemView.findViewById(R.id.ivPersonThumb);
        }
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Person person = this.personList.get(i);
        String text = person.getTitle() + " " + person.getFirst() + " " + person.getLast();

        ((PersonViewHolder)viewHolder).personText.setText(text);
    }


    @Override
    public int getItemCount() {
        return this.personList.size();
    }
}

//public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PersonViewHolder> {
//    List<ListItem> listItems;
//    private Context context;
//
//
//    public PeopleAdapter(List<ListItem> listItems, Context context) {
//        this.listItems = listItems;
//        this.context = context;
//    }
//
//    public class PersonViewHolder extends RecyclerView.PersonViewHolder {
//        public TextView personText;
//
//
//        public PersonViewHolder(View v) {
//            super(v);
//
//            personText = itemView.findViewById(R.id.firstLine);
//        }
//    }
//
//    @Override
//    public PeopleAdapter.PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View v = inflater.inflate(R.layout.row_layout, null);
//        PersonViewHolder vh = new PersonViewHolder(v);
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(PersonViewHolder holder, final int position) {
//
//        ListItem listItemBind = listItems.get(position);
//        holder.personText.setText(listItemBind.getPersonName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return listItems.size();
//    }
//
//}