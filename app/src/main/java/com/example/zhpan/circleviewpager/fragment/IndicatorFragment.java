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
    protected void initView(Bundle savedInstanceState, View view) {
        radioButton = view.findViewById(R.id.rb_circle);
        radioGroupStyle = view.findViewById(R.id.rg_indicator_style);
        mViewPager = view.findViewById(R.id.banner_view);
        mViewPager.setIndicatorGap(BannerUtils.dp2px(6))
                .setRoundRect(BannerUtils.dp2px(6))
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
                .setIndicatorWidth(BannerUtils.dp2px(12), BannerUtils.dp2px(12))
                .setIndicatorHeight(BannerUtils.dp2px(1.5f))
                .create(getMDrawableList());
    }

    private void setupRoundRectIndicator() {
        mViewPager.setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorGap(BannerUtils.dp2px(4))
                .setPageMargin(0)
                .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setIndicatorHeight(BannerUtils.dp2px(4f))
                .setOnPageClickListener(position -> ToastUtils.show("position:" + position))
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setIndicatorWidth(BannerUtils.dp2px(4), BannerUtils.dp2px(10)).create(getMDrawableList());
    }

    private void setupCircleIndicator() {
        mViewPager.setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorGap(BannerUtils.dp2px(6))
                .setPageMargin(0)
                .setOnPageClickListener(position -> ToastUtils.show("position:" + position))
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setIndicatorRadius(BannerUtils.dp2px(4), BannerUtils.dp2px(5)).create(getMDrawableList());
    }

    private void setupDashIndicator() {
        mViewPager.setIndicatorStyle(IndicatorStyle.DASH)
                .setIndicatorHeight(BannerUtils.dp2px(3f))
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setIndicatorGap(BannerUtils.dp2px(3))
                .setPageMargin(0)
                .setIndicatorWidth(BannerUtils.dp2px(3), BannerUtils.dp2px(10))
                .setIndicatorColor(Color.parseColor("#888888"),
                        Color.parseColor("#118EEA")).create(getMDrawableList());
    }
}
