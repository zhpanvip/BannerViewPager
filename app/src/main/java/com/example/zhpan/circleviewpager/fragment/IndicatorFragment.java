package com.example.zhpan.circleviewpager.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.annotation.AIndicatorSlideMode;
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
    private RadioGroup mRadioGroupStyle;
    private RadioGroup mRadioGroupMode;
    private RadioButton radioButton;
    private @AIndicatorSlideMode
    int mSlideMode = IndicatorSlideMode.WORM;
    private int mCheckId = R.id.rb_circle;

    @Override
    protected int getLayout() {
        return R.layout.fragment_indicator;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public void onPause() {
        super.onPause();
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
        mRadioGroupStyle = view.findViewById(R.id.rg_indicator_style);
        mRadioGroupMode = view.findViewById(R.id.rg_slide_mode);
        mViewPager = view.findViewById(R.id.banner_view);
        mViewPager.setIndicatorGap(BannerUtils.dp2px(6))
                .setRoundCorner(BannerUtils.dp2px(6))
                .setHolderCreator(() -> new ImageResourceViewHolder(0));
        initRadioGroup();
    }

    public static IndicatorFragment getInstance() {
        return new IndicatorFragment();
    }

    private void initRadioGroup() {
        mRadioGroupStyle.setOnCheckedChangeListener((group, checkedId) -> checkedChange(mCheckId = checkedId));
        radioButton.performClick();
        mRadioGroupMode.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_normal:
                    mSlideMode = IndicatorSlideMode.NORMAL;
                    break;
                case R.id.rb_worm:
                    mSlideMode = IndicatorSlideMode.WORM;
                    break;
                case R.id.rb_smooth:
                    mSlideMode = IndicatorSlideMode.SMOOTH;
                    break;
            }
            checkedChange(mCheckId);
        });
    }

    private void checkedChange(int checkedId) {
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
                setupTMallIndicator();
                break;
        }
    }

    private void setupTMallIndicator() {
        mViewPager
                .setIndicatorStyle(IndicatorStyle.DASH)
                .setIndicatorGap(0)
                .setIndicatorSlideMode(mSlideMode)
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setIndicatorWidth(getResources().getDimensionPixelOffset(R.dimen.dp_15))
                .setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.dp_3))
                .create(getMDrawableList());
    }

    private void setupRoundRectIndicator() {
        int checkedWidth = getResources().getDimensionPixelOffset(R.dimen.dp_10);
        int normalWidth = getNormalWidth();
        mViewPager.setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorGap(BannerUtils.dp2px(4))
                .setPageMargin(0)
                .setIndicatorSlideMode(mSlideMode)
                .setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.dp_4))
                .setOnPageClickListener(position -> ToastUtils.show("position:" + position))
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setIndicatorWidth(normalWidth, checkedWidth).create(getMDrawableList());
    }

    private void setupCircleIndicator() {
        mViewPager.setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(mSlideMode)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorGap(getResources().getDimensionPixelOffset(R.dimen.dp_6))
                .setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.dp_4))
                .setPageMargin(0)
                .setOnPageClickListener(position -> ToastUtils.show("position:" + position))
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setIndicatorRadius(getResources().getDimensionPixelOffset(R.dimen.dp_4)).create(getMDrawableList());
    }

    private void setupDashIndicator() {
        int checkedWidth = getResources().getDimensionPixelOffset(R.dimen.dp_10);
        int normalWidth = getNormalWidth();
        mViewPager.setIndicatorStyle(IndicatorStyle.DASH)
                .setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.dp_3))
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorSlideMode(mSlideMode)
                .setIndicatorGap(getResources().getDimensionPixelOffset(R.dimen.dp_3))
                .setPageMargin(0)
                .setIndicatorWidth(normalWidth, checkedWidth)
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .create(getMDrawableList());
    }

    private int getNormalWidth() {
        if (mSlideMode == IndicatorSlideMode.SMOOTH || mSlideMode == IndicatorSlideMode.WORM) {
            return getResources().getDimensionPixelOffset(R.dimen.dp_10);
        } else {
            return getResources().getDimensionPixelOffset(R.dimen.dp_4);
        }
    }
}
