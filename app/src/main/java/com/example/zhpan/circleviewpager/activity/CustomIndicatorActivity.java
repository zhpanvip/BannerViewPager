package com.example.zhpan.circleviewpager.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.example.zhpan.circleviewpager.view.FigureIndicatorView;
import com.zhpan.bannerview.constants.IndicatorGravity;
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
                .setPageMargin(DpUtils.dp2px(20))
                .setIndicatorGravity(IndicatorGravity.END)
                .setIndicatorView(setupIndicatorView(mDrawableList.size()))
                .setOnPageClickListener(position -> ToastUtils.show(position + ""))
                .setHolderCreator(ImageResourceViewHolder::new).create(mDrawableList);
    }

    /**
     * 这里可以是自定义的Indicator，需要继承BaseIndicatorView或者实现IIndicator接口;
     */
    private FigureIndicatorView setupIndicatorView(int pageSize) {
        FigureIndicatorView indicatorView = new FigureIndicatorView(this);
        indicatorView.setPageSize(pageSize);
        indicatorView.setRadius(DpUtils.dp2px(18));
        indicatorView.setTextSize(DpUtils.dp2px(13));
        indicatorView.setBackgroundColor(Color.parseColor("#88FF5252"));
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
