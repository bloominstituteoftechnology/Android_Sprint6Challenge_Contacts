package com.example.lambda_school_loaner_47.contactsapp;

import android.util.LruCache;

public class Cache {

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

    public LruCache<Object, Object> getLru(){
        return cacheLru;
    }
}
