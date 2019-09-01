package com.example.zhpan.circleviewpager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.SlideModeViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.enums.IndicatorSlideMode;
import com.zhpan.bannerview.enums.IndicatorStyle;

import java.util.Arrays;
import java.util.List;

public class IndicatorSlideModeActivity extends AppCompatActivity {
    private String[] picUrls = {"http://pic31.nipic.com/20130801/11604791_100539834000_2.jpg",
            "http://pic37.nipic.com/20140115/7430301_100825571157_2.jpg",
            "http://pic29.nipic.com/20130507/8952533_183922555000_2.jpg",
            "http://b-ssl.duitang.com/uploads/item/201706/10/20170610095055_G5LM8.jpeg"};
    private BannerViewPager<String, SlideModeViewHolder> mViewPager;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_slide_mode);
        mViewPager = findViewById(R.id.banner_view);
        mList = Arrays.asList(picUrls);
        mViewPager.setAutoPlay(false).setCanLoop(true)
                .setRoundCorner(5f)
                .setIndicatorRadius(3f,3.5f)
                .setHolderCreator(SlideModeViewHolder::new)
                .setIndicatorColor(Color.parseColor("#935656"), Color.parseColor("#FF4C39")).create(mList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.indicator_slide_style_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu0:
                mViewPager.setIndicatorSlideMode(IndicatorSlideMode.NORMAL).create(mList);
                break;
            case R.id.menu1:
                mViewPager.setIndicatorSlideMode(IndicatorSlideMode.SMOOTH).create(mList);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
