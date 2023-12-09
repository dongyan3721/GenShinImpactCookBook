package com.example.myapplication.entity;

import android.graphics.drawable.Drawable;

// defining the model
public class Members {
    public String imageAD;
    public String name;
    public String detail;
    public Drawable imageURL;
    public Members(String imageAD, String name, String detail,Drawable imageURL)
    {
        this.imageAD = imageAD;
        this.name = name;
        this.detail = detail;
        this.imageURL = imageURL;
    }
}
