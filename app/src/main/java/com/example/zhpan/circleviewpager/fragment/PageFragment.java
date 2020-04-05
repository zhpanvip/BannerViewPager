package com.example.zhpan.circleviewpager.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.adapter.ImageResourceAdapter;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.annotation.APageStyle;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.idea.utils.ToastUtils;
import com.zhpan.indicator.enums.IndicatorSlideMode;

/**
 * Created by zhpan on 2018/7/24.
 */
public class PageFragment extends BaseFragment {
    private BannerViewPager<Integer, ImageResourceViewHolder> mViewPager;
    private RadioGroup mRadioGroupPageStyle;
    private RadioButton radioButton;

    @Override
    protected int getLayout() {
        return R.layout.fragment_find;
    }


    public static PageFragment getInstance() {
        return new PageFragment();
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
        mViewPager = view.findViewById(R.id.banner_view);
        mRadioGroupPageStyle = view.findViewById(R.id.rg_page_style);
        radioButton = view.findViewById(R.id.rb_multi_page);
        mViewPager
                .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setIndicatorSliderColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setIndicatorSliderRadius(getResources().getDimensionPixelOffset(R.dimen.dp_4), getResources().getDimensionPixelOffset(R.dimen.dp_5))
                .setOnPageClickListener(position -> ToastUtils.show("position:" + position))
                .setAdapter(new ImageResourceAdapter().setData(getMDrawableList()))
                .setInterval(5000);
        initRadioGroup();
    }

    private void initRadioGroup() {
        mRadioGroupPageStyle.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_multi_page:
                    setupBanner(PageStyle.MULTI_PAGE);
                    break;
                case R.id.rb_multi_page_scale:
                    setupBanner(PageStyle.MULTI_PAGE_SCALE);
                    break;
                case R.id.rb_multi_page_overlap:
                    setupBanner(PageStyle.MULTI_PAGE_OVERLAP);
                    break;
                case R.id.rb_qq_music_style:
                    setNetEaseMusicStyle();
                    break;
            }
        });
        radioButton.performClick();
    }

    private void setupBanner(@APageStyle int pageStyle) {
        mViewPager
                .setRevealWidth(getResources().getDimensionPixelOffset(R.dimen.dp_10))
                .setPageStyle(pageStyle)
                .create();
    }

    //  仿QQ音乐的Banner
    private void setNetEaseMusicStyle() {
        mViewPager
                .setRevealWidth(BannerUtils.dp2px(0))
                .setPageStyle(PageStyle.MULTI_PAGE)
                .setIndicatorSliderColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setOnPageClickListener(position -> ToastUtils.show("position:" + position))
                .setInterval(5000).create();
    }
}
