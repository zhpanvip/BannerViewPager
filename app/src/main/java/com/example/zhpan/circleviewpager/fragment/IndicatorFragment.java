package com.example.zhpan.circleviewpager.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.idea.utils.ToastUtils;
import com.zhpan.indicator.annotation.AIndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

/**
 * Created by zhpan on 2018/7/24.
 */
public class IndicatorFragment extends BaseFragment {

    private BannerViewPager<Integer, ImageResourceViewHolder> mViewPager;
    private RadioGroup mRadioGroupStyle;
    private RadioGroup mRadioGroupMode;
    private RadioButton radioButton;
    private @AIndicatorSlideMode
    int mSlideMode = IndicatorSlideMode.SMOOTH;
    private int mCheckId = R.id.rb_circle;

    @Override
    protected int getLayout() {
        return R.layout.fragment_indicator;
    }

    public static IndicatorFragment getInstance() {
        return new IndicatorFragment();
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
        radioButton = view.findViewById(R.id.rb_circle);
        mRadioGroupStyle = view.findViewById(R.id.rg_indicator_style);
        mRadioGroupMode = view.findViewById(R.id.rg_slide_mode);
        mViewPager = view.findViewById(R.id.banner_view);
        mViewPager.setIndicatorSliderGap(BannerUtils.dp2px(6))
                .setScrollDuration(800)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setOnPageClickListener(position -> ToastUtils.show("position:" + position))
                .setAdapter(new BaseBannerAdapter<Integer, ImageResourceViewHolder>() {
                    @Override
                    protected void onBind(ImageResourceViewHolder holder, Integer data, int position, int pageSize) {
                        holder.bindData(data, position, pageSize);
                    }

                    @Override
                    public ImageResourceViewHolder createViewHolder(View itemView, int viewType) {
                        return new ImageResourceViewHolder(itemView, getResources().getDimensionPixelOffset(R.dimen.dp_8));
                    }

                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_page_indicator;
                    }
                }).create();
        initRadioGroup();
        mViewPager.setUserInputEnabled(true);
    }


    private void initRadioGroup() {
        mRadioGroupStyle.setOnCheckedChangeListener((group, checkedId) -> checkedChange(mCheckId = checkedId));
        radioButton.performClick();
        mRadioGroupMode.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_normal:
                    mSlideMode = IndicatorSlideMode.NORMAL;
                    break;
                case R.id.rb_worm:
                    mSlideMode = IndicatorSlideMode.WORM;
                    break;
                case R.id.rb_smooth:
                    mSlideMode = IndicatorSlideMode.SMOOTH;
                    break;
                case R.id.rb_color:
                    mSlideMode = IndicatorSlideMode.COLOR;
                    break;
                case R.id.rb_scale:
                    mSlideMode = IndicatorSlideMode.SCALE;
                    break;
                default:
                    break;
            }
            checkedChange(mCheckId);
        });
    }

    private void checkedChange(int checkedId) {
        switch (checkedId) {
            case R.id.rb_circle:
                setupCircleIndicator();
                break;
            case R.id.rb_dash:
                setupDashIndicator();
                break;
            case R.id.rb_round_rect:
                setupRoundRectIndicator();
                break;
            default:
                break;
        }
    }

    private void setupRoundRectIndicator() {
        int checkedWidth = getResources().getDimensionPixelOffset(R.dimen.dp_10);
        int normalWidth = getNormalWidth();
        mViewPager.setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                .setIndicatorSliderGap(BannerUtils.dp2px(4))
                .setIndicatorSlideMode(mSlideMode)
                .setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.dp_4))
                .setIndicatorSliderColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setIndicatorSliderWidth(normalWidth, checkedWidth).refreshData(getPicList(4));
    }

    private void setupCircleIndicator() {
        int checkedWidth;
        int normalWidth;
        if (mSlideMode == IndicatorSlideMode.SCALE) {
            checkedWidth = getResources().getDimensionPixelOffset(R.dimen.dp_5);
            normalWidth = getResources().getDimensionPixelOffset(R.dimen.dp_4);
        } else {
            checkedWidth = getResources().getDimensionPixelOffset(R.dimen.dp_4);
            normalWidth = checkedWidth;
        }

        mViewPager.setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(mSlideMode)
                .setIndicatorSliderGap(getResources().getDimensionPixelOffset(R.dimen.dp_6))
                .setIndicatorSliderRadius(normalWidth, checkedWidth)
                .setIndicatorSliderColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color)).refreshData(getPicList(4));
    }

    private void setupDashIndicator() {
        int checkedWidth = getResources().getDimensionPixelOffset(R.dimen.dp_10);
        int normalWidth = getNormalWidth();
        mViewPager.setIndicatorStyle(IndicatorStyle.DASH)
                .setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.dp_3))
                .setIndicatorSlideMode(mSlideMode)
                .setIndicatorSliderGap(getResources().getDimensionPixelOffset(R.dimen.dp_3))
                .setIndicatorSliderWidth(normalWidth, checkedWidth)
                .setIndicatorSliderColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .refreshData(getPicList(4));
    }

    private int getNormalWidth() {
        if (mSlideMode == IndicatorSlideMode.SMOOTH || mSlideMode == IndicatorSlideMode.WORM
                || mSlideMode == IndicatorSlideMode.COLOR) {
            return getResources().getDimensionPixelOffset(R.dimen.dp_10);
        } else {
            return getResources().getDimensionPixelOffset(R.dimen.dp_4);
        }
    }
}
