package com.example.zhpan.circleviewpager.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

public class WelcomeActivity extends AppCompatActivity {

    private BannerViewPager<CustomBean, CustomPageViewHolder> mViewPager;
    private int[] imgRes = {R.drawable.guide0, R.drawable.guide1, R.drawable.guide2};
    private String[] des = {"在这里\n你可以听到周围人的心声", "在这里\nTA会在下一秒遇见你", "在这里\n不再错过可以改变你一生的人"};
    private int[] transforms = {TransformerStyle.NONE, TransformerStyle.ACCORDION, TransformerStyle.STACK, TransformerStyle.DEPTH, TransformerStyle.ROTATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
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
                .setHolderCreator(() -> {
                    CustomPageViewHolder customPageViewHolder = new CustomPageViewHolder();
                    customPageViewHolder.setOnSubViewClickListener((view, position) -> {
                        MainActivity.start(WelcomeActivity.this);
                        finish();
                    });
                    return customPageViewHolder;
                }).create(getData());
    }

    private IIndicator getIndicatorView() {
        CircleIndicatorView indicatorView = findViewById(R.id.indicator);
        indicatorView.setNormalColor(getResources().getColor(R.color.white));
        indicatorView.setCheckedColor(getResources().getColor(R.color.white_alpha_75));
        indicatorView.setIndicatorWidth((int) getResources().getDimension(R.dimen.dp_6),
                (int) getResources().getDimension(R.dimen.dp_9));
        indicatorView.setIndicatorGap((int) getResources().getDimension(R.dimen.dp_10));
        return indicatorView;
    }

    private List<CustomBean> getData() {
        List<CustomBean> list = new ArrayList<>();
        for (int i = 0; i < imgRes.length; i++) {
            CustomBean customBean = new CustomBean();
            customBean.setImageRes(imgRes[i]);
            customBean.setImageDescription(des[i]);
            list.add(customBean);
        }
        return list;
    }
}
