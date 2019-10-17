package com.example.zhpan.circleviewpager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class BaseDataActivity extends AppCompatActivity {
    protected List<Integer> mDrawableList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        for (int i = 0; i <= 3; i++) {
            int drawable2 = getResources().getIdentifier("t" + i, "drawable", getPackageName());
            mDrawableList.add(drawable2);
        }
    }
}
