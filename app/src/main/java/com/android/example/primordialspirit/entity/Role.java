package com.android.example.primordialspirit.entity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;


import com.android.example.primordialspirit.BR;

import java.io.Serializable;

public class Role extends BaseObservable implements Serializable {
    private String imgStr;
    private Drawable img;
    private String name;
    private String star;//星级

    private String attribute;//属性

    public Role(String imgStr,Drawable img, String name, String star,String attribute) {
        this.imgStr = imgStr;
        this.img = img;
        this.name = name;
        this.star = star;
        this.attribute = attribute;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    @BindingAdapter("img")
    public static void getImage(ImageView view, Drawable img) {
           view.setBackground(img);
    }


    @Bindable
    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
        notifyPropertyChanged(BR.img);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
        notifyPropertyChanged(BR.star);
    }
    @Bindable
    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
        notifyPropertyChanged(BR.attribute);
    }
}
