package com.example.zhpan.circleviewpager.activity;


import android.graphics.Color;
import android.os.Bundle;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.enums.IndicatorSlideMode;
import com.zhpan.bannerview.enums.IndicatorStyle;
import com.zhpan.bannerview.utils.DpUtils;

public class IndicatorStyleActivity extends BaseDataActivity {

    private BannerViewPager<Integer, ImageResourceViewHolder> mViewPagerSmoothSlide;

    private BannerViewPager<Integer, ImageResourceViewHolder> mViewPagerNormalSlide;

    private BannerViewPager<Integer, ImageResourceViewHolder> mViewPagerDash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_slide_mode);
        setTitle(getString(R.string.indicator_style));
        initCircleNormalSlide();
        initCircleSmoothSlide();
        initDashIndicator();
    }

    private void initDashIndicator() {
        mViewPagerDash = findViewById(R.id.banner_view_dash);
        mViewPagerDash.setAutoPlay(true).setCanLoop(true)
                .setIndicatorGap(DpUtils.dp2px(5))
                .setScrollDuration(1000).setPageMargin(DpUtils.dp2px(20))
                .setIndicatorHeight(DpUtils.dp2px(2.5f))
                .setIndicatorStyle(IndicatorStyle.DASH)
                .setIndicatorWidth(DpUtils.dp2px(10), DpUtils.dp2px(5))
                .setHolderCreator(ImageResourceViewHolder::new)
                .setIndicatorColor(Color.parseColor("#888888"),
                        Color.parseColor("#118EEA")).create(mDrawableList);
    }

    private void initCircleSmoothSlide() {
        mViewPagerSmoothSlide = findViewById(R.id.banner_view);
        mViewPagerSmoothSlide.setAutoPlay(true).setCanLoop(true)
                .setRoundCorner(DpUtils.dp2px(5))
                .setIndicatorGap(DpUtils.dp2px(7))
                .setScrollDuration(1000)
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorRadius(DpUtils.dp2px(6), DpUtils.dp2px(7))
                .setHolderCreator(ImageResourceViewHolder::new)
                .setIndicatorColor(Color.parseColor("#935656"),
                        Color.parseColor("#CCFF4C39")).create(mDrawableList);
    }

    private void initCircleNormalSlide() {
        mViewPagerNormalSlide = findViewById(R.id.banner_view_normal_slide);
        mViewPagerNormalSlide.setAutoPlay(true).setCanLoop(true)
                .setRoundCorner(DpUtils.dp2px(5))
                .setIndicatorGap(DpUtils.dp2px(7))
                .setScrollDuration(1000)
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorWidth(DpUtils.dp2px(8))
                .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setHolderCreator(ImageResourceViewHolder::new)
                .setIndicatorColor(Color.parseColor("#888888"),
                        Color.parseColor("#118EEA")).create(mDrawableList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPagerSmoothSlide.stopLoop();
        mViewPagerNormalSlide.stopLoop();
        mViewPagerDash.stopLoop();
    }
}
