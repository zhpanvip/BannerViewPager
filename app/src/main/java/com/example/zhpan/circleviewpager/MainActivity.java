package com.example.zhpan.circleviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CircleViewPager mViewpager;
    private int[] images={R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mViewpager = (CircleViewPager) findViewById(R.id.viewpager);
        mViewpager.setImages(images);
        //mViewpager.setDarkDotRes(R.mipmap.ic_launcher);
        //mViewpager.setDotWidth(10);
        mViewpager.setInterval(5000);
    }
}
