package com.example.zhpan.circleviewpager.activity;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
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
import com.zhpan.bannerview.utils.BannerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WelcomeActivity extends BaseDataActivity implements
        HolderCreator<CustomPageViewHolder> {

    private BannerViewPager<CustomBean, CustomPageViewHolder> mViewPager;

    private String[] des = {"在这里\n你可以听到周围人的心声", "在这里\nTA会在下一秒遇见你", "在这里\n不再错过可以改变你一生的人"};

    private int[] transforms = {TransformerStyle.NONE, TransformerStyle.ACCORDION, TransformerStyle.STACK, TransformerStyle.DEPTH, TransformerStyle.ROTATE};

    TextView mTvStart;

    TextView mTvDescription;

    private static final int ANIMATION_DURATION = 1300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mTvStart = findViewById(R.id.btn_start);
        mTvDescription = findViewById(R.id.tv_describe);
        setupViewPager();
        updateUI(0);
    }

    private void setupViewPager() {
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAutoPlay(false)
                .setCanLoop(false)
                .setPageTransformerStyle(transforms[new Random().nextInt(5)])
                .setScrollDuration(ANIMATION_DURATION)
                .setIndicatorMargin(0, 0, 0, BannerUtils.dp2px(100))
                .setIndicatorGap((int) getResources().getDimension(R.dimen.dp_10))
                .setIndicatorColor(getResources().getColor(R.color.white),
                        getResources().getColor(R.color.white_alpha_75))
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorRadius((int) getResources().getDimension(R.dimen.dp_3), (int) getResources().getDimension(R.dimen.dp_4_5))
                .setOnPageChangeListener(new OnPageChangeListenerAdapter() {
                    @Override
                    public void onPageSelected(int position) {
                        updateUI(position);
                    }
                })
                .setHolderCreator(this)
                .create(getData());
    }

    public void onClick(View view) {
        MainActivity.start(WelcomeActivity.this);
        finish();
    }

    private void updateUI(int position) {
        mTvDescription.setText(des[position]);
        ObjectAnimator translationAnim = ObjectAnimator.ofFloat(mTvDescription, "translationX", -120, 0);
        translationAnim.setDuration(ANIMATION_DURATION);
        translationAnim.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(mTvDescription, "alpha", 0, 1);
        alphaAnimator1.setDuration(ANIMATION_DURATION);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationAnim, alphaAnimator1);
        animatorSet.start();

        if (position == mViewPager.getList().size() - 1 && mTvStart.getVisibility() == View.GONE) {
            mTvStart.setVisibility(View.VISIBLE);
            ObjectAnimator
                    .ofFloat(mTvStart, "alpha", 0, 1)
                    .setDuration(ANIMATION_DURATION).start();
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
