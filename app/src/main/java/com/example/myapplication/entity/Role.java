package com.example.myapplication.entity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.example.myapplication.BR;
import com.example.myapplication.R;

import java.io.Serializable;

public class Role extends BaseObservable implements Serializable {
    private Drawable img;
    private String name;
    private int star;//星级
    private Drawable starImg;//星级图片
    private String attribute;//属性
    private String gender;// 性别
    private String weapon;//  武器



    public Role(Drawable img, String name, int star,Drawable starImg, String attribute, String gender, String weapon) {
        this.img = img;
        this.name = name;
        this.star = star;
        this.starImg = starImg;
        this.attribute = attribute;
        this.gender = gender;
        this.weapon = weapon;
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
    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
        notifyPropertyChanged(BR.star);
    }


    @BindingAdapter("starImg")
    public static void getStarImage(ImageView view, Drawable starImg) {
        view.setBackground(starImg);
    }
    @Bindable
    public Drawable getStarImg() {
        return starImg;
    }

    public void setStarImg(Drawable starImg) {
        this.starImg = starImg;
        notifyPropertyChanged(BR.starImg);
    }

    @Bindable
    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
        notifyPropertyChanged(BR.attribute);
    }

    @Bindable
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }

    @Bindable
    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
        notifyPropertyChanged(BR.weapon);
    }
}
