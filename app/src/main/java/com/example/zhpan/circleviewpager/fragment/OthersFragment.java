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
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.indicator.IIndicator;
import com.zhpan.bannerview.indicator.IndicatorView;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.idea.utils.ToastUtils;

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

    @Override
    protected int getLayout() {
        return R.layout.fragment_others;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView(Bundle savedInstanceState, View view) {
        radioButton = view.findViewById(R.id.rb_indicator_below);
        radioGroupStyle = view.findViewById(R.id.rg_indicator_style);
        mViewPager = view.findViewById(R.id.banner_view);
        mIndicatorView = view.findViewById(R.id.indicator_view);
        view.findViewById(R.id.tv_photo_view).setOnClickListener(this);
        view.findViewById(R.id.btn_refresh).setOnClickListener(v -> updateData());
        mViewPager.setIndicatorGap(BannerUtils.dp2px(6))
                .setRoundRect(BannerUtils.dp2px(6))
                .setOnPageClickListener(position -> ToastUtils.show("Position:" + position))
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setHolderCreator(() -> new ImageResourceViewHolder(0));
        initRadioGroup();
    }

    public static OthersFragment getInstance() {
        return new OthersFragment();
    }

    private void initRadioGroup() {
        radioGroupStyle.setVisibility(View.VISIBLE);
        radioGroupStyle.setVisibility(View.VISIBLE);
        radioGroupStyle.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_indicator_below:
                    resetBannerViewPager();
                    setIndicatorBelowOfBanner();
                    break;
                case R.id.rb_dash:
                    resetBannerViewPager();
                    setupCustomIndicator();
                    break;
            }
        });
        radioButton.performClick();
    }


    private void setIndicatorBelowOfBanner() {
        mIndicatorView.setVisibility(View.VISIBLE);
        mViewPager
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorVisibility(View.GONE)
                .setIndicatorView(mIndicatorView)
                .create(getMDrawableList());
    }


    private void setupCustomIndicator() {
        mIndicatorView.setVisibility(View.INVISIBLE);
        mViewPager.setAutoPlay(false).setCanLoop(true)
                .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setIndicatorVisibility(View.VISIBLE)
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

    private void updateData() {
        //  生成[-1,3]整数
        initData(new Random().nextInt(5) - 1);
        ToastUtils.show("size=" + getMDrawableList().size());
        mViewPager.create(getMDrawableList());
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), PhotoViewActivity.class));
    }

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
