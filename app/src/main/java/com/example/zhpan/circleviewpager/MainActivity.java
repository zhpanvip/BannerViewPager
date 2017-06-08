package com.example.zhpan.circleviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.example.viewpager.view.CircleViewPager;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CircleViewPager mViewpager;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setViewPager();
    }

    private void initView() {
        mViewpager = (CircleViewPager) findViewById(R.id.viewpager);
    }

    private void initData() {
        mList.add("http://pic.58pic.com/58pic/15/36/01/17C58PICR67_1024.jpg");
        mList.add("http://img.tupianzj.com/uploads/allimg/160822/9-160R2213608.jpg");
        mList.add("http://img1.ph.126.net/3NuwEWzx-efuHLUhoAg1Rw==/1459447754345023507.jpg");
        mList.add("http://pic.58pic.com/58pic/15/36/02/06Q58PICH7S_1024.jpg");
        mList.add("http://images.jfdaily.com/jiefang/life/new/201502/W020150213267781833219.jpg");
    }

    private void setViewPager() {
        mViewpager.setDarkDotRes(R.drawable.red_dot_night);
        mViewpager.setLightDotRes(R.drawable.red_dot);
        mViewpager.setDotWidth(10);
        mViewpager.setInterval(5000);
        mViewpager.setUrlList(mList);
        mViewpager.setOnPageClickListener(new CircleViewPager.OnPageClickListener() {
            @Override
            public void pageClickListener(int position) {
                Toast.makeText(MainActivity.this, mViewpager.getUrlList().get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
