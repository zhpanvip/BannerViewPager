package com.example.zhpan.circleviewpager.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.activity.PhotoViewActivity;
import com.example.zhpan.circleviewpager.adapter.ImageResourceAdapter;
import com.example.zhpan.circleviewpager.view.DrawableIndicator;
import com.example.zhpan.circleviewpager.view.FigureIndicatorView;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.idea.utils.LogUtils;
import com.zhpan.idea.utils.ToastUtils;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.base.IIndicator;
import com.zhpan.indicator.enums.IndicatorSlideMode;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by zhpan on 2018/7/24.
 */
public class OthersFragment extends BaseFragment implements View.OnClickListener {

    private BannerViewPager<Integer, ImageResourceViewHolder> mViewPager;
    private RadioGroup radioGroupStyle;
    private RadioButton radioButton;
    private IndicatorView mIndicatorView;

    public static OthersFragment getInstance() {
        return new OthersFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_others;
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
        radioButton = view.findViewById(R.id.rb_indicator_below);
        radioGroupStyle = view.findViewById(R.id.rg_indicator_style);
        mViewPager = view.findViewById(R.id.banner_view);
        mIndicatorView = view.findViewById(R.id.indicator_view);
        view.findViewById(R.id.tv_photo_view).setOnClickListener(this);
        view.findViewById(R.id.btn_refresh).setOnClickListener(v -> updateData());
        mViewPager.setIndicatorSliderGap(BannerUtils.dp2px(6))
                .setRoundCorner(BannerUtils.dp2px(6))
                .setOnPageClickListener(position -> {
                    ToastUtils.show("position:" + position);
                    int currentItem = mViewPager.getCurrentItem();
                    LogUtils.e("currentItem:", currentItem + "");
                })
                .setAdapter(new ImageResourceAdapter(0))
                .setOnPageClickListener(position -> ToastUtils.show("Position:" + position))
                .setIndicatorSliderColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color));
        initRadioGroup();
    }


    private void initRadioGroup() {
        radioGroupStyle.setVisibility(View.VISIBLE);
        radioGroupStyle.setVisibility(View.VISIBLE);
        radioGroupStyle.setOnCheckedChangeListener((group, checkedId) -> {
            resetBannerViewPager();
            switch (checkedId) {
                case R.id.rb_indicator_below:
                    setIndicatorBelowOfBanner();
                    break;
                case R.id.rb_dash:
                    setupCustomIndicator();
                    break;
                case R.id.rb_drawable:
                    setDrawableIndicator(getDrawableIndicator());
                    break;
                case R.id.rb_vector_drawable:
                    setDrawableIndicator(getVectorDrawableIndicator());
                    break;
            }
        });
        radioButton.performClick();
    }

    private void setDrawableIndicator(IIndicator indicator) {
        mIndicatorView.setVisibility(View.INVISIBLE);
        mViewPager
                .setIndicatorView(indicator)
                .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setIndicatorVisibility(View.VISIBLE)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .create(getPicList(4));
    }

    private IIndicator getDrawableIndicator() {
        int dp10 = getResources().getDimensionPixelOffset(R.dimen.dp_10);
        return new DrawableIndicator(getContext())
                .setIndicatorGap(getResources().getDimensionPixelOffset(R.dimen.dp_2_5))
                .setIndicatorDrawable(R.drawable.heart_empty, R.drawable.heart_red)
                .setIndicatorSize(dp10, dp10, dp10, dp10);
    }

    private IIndicator getVectorDrawableIndicator() {
        int dp6 = getResources().getDimensionPixelOffset(R.dimen.dp_6);
        return new DrawableIndicator(getContext())
                .setIndicatorGap(getResources().getDimensionPixelOffset(R.dimen.dp_2_5))
                .setIndicatorDrawable(R.drawable.banner_indicator_nornal, R.drawable.banner_indicator_focus)
                .setIndicatorSize(dp6, dp6, getResources().getDimensionPixelOffset(R.dimen.dp_13), dp6);
    }

    private void setIndicatorBelowOfBanner() {
        mIndicatorView.setVisibility(View.VISIBLE);
        mViewPager
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorVisibility(View.GONE)
                .setIndicatorView(mIndicatorView)
                .create(getPicList(4));
    }


    private void setupCustomIndicator() {
        mIndicatorView.setVisibility(View.INVISIBLE);
        mViewPager.setAutoPlay(false).setCanLoop(true)
                .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setIndicatorVisibility(View.VISIBLE)
                .setIndicatorGravity(IndicatorGravity.END)
                .setIndicatorView(setupIndicatorView()).create(getPicList(4));
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

    private void updateData() {
        mViewPager.setData(getPicList(new Random().nextInt(5)));
        ToastUtils.show("size=" + mViewPager.getData().size());

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), PhotoViewActivity.class));
    }

    /**
     * 注意：在项目中不需要使用该方法
     */
    private void resetBannerViewPager() {
        try {
            Field mIndicatorView = BannerViewPager.class.getDeclaredField("mIndicatorView");
            mIndicatorView.setAccessible(true);
            mIndicatorView.set(mViewPager, null);
            Field isCustomIndicator = BannerViewPager.class.getDeclaredField("isCustomIndicator");
            isCustomIndicator.setAccessible(true);
            isCustomIndicator.set(mViewPager, false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
