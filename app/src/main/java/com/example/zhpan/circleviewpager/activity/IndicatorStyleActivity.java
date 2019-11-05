package com.example.zhpan.circleviewpager.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.view.FigureIndicatorView;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.constants.IndicatorStyle;
import com.zhpan.bannerview.utils.DpUtils;
import com.zhpan.idea.utils.LogUtils;
import com.zhpan.idea.utils.ToastUtils;

public class IndicatorStyleActivity extends BaseDataActivity {

    private BannerViewPager<Integer, ImageResourceViewHolder> mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_slide_mode);
        setTitle(getString(R.string.indicator_style));
        mViewPager = findViewById(R.id.banner_view_dash);
        mViewPager.setRoundCorner(DpUtils.dp2px(5))
                .setIndicatorGap(DpUtils.dp2px(6))
                .setHolderCreator(ImageResourceViewHolder::new);
        initRadioGroup();
    }

    private void initRadioGroup() {
        RadioGroup radioGroupStyle = findViewById(R.id.rg_indicator_style);
        radioGroupStyle.setVisibility(View.VISIBLE);
        radioGroupStyle.setVisibility(View.VISIBLE);
        radioGroupStyle.setOnCheckedChangeListener((group, checkedId) -> {
            LogUtils.e("LLL", "check" + checkedId);
            switch (checkedId) {
                case R.id.rb_circle:
                    mViewPager.resetIndicator();
                    setupCircleIndicator();
                    break;
                case R.id.rb_dash:
                    mViewPager.resetIndicator();
                    setupDashIndicator();
                    break;
                case R.id.rb_custom:
                    setupCustomIndicator();
                    break;
            }
        });
        RadioButton radioButton = findViewById(R.id.rb_circle);
        radioButton.performClick();
    }

    private void setupCircleIndicator() {
        mViewPager.setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorColor(Color.parseColor("#935656"), Color.parseColor("#FF4C39"))
                .setIndicatorRadius(DpUtils.dp2px(4), DpUtils.dp2px(5)).create(mDrawableList);
    }

    private void setupDashIndicator() {
        mViewPager.setIndicatorStyle(IndicatorStyle.DASH)
                .setIndicatorHeight(DpUtils.dp2px(3f))
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorGap(DpUtils.dp2px(3))
                .setIndicatorWidth(DpUtils.dp2px(3), DpUtils.dp2px(10))
                .setIndicatorColor(Color.parseColor("#888888"),
                        Color.parseColor("#118EEA")).create(mDrawableList);
    }

    private void setupCustomIndicator() {
        mViewPager = findViewById(R.id.banner_view_dash);
        mViewPager.setAutoPlay(false).setCanLoop(true)
                .setPageMargin(DpUtils.dp2px(20))
                .setIndicatorGravity(IndicatorGravity.END)
                .setIndicatorView(setupIndicatorView(mDrawableList.size()))
                .setOnPageClickListener(position -> ToastUtils.show(position + ""))
                .setHolderCreator(ImageResourceViewHolder::new).create(mDrawableList);
    }

    /**
     * 这里可以是自定义的Indicator，需要继承BaseIndicatorView或者实现IIndicator接口;
     */
    private FigureIndicatorView setupIndicatorView(int pageSize) {
        FigureIndicatorView indicatorView = new FigureIndicatorView(this);
        indicatorView.setPageSize(pageSize);
        indicatorView.setRadius(DpUtils.dp2px(18));
        indicatorView.setTextSize(DpUtils.dp2px(13));
        indicatorView.setBackgroundColor(Color.parseColor("#88FF5252"));
        return indicatorView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.stopLoop();
    }
}
