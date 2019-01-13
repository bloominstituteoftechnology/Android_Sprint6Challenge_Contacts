package com.example.caz.android_sprint6challenge_contacts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;
    public GetImageFromUrl(ImageView imageView){
        this.imageView = imageView;
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

    }
}