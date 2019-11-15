package com.example.zhpan.circleviewpager.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.bean.CustomBean;
import com.example.zhpan.circleviewpager.viewholder.CustomPageViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.adapter.OnPageChangeListenerAdapter;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.constants.TransformerStyle;
import com.zhpan.bannerview.holder.HolderCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends BaseDataActivity implements
        HolderCreator<CustomPageViewHolder> {

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
        setupViewPager();
    }

    @Override
    protected void onDestroy() {
        mViewPager.stopLoop();
        super.onDestroy();
    }

    private void setupViewPager() {
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAutoPlay(false)
                .setCanLoop(false)
                .setPageTransformerStyle(transforms[new Random().nextInt(5)])
                .setIndicatorVisibility(View.GONE)
                .setIndicatorView(findViewById(R.id.indicator))
                .setIndicatorGap((int) getResources().getDimension(R.dimen.dp_10))
                .setIndicatorColor(getResources().getColor(R.color.white),
                        getResources().getColor(R.color.white_alpha_75))
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorRadius((int) getResources().getDimension(R.dimen.dp_3), (int) getResources().getDimension(R.dimen.dp_4_5))
                .setOnPageChangeListener(new OnPageChangeListenerAdapter() {
                    @Override
                    public void onPageSelected(int position) {
                        showStartButton(position);
                    }
                })
                .setHolderCreator(this)
                .create(getData());
    }

    @OnClick(R.id.btn_start)
    public void onClick(View view) {
        MainActivity.start(WelcomeActivity.this);
        finish();
    }

    private void showStartButton(int position) {
        if (position == mViewPager.getList().size() - 1 && mTvStart.getVisibility() == View.GONE) {
            mTvStart.setVisibility(View.VISIBLE);
            ObjectAnimator
                    .ofFloat(mTvStart, "alpha", 0, 1)
                    .setDuration(1500).start();
        } else {
            mTvStart.setVisibility(View.GONE);
        }
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

    @Override
    public CustomPageViewHolder createViewHolder() {
        CustomPageViewHolder customPageViewHolder = new CustomPageViewHolder();
        customPageViewHolder.setOnSubViewClickListener((view, position) ->
                Toast.makeText(WelcomeActivity.this, "Logo Clicked,Item: " + position, Toast.LENGTH_SHORT).show());
        return customPageViewHolder;
    }
}
