package com.example.caz.android_sprint6challenge_contacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;
    private Person person;
    private Context context;

    public GetImageFromUrl(ImageView imageView, Person person, Context context) {
        this.imageView = imageView;
        this.person = person;
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        Bitmap bitmap;
        String urlDisplay = strings[0];
        bitmap = null;

        try {
            InputStream inputStream = new URL(urlDisplay).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);


        this.imageView.setImageBitmap(bitmap);
        String[] parts = person.getThumbnail().split("/");
        String fileName = parts[parts.length-1];
        ImageCache.saveToInternalStorage(fileName, bitmap, context);
    }
}