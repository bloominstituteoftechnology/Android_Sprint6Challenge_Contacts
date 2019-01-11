package com.joshuahalvorson.android_sprint6challenge_contacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder>{
    private final List<User> users;
    Bitmap bitmap = null;
    final AtomicBoolean canceled = new AtomicBoolean(false);
    private Activity activity;
    static ImageCache cache = ImageCache.getInstance();

    UsersListAdapter(Activity activity, List<User> items) {
        this.activity = activity;
        users = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        cache.initializeCache();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(activity, UserDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", users.get(position));
                detailIntent.putExtras(bundle);
                activity.startActivity(detailIntent);
            }
        });
        holder.userName.setText(
                users.get(position).getTitle() +
                " " +
                users.get(position).getFirst() +
                users.get(position).getLast());
        holder.userPhoneNumber.setText(users.get(position).getPhone());
        Bitmap bitmapFromCache = (Bitmap)cache.getImageFromCache(
                users.get(position)
                        .getPictureThumbnail()
                        .substring(users
                                .get(position)
                                .pictureThumbnail.length() - 6, users.get(position).pictureThumbnail.length()));
        if(bitmapFromCache != null){
            holder.userImage.setImageBitmap(bitmapFromCache);
        }else{
            canceled.set(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bitmap = UsersDbDao.getImage(users.get(position).getPictureThumbnail(), canceled);
                }
            }).start();

            Log.i("onBindViewHolder", position + " bound");
            holder.userImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        //canceled.set(true);
        Log.i("ViewDetachedFromWindow", "canceled");
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TableRow parentView;
        final ImageView userImage;
        final TextView userName, userPhoneNumber;

        ViewHolder(View view) {
            super(view);
            parentView = view.findViewById(R.id.parent_view);
            userImage = view.findViewById(R.id.user_image_thumbnail);
            userName = view.findViewById(R.id.user_name);
            userPhoneNumber = view.findViewById(R.id.user_phone);
        }
    }
}
