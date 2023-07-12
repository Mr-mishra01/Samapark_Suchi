package com.example.samaparksuchi;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ContactModel implements Serializable {
    int img;
    String name , numb;

    public ContactModel(String name, String numb) {
        this.name = name;
        this.numb = numb;
    }

    public ContactModel(int img, String name, String numb) {
        this.img = img;
        this.name = name;
        this.numb = numb;
    }
}
