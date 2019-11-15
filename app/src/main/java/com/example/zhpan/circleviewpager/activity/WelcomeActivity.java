package com.example.zhpan.circleviewpager.activity;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.bean.CustomBean;
import com.example.zhpan.circleviewpager.viewholder.CustomPageViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.constants.TransformerStyle;
import com.zhpan.bannerview.indicator.CircleIndicatorView;
import com.zhpan.bannerview.indicator.IIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends BaseDataActivity {

    private BannerViewPager<CustomBean, CustomPageViewHolder> mViewPager;
    private String[] des = {"在这里\n你可以听到周围人的心声", "在这里\nTA会在下一秒遇见你", "在这里\n不再错过可以改变你一生的人"};
    private int[] transforms = {TransformerStyle.NONE, TransformerStyle.ACCORDION, TransformerStyle.STACK, TransformerStyle.DEPTH, TransformerStyle.ROTATE};

    @BindView(R.id.btn_start)
    TextView mTvStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        getData();
        setupViewPager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.stopLoop();
    }

    private void setupViewPager() {
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAutoPlay(false)
                .setCanLoop(false)
                .setPageTransformerStyle(transforms[new Random().nextInt(4)])
                .setIndicatorVisibility(View.GONE)
                .setIndicatorView(getIndicatorView())
                .setOnPageSelectedListener(this::showStartButton)
                .setHolderCreator(() -> {
                    CustomPageViewHolder customPageViewHolder = new CustomPageViewHolder();
                    customPageViewHolder.setOnSubViewClickListener((view, position) ->
                            Toast.makeText(WelcomeActivity.this, "Click Logo " + position, Toast.LENGTH_SHORT).show());
                    return customPageViewHolder;
                }).create(getData());
    }

    @OnClick(R.id.btn_start)
    public void onClick(View view) {
        MainActivity.start(WelcomeActivity.this);
        finish();
    }

    private void showStartButton(int position) {
        if (position == mViewPager.getList().size() - 1 && mTvStart.getVisibility() == View.GONE) {
            mTvStart.setVisibility(View.VISIBLE);
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mTvStart, "alpha", 0, 1);
            alphaAnimator.setDuration(1500);
            alphaAnimator.start();
        }
    }

    private IIndicator getIndicatorView() {
        CircleIndicatorView indicatorView = findViewById(R.id.indicator);
        indicatorView.setNormalColor(getResources().getColor(R.color.white));
        indicatorView.setCheckedColor(getResources().getColor(R.color.white_alpha_75));
        indicatorView.setIndicatorWidth((int) getResources().getDimension(R.dimen.dp_6),
                (int) getResources().getDimension(R.dimen.dp_9));
        indicatorView.setIndicatorGap((int) getResources().getDimension(R.dimen.dp_10));
        indicatorView.setSlideMode(IndicatorSlideMode.SMOOTH);
        return indicatorView;
    }

    private List<CustomBean> getData() {
        List<CustomBean> list = new ArrayList<>();
        for (int i = 0; i < mDrawableList.size(); i++) {
            CustomBean customBean = new CustomBean();
            customBean.setImageRes(mDrawableList.get(i));
            customBean.setImageDescription(des[i]);
            list.add(customBean);
        }
        return list;
    }
}
