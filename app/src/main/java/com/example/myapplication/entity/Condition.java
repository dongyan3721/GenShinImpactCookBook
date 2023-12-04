package com.example.myapplication.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.myapplication.BR;

import java.io.Serializable;

public class Condition extends BaseObservable implements Serializable {
    private String value;//条件值

    public Condition(String value) {
        this.value = value;
    }


    @Bindable
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        notifyPropertyChanged(BR.value);
    }
}
