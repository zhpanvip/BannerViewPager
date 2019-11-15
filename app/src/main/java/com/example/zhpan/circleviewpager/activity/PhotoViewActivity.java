package com.example.zhpan.circleviewpager.activity;


import android.os.Bundle;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.PhotoViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorSlideMode;

public class PhotoViewActivity extends BaseDataActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_photo_view);
        setTitle(R.string.wrapper_photo_view);
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
}
