package com.example.zhpan.banner.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.zhpan.banner.R;
import com.example.zhpan.banner.adapter.ViewBindingSampleAdapter;
import com.example.zhpan.banner.view.FigureIndicatorView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.indicator.DrawableIndicator;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.base.IIndicator;
import com.zhpan.indicator.enums.IndicatorSlideMode;

import java.util.Random;

/**
 * Created by zhpan on 2018/7/24.
 */
public class OthersFragment extends BaseFragment implements View.OnClickListener {

    private BannerViewPager<Integer> mViewPager;
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
        view.findViewById(R.id.tv_add_data).setOnClickListener(this);
        view.findViewById(R.id.tv_remove_item).setOnClickListener(this);
        view.findViewById(R.id.tv_insert_item).setOnClickListener(this);
        mIndicatorView = view.findViewById(R.id.indicator_view);
        view.findViewById(R.id.tv_photo_view).setOnClickListener(this);
        mViewPager.setIndicatorSliderGap(BannerUtils.dp2px(6))
                .setIndicatorView(mIndicatorView)
                .setLifecycleRegistry(getLifecycle())
                .setRoundCorner(BannerUtils.dp2px(6))
                .setOnPageClickListener((clickedView, position) -> {
                    ToastUtils.showShort("position:" + position);
                    int currentItem = mViewPager.getCurrentItem();
                    LogUtils.e("currentItem:", currentItem + "");
                })
                .setAdapter(new ViewBindingSampleAdapter(0))
                .setOnPageClickListener((clickedView, position) -> ToastUtils.showShort("Position:" + position))
                .setIndicatorSliderColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color)).create();
        initRadioGroup();
    }


    private void initRadioGroup() {
        radioGroupStyle.setVisibility(View.VISIBLE);
        radioGroupStyle.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_indicator_below) {
                setIndicatorBelowOfBanner();
            } else if (checkedId == R.id.rb_dash) {
                setupCustomIndicator();
            } else if (checkedId == R.id.rb_drawable) {
                setDrawableIndicator(getDrawableIndicator());
            } else if (checkedId == R.id.rb_vector_drawable) {
                setDrawableIndicator(getVectorDrawableIndicator());
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
                .refreshData(getPicList(4));
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
        ToastUtils.showShort("size=" + mViewPager.getData().size());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_add_data) {
            addData();
        } else if (view.getId() == R.id.tv_remove_item) {
            removeItem();
        } else if (view.getId() == R.id.tv_insert_item) {
            insertItem();
        } else {
            int position = new Random().nextInt(5);
            mViewPager.setCurrentItem(position, true);
            ToastUtils.showShort("Jump to position:" + position);
        }
    }

    private void addData() {
        mViewPager.addData(getPicList(2));
        ToastUtils.showShort("size=" + mViewPager.getData().size());
    }

    private void insertItem() {
        Integer item = getPicList(0).get(0);
        mViewPager.insertItem(mViewPager.getData().size(), item);
    }

    private void removeItem() {
        mViewPager.removeItem(mViewPager.getData().size() - 1);
    }
}
