package com.example.zhpan.circleviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CircleViewPager mViewpager;
    //private int[] images={R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5};
    private List<String> mList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mList.add("http://pic.58pic.com/58pic/15/36/01/17C58PICR67_1024.jpg");
        mList.add("http://img.tupianzj.com/uploads/allimg/160822/9-160R2213608.jpg");
        mList.add("http://img1.ph.126.net/3NuwEWzx-efuHLUhoAg1Rw==/1459447754345023507.jpg");
        mList.add("http://pic.58pic.com/58pic/15/36/02/06Q58PICH7S_1024.jpg");
        mList.add("http://images.jfdaily.com/jiefang/life/new/201502/W020150213267781833219.jpg");
        mViewpager = (CircleViewPager) findViewById(R.id.viewpager);
        //mViewpager.setImages(images);
        mViewpager.setDarkDotRes(R.mipmap.ic_launcher);
        mViewpager.setDotWidth(10);
        mViewpager.setInterval(5000);
        mViewpager.setUrlList(mList);
        mViewpager.setOnPageClickListener(new CircleViewPager.OnPageClickListener() {
            @Override
            public void pageClickListener(int position) {
                Toast.makeText(MainActivity.this, mList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
