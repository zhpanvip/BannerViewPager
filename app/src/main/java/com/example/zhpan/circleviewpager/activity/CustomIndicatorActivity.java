package com.example.zhpan.circleviewpager.activity;

import android.os.Bundle;

import com.example.zhpan.circleviewpager.R;
import com.zhpan.bannerview.indicator.DashIndicatorView;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.utils.DpUtils;
import com.zhpan.idea.utils.ToastUtils;

public class CustomIndicatorActivity extends BaseDataActivity {

    private BannerViewPager<Integer, ImageResourceViewHolder> mBannerViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_slide_mode);
        setTitle(getString(R.string.custom_indicator_view));
        setUpViewPager();
    }

    private void setUpViewPager() {
        mBannerViewPager = findViewById(R.id.banner_view_dash);
        mBannerViewPager.setAutoPlay(false).setCanLoop(true)
                .setRoundCorner(DpUtils.dp2px(5))
                .setIndicatorView(setupIndicatorView(mDrawableList.size()))
                .setOnPageClickListener(position -> ToastUtils.show(position + ""))
                .setHolderCreator(ImageResourceViewHolder::new).create(mDrawableList);
    }

    /**
     * 这里可以是自定义的Indicator，需要继承BaseIndicatorView或者实现IIndicator接口;
     */
    private DashIndicatorView setupIndicatorView(int pageSize) {
        DashIndicatorView indicatorView = new DashIndicatorView(this);
        indicatorView.setPageSize(pageSize);
        indicatorView.setIndicatorWidth(DpUtils.dp2px(8), DpUtils.dp2px(8));
        indicatorView.setSliderHeight(DpUtils.dp2px(4));
        indicatorView.setIndicatorGap(DpUtils.dp2px(5));
        indicatorView.setCheckedColor(getResources().getColor(R.color.colorAccent));
        indicatorView.setNormalColor(getResources().getColor(R.color.colorPrimary));
        return indicatorView;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBannerViewPager.stopLoop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBannerViewPager.startLoop();
    }
}
