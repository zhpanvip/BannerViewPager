package com.example.zhpan.circleviewpager.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.activity.PhotoViewActivity;
import com.example.zhpan.circleviewpager.view.FigureIndicatorView;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.constants.IndicatorStyle;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.idea.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhpan on 2018/7/24.
 */
public class IndicatorFragment extends BaseFragment {
    @BindView(R.id.banner_view)
    BannerViewPager<Integer, ImageResourceViewHolder> mViewPager;
    @BindView(R.id.rg_indicator_style)
    RadioGroup radioGroupStyle;
    @BindView(R.id.rb_circle)
    RadioButton radioButton;

    @Override
    protected int getLayout() {
        return R.layout.fragment_others;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView(Bundle savedInstanceState, View view) {
        mViewPager.setIndicatorGap(BannerUtils.dp2px(6))
                .setHolderCreator(() -> new ImageResourceViewHolder(0));
        initRadioGroup();
    }

    public static IndicatorFragment getInstance() {
        return new IndicatorFragment();
    }

    private void initRadioGroup() {
        radioGroupStyle.setVisibility(View.VISIBLE);
        radioGroupStyle.setVisibility(View.VISIBLE);
        radioGroupStyle.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_circle:
//                    mViewPager.resetIndicator();
                    setupCircleIndicator();
                    break;
                case R.id.rb_dash:
//                    mViewPager.resetIndicator();
                    setupDashIndicator();
                    break;
                case R.id.rb_custom:
                    setupCustomIndicator();
                    break;
            }
        });
        radioButton.performClick();
    }

    private void setupCircleIndicator() {
        mViewPager.setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorGap(BannerUtils.dp2px(6))
                .setPageMargin(0)
                .setOnPageClickListener(position -> ToastUtils.show("position:" + position))
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setIndicatorRadius(BannerUtils.dp2px(4), BannerUtils.dp2px(5)).create(mDrawableList);
    }

    private void setupDashIndicator() {
        mViewPager.setIndicatorStyle(IndicatorStyle.DASH)
                .setIndicatorHeight(BannerUtils.dp2px(3f))
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorGap(BannerUtils.dp2px(3))
                .setPageMargin(0)
                .setIndicatorWidth(BannerUtils.dp2px(3), BannerUtils.dp2px(10))
                .setIndicatorColor(Color.parseColor("#888888"),
                        Color.parseColor("#118EEA")).create(mDrawableList);
    }

    private void setupCustomIndicator() {
        mViewPager.setAutoPlay(false).setCanLoop(true)
                .setPageMargin(BannerUtils.dp2px(20))
                .setIndicatorGravity(IndicatorGravity.END)
                .setIndicatorView(setupIndicatorView())
                .setHolderCreator(() -> new ImageResourceViewHolder(0)).create(mDrawableList);
    }

    /**
     * 这里可以是自定义的Indicator，需要继承BaseIndicatorView或者实现IIndicator接口;
     */
    private FigureIndicatorView setupIndicatorView() {
        FigureIndicatorView indicatorView = new FigureIndicatorView(mContext);
        indicatorView.setRadius(BannerUtils.dp2px(18));
        indicatorView.setTextSize(BannerUtils.dp2px(13));
        indicatorView.setBackgroundColor(Color.parseColor("#aa118EEA"));
        return indicatorView;
    }

    @OnClick(R.id.tv_photo_view)
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), PhotoViewActivity.class));
    }

    @Override
    public void onStop() {
        if (mViewPager != null)
            mViewPager.stopLoop();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mViewPager != null)
            mViewPager.stopLoop();
    }
}
