package com.example.zhpan.circleviewpager.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.annotation.APageStyle;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.indicator.CircleIndicatorView;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.idea.utils.ToastUtils;

import butterknife.BindView;

/**
 * Created by zhpan on 2018/7/24.
 */
public class PageFragment extends BaseFragment {

    @BindView(R.id.banner_view)
    BannerViewPager<Integer, ImageResourceViewHolder> mViewPager;
    @BindView(R.id.rg_page_style)
    RadioGroup mRadioGroupPageStyle;
    @BindView(R.id.indicator_view)
    CircleIndicatorView indicatorView;
    @BindView(R.id.rb_multi_page)
    RadioButton radioButton;

    @Override
    protected int getLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView(Bundle savedInstanceState, View view) {
        mViewPager
                .setPageMargin(BannerUtils.dp2px(10))
                .setRevealWidth(BannerUtils.dp2px(10))
                .setHolderCreator(() -> new ImageResourceViewHolder(BannerUtils.dp2px(5)))
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setOnPageClickListener(position -> ToastUtils.show("position:" + position))
                .setInterval(5000);
        initRadioGroup();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static PageFragment getInstance() {
        return new PageFragment();
    }

    private void setupBanner(@APageStyle int pageStyle) {
        mViewPager
                .setIndicatorVisibility(View.VISIBLE) // 在实际开发中这行代码不必添加，此处因为受到其它两种模式影响所以要隐藏掉内置指示器
                .setPageStyle(pageStyle)
                .create(mDrawableList);
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
                .create(mDrawableList);
    }

    @Override
    public void onStop() {
        if (mViewPager != null)
            mViewPager.stopLoop();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mViewPager != null)
            mViewPager.startLoop();
    }
}
