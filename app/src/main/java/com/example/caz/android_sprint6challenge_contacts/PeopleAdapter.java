package com.example.caz.android_sprint6challenge_contacts;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        final Person person = this.personList.get(i);
        String text = person.getTitle() + " " + person.getFirst() + " " + person.getLast();

        ((PersonViewHolder)viewHolder).personText.setText(text);

        Bitmap image = ImageCache.loadImageFromStorage(person, context);

        if(image == null){
            // if no image, request
            new GetImageFromUrl(((PersonViewHolder) viewHolder).ivPersonThumb, person, context).execute(person.getThumbnail());
        }else{
            ((PersonViewHolder)viewHolder).ivPersonThumb.setImageBitmap(image); // with image
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PersonDetailActivity.class);
                i.putExtra("selectedPerson", person);
                context.startActivity(i);
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return this.personList.size();
    }
}
