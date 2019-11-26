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
import com.zhpan.bannerview.indicator.IIndicator;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.idea.utils.ToastUtils;

/**
 * Created by zhpan on 2018/7/24.
 */
public class IndicatorFragment extends BaseFragment implements View.OnClickListener {

    private BannerViewPager<Integer, ImageResourceViewHolder> mViewPager;
    private RadioGroup radioGroupStyle;
    private RadioButton radioButton;

    @Override
    protected int getLayout() {
        return R.layout.fragment_others;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView(Bundle savedInstanceState, View view) {
        radioButton = view.findViewById(R.id.rb_circle);
        radioGroupStyle = view.findViewById(R.id.rg_indicator_style);
        mViewPager = view.findViewById(R.id.banner_view);
        view.findViewById(R.id.tv_photo_view).setOnClickListener(this);
        mViewPager.setIndicatorGap(BannerUtils.dp2px(6))
                .setRoundCorner(BannerUtils.dp2px(6))
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
                case R.id.rb_round_rect:
                    setupRoundRectIndicator();
                    break;
                case R.id.rb_custom:
                    setupCustomIndicator();
                    break;
            }
        });
        radioButton.performClick();
    }

    private void setupRoundRectIndicator() {
        mViewPager.setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setIndicatorGap(BannerUtils.dp2px(4))
                .setPageMargin(0)
                .setIndicatorHeight(BannerUtils.dp2px(5f))
                .setOnPageClickListener(position -> ToastUtils.show("position:" + position))
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setIndicatorWidth(BannerUtils.dp2px(5), BannerUtils.dp2px(10)).create(getMDrawableList());
    }

    private void setupCircleIndicator() {
        mViewPager.setIndicatorStyle(IndicatorStyle.CIRCLE)
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
                .setIndicatorGap(BannerUtils.dp2px(3))
                .setPageMargin(0)
                .setIndicatorWidth(BannerUtils.dp2px(3), BannerUtils.dp2px(10))
                .setIndicatorColor(Color.parseColor("#888888"),
                        Color.parseColor("#118EEA")).create(getMDrawableList());
    }

    private void setupCustomIndicator() {
        mViewPager.setAutoPlay(false).setCanLoop(true)
                .setPageMargin(BannerUtils.dp2px(20))
                .setIndicatorGravity(IndicatorGravity.END)
                .setIndicatorView(setupIndicatorView())
                .setHolderCreator(() -> new ImageResourceViewHolder(0)).create(getMDrawableList());
    }

    /**
     * 这里可以是自定义的Indicator，需要继承BaseIndicatorView或者实现IIndicator接口;
     */
    private IIndicator setupIndicatorView() {
        FigureIndicatorView indicatorView = new FigureIndicatorView(getMContext());
        indicatorView.setRadius(BannerUtils.dp2px(18));
        indicatorView.setTextSize(BannerUtils.dp2px(13));
        indicatorView.setBackgroundColor(Color.parseColor("#aa118EEA"));
        return indicatorView;
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), PhotoViewActivity.class));
    }
}
