package com.example.zhpan.circleviewpager.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.annotation.APageStyle;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.indicator.IndicatorView;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.idea.utils.ToastUtils;

/**
 * Created by zhpan on 2018/7/24.
 */
public class PageFragment extends BaseFragment {

    private BannerViewPager<Integer, ImageResourceViewHolder> mViewPager;
    private RadioGroup mRadioGroupPageStyle;
    private IndicatorView indicatorView;
    private RadioButton radioButton;

    @Override
    protected int getLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView(Bundle savedInstanceState, View view) {
        mViewPager = view.findViewById(R.id.banner_view);
        mRadioGroupPageStyle = view.findViewById(R.id.rg_page_style);
        indicatorView = view.findViewById(R.id.indicator_view);
        radioButton = view.findViewById(R.id.rb_multi_page);
        mViewPager
                .setPageMargin(BannerUtils.dp2px(10))
                .setRevealWidth(BannerUtils.dp2px(10))
                .setHolderCreator(() -> new ImageResourceViewHolder(BannerUtils.dp2px(5)))
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setOnPageClickListener(position -> ToastUtils.show("position:" + position))
                .setInterval(5000);
        initRadioGroup();
    }

    public static PageFragment getInstance() {
        return new PageFragment();
    }

    private void setupBanner(@APageStyle int pageStyle) {
        mViewPager
                .setIndicatorVisibility(View.VISIBLE) // 在实际开发中这行代码不必添加，此处因为受到其它两种模式影响所以要隐藏掉内置指示器
                .setPageStyle(pageStyle)
                .create(getMDrawableList());
    }

    private void initRadioGroup() {
        mRadioGroupPageStyle.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_multi_page:
//                    mViewPager.resetIndicator();// 在实际开发中这行代码不必添加，此处因为受到其它两种模式影响所以要隐藏掉内置指示器
                    indicatorView.setVisibility(View.INVISIBLE);// 在实际开发中这行代码不必添加，此处因为受到其它两种模式影响所以要隐藏掉内置指示器
                    setupBanner(PageStyle.MULTI_PAGE);
                    break;
                case R.id.rb_multi_page_scale:
//                    mViewPager.resetIndicator();// 在实际开发中这行代码不必添加，此处因为受到其它两种模式影响所以要隐藏掉内置指示器
                    indicatorView.setVisibility(View.INVISIBLE);// 在实际开发中这行代码不必添加，此处因为受到其它两种模式影响所以要隐藏掉内置指示器
                    setupBanner(PageStyle.MULTI_PAGE_SCALE);
                    break;
                case R.id.rb_multi_page_overlap:
                    indicatorView.setVisibility(View.VISIBLE);// 在实际开发中这行代码不必添加，此处因为受到其它两种模式影响所以要隐藏掉内置指示器
//                    mViewPager.resetIndicator();
                    setupOverlapBanner();
                    break;
            }
        });
        radioButton.performClick();
    }

    private void setupOverlapBanner() {
        mViewPager
                .setIndicatorVisibility(View.GONE) // 在实际开发中这行代码不必添加，此处因为受到其它两种模式影响所以要隐藏掉内置指示器
                .setPageStyle(PageStyle.MULTI_PAGE_OVERLAP)
                .setIndicatorView(indicatorView)
                .create(getMDrawableList());
    }
}
