package com.example.caz.android_sprint6challenge_contacts;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCache {

    public static String saveToInternalStorage(String fileName, Bitmap bitmapImage, Context context){

        ContextWrapper cw = new ContextWrapper(context);

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath=new File(directory,fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public static  Bitmap loadImageFromStorage(Person person, Context context)
    {
        String[] parts = person.getThumbnail().split("/");
        String fileName = parts[parts.length-1];
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath=new File(directory,fileName);                   // Store the given bitmap image as this fileName
        try {
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(mypath));
            //ImageView img=(ImageView)findViewById(R.id.imgPicker);
            //img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
        }
        return null;
    }
}
