package com.example.lambda_school_loaner_47.contactsapp;

import android.graphics.Bitmap;
import android.util.LruCache;

public class Cache<T> {

    private static Cache instance;
    private LruCache<Object, Object> cacheLru;

    private Cache(){
        this.cacheLru = new LruCache<Object, Object>(1024);

    }

    public static Cache getInstance(){
        if (instance == null){
            instance = new Cache();
        }

        return instance;
    }

    public LruCache getLru(){
        return cacheLru;
    }
}
