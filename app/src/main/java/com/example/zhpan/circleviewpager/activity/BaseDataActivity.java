package com.example.zhpan.circleviewpager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDataActivity extends AppCompatActivity {

    protected List<Integer> mDrawableList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        for (int i = 0; i <= 2; i++) {
            int drawable = getResources().getIdentifier("guide" + i, "drawable", getPackageName());
            mDrawableList.add(drawable);
        }
    }
}
