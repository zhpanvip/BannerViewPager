package com.example.zhpan.circleviewpager.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.adapter.ImageResourceAdapter;
import com.example.zhpan.circleviewpager.view.FigureIndicatorView;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.idea.utils.LogUtils;
import com.zhpan.idea.utils.ToastUtils;
import com.zhpan.indicator.DrawableIndicator;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.base.IIndicator;
import com.zhpan.indicator.enums.IndicatorSlideMode;

import java.util.Random;

/**
 * Created by zhpan on 2018/7/24.
 */
public class OthersFragment extends BaseFragment implements View.OnClickListener {

    private BannerViewPager<Integer, ImageResourceViewHolder> mViewPager;
    private RadioGroup radioGroupStyle;
    private RadioButton radioButton;
    private IndicatorView mIndicatorView;
    private SmartRefreshLayout mSmartRefreshLayout;

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

    private void initRefreshLayout(View view) {
        mSmartRefreshLayout = view.findViewById(R.id.refresh_layout);
        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(getMContext()));
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            updateData();
            mSmartRefreshLayout.finishRefresh();
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState, @NonNull View view) {
        initRefreshLayout(view);
        radioButton = view.findViewById(R.id.rb_indicator_below);
        radioGroupStyle = view.findViewById(R.id.rg_indicator_style);
        mViewPager = view.findViewById(R.id.banner_view);
        mIndicatorView = view.findViewById(R.id.indicator_view);
        view.findViewById(R.id.tv_photo_view).setOnClickListener(this);
        mViewPager.setIndicatorSliderGap(BannerUtils.dp2px(6))
                .setIndicatorView(mIndicatorView)
                .setLifecycleRegistry(getLifecycle())
                .setRoundCorner(BannerUtils.dp2px(6))
                .setOnPageClickListener(position -> {
                    ToastUtils.show("position:" + position);
                    int currentItem = mViewPager.getCurrentItem();
                    LogUtils.e("currentItem:", currentItem + "");
                })
                .setAdapter(new ImageResourceAdapter(0))
                .setOnPageClickListener(position -> ToastUtils.show("Position:" + position))
                .setIndicatorSliderColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color)).create();
        initRadioGroup();
    }


    private void initRadioGroup() {
        radioGroupStyle.setVisibility(View.VISIBLE);
        radioGroupStyle.setOnCheckedChangeListener((group, checkedId) -> {
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
                .refreshData(getMDrawableList());
    }

    private void setIndicatorBelowOfBanner() {
        mIndicatorView.setVisibility(View.VISIBLE);
        mViewPager
                .setIndicatorVisibility(View.GONE)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorView(mIndicatorView)
                .refreshData(getPicList(4));
    }

    private void setupCustomIndicator() {
        mIndicatorView.setVisibility(View.INVISIBLE);
        mViewPager.setAutoPlay(true).setCanLoop(true)
                .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setIndicatorVisibility(View.VISIBLE)
                .setIndicatorGravity(IndicatorGravity.END)
                .setIndicatorView(setupIndicatorView()).refreshData(getPicList(4));
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

    /**
     * 这里可以是自定义的Indicator，需要继承BaseIndicatorView或者实现IIndicator接口;
     */
    private IIndicator setupIndicatorView() {
        FigureIndicatorView indicatorView = new FigureIndicatorView(getMContext());
        indicatorView.setRadius(getResources().getDimensionPixelOffset(R.dimen.dp_18));
        indicatorView.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_13));
        indicatorView.setBackgroundColor(Color.parseColor("#aa118EEA"));
        return indicatorView;
    }

    private void updateData() {
        mViewPager.refreshData(getPicList(new Random().nextInt(5) - 1));
        ToastUtils.show("size=" + mViewPager.getData().size());
    }

    @Override
    public void onClick(View view) {
        int position = new Random().nextInt(5);
        mViewPager.setCurrentItem(position, true);
        ToastUtils.show("Jump to position:" + position);
    }
}
