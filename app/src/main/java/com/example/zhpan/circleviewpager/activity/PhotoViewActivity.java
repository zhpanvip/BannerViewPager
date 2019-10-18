package com.example.zhpan.circleviewpager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.PhotoViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorSlideMode;

import java.util.ArrayList;
import java.util.List;

public class PhotoViewActivity extends AppCompatActivity {
    private List<Integer> mDrawableList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_photo_view);
        setTitle(R.string.wrapper_photo_view);
        initData();
        initViewPager();
    }

    private void initViewPager() {
        BannerViewPager<Integer, PhotoViewHolder> bannerViewPager = findViewById(R.id.viewpager);
        bannerViewPager.setAutoPlay(false)
                .setCanLoop(false)
                .setHolderCreator(PhotoViewHolder::new)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .create(mDrawableList);
        bannerViewPager.setCurrentItem(1);
    }

    private void initData() {
        for (int i = 0; i <= 3; i++) {
            int drawable2 = getResources().getIdentifier("c" + i, "drawable", getPackageName());
            mDrawableList.add(drawable2);
        }
    }
}
