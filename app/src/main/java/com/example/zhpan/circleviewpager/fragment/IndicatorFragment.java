package com.example.zhpan.circleviewpager.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.constants.IndicatorStyle;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.idea.utils.ToastUtils;

/**
 * Created by zhpan on 2018/7/24.
 */
public class IndicatorFragment extends BaseFragment {

    private BannerViewPager<Integer, ImageResourceViewHolder> mViewPager;
    private RadioGroup radioGroupStyle;
    private RadioButton radioButton;

    @Override
    protected int getLayout() {
        return R.layout.fragment_indicator;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mViewPager != null) {
            mViewPager.stopLoop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mViewPager != null) {
            mViewPager.startLoop();
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState, View view) {
        radioButton = view.findViewById(R.id.rb_circle);
        radioGroupStyle = view.findViewById(R.id.rg_indicator_style);
        mViewPager = view.findViewById(R.id.banner_view);
        mViewPager.setIndicatorGap(getResources().getDimensionPixelOffset(R.dimen.dp_6))
                .setRoundRect(getResources().getDimensionPixelOffset(R.dimen.dp_6))
                .setHolderCreator(() -> new ImageResourceViewHolder(0));
        initRadioGroup();
    }

    public static IndicatorFragment getInstance() {
        return new IndicatorFragment();
    }

    private void initRadioGroup() {
        radioGroupStyle.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_circle:
                    setupCircleIndicator();
                    break;
                case R.id.rb_dash:
                    setupDashIndicator();
                    break;
                case R.id.rb_round_rect:
                    setupRoundRectIndicator();
                    break;
                case R.id.rb_tmall:
                    setupTmallIndicator();
                    break;
            }
        });
        radioButton.performClick();
    }

    private void setupTmallIndicator() {
        mViewPager
                .setIndicatorStyle(IndicatorStyle.DASH)
                .setIndicatorGap(0)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorColor(getColor(R.color.white_alpha_75), getColor(R.color.white))
                .setIndicatorWidth(getResources().getDimensionPixelOffset(R.dimen.dp_12), getResources().getDimensionPixelOffset(R.dimen.dp_12))
                .setIndicatorHeight(BannerUtils.dp2px(1.5f))
                .create(getMDrawableList());
    }

    private void setupRoundRectIndicator() {
        mViewPager.setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorGap(BannerUtils.dp2px(4))
                .setPageMargin(0)
                .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.dp_4))
                .setOnPageClickListener(position -> ToastUtils.show("position:" + position))
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setIndicatorWidth(getResources().getDimensionPixelOffset(R.dimen.dp_4), getResources().getDimensionPixelOffset(R.dimen.dp_10)).create(getMDrawableList());
    }

    private void setupCircleIndicator() {
        mViewPager.setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorGap(getResources().getDimensionPixelOffset(R.dimen.dp_6))
                .setPageMargin(0)
                .setOnPageClickListener(position -> ToastUtils.show("position:" + position))
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setIndicatorRadius(getResources().getDimensionPixelOffset(R.dimen.dp_4), getResources().getDimensionPixelOffset(R.dimen.dp_5)).create(getMDrawableList());
    }

    private void setupDashIndicator() {
        mViewPager.setIndicatorStyle(IndicatorStyle.DASH)
                .setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.dp_3))
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setIndicatorGap(getResources().getDimensionPixelOffset(R.dimen.dp_3))
                .setPageMargin(0)
                .setIndicatorWidth(getResources().getDimensionPixelOffset(R.dimen.dp_3), getResources().getDimensionPixelOffset(R.dimen.dp_10))
                .setIndicatorColor(Color.parseColor("#888888"),
                        Color.parseColor("#118EEA")).create(getMDrawableList());
    }
}
