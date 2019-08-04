package com.example.zhpan.circleviewpager.bean;

import android.support.annotation.DrawableRes;

public class CustomBean {

    private int imageRes;

    private String imageDescription;

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(@DrawableRes int imageRes) {
        this.imageRes = imageRes;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }
}
