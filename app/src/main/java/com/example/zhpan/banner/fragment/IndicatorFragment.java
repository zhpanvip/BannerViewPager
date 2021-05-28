package com.example.zhpan.banner.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.example.zhpan.banner.R;
import com.example.zhpan.banner.adapter.ViewBindingSampleAdapter;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.indicator.annotation.AIndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

/**
 * Created by zhpan on 2018/7/24.
 */
public class IndicatorFragment extends BaseFragment {

  private BannerViewPager<Integer> mViewPager;
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
  protected void initView(Bundle savedInstanceState, View view) {
    radioButton = view.findViewById(R.id.rb_circle);
    mRadioGroupStyle = view.findViewById(R.id.rg_indicator_style);
    mRadioGroupMode = view.findViewById(R.id.rg_slide_mode);
    mViewPager = view.findViewById(R.id.banner_view);
    mViewPager.setIndicatorSliderGap(BannerUtils.dp2px(6))
        .setScrollDuration(800)
        .setLifecycleRegistry(getLifecycle())
        .setIndicatorGravity(IndicatorGravity.CENTER)
        .setOnPageClickListener(
            (clickedView, position) -> ToastUtils.showShort("position:" + position))
        .setAdapter(
            new ViewBindingSampleAdapter(getResources().getDimensionPixelOffset(R.dimen.dp_8)))
        .create();
    initRadioGroup();
  }

  private void initRadioGroup() {
    mRadioGroupStyle.setOnCheckedChangeListener(
        (group, checkedId) -> checkedChange(mCheckId = checkedId));
    radioButton.performClick();
    mRadioGroupMode.setOnCheckedChangeListener((group, checkedId) -> {
      if (checkedId == R.id.rb_normal) {
        mSlideMode = IndicatorSlideMode.NORMAL;
      } else if (checkedId == R.id.rb_worm) {
        mSlideMode = IndicatorSlideMode.WORM;
      } else if (checkedId == R.id.rb_smooth) {
        mSlideMode = IndicatorSlideMode.SMOOTH;
      } else if (checkedId == R.id.rb_color) {
        mSlideMode = IndicatorSlideMode.COLOR;
      } else if (checkedId == R.id.rb_scale) {
        mSlideMode = IndicatorSlideMode.SCALE;
      }
      checkedChange(mCheckId);
    });
  }

  private void checkedChange(int checkedId) {
    if (checkedId == R.id.rb_circle) {
      setupCircleIndicator();
    } else if (checkedId == R.id.rb_dash) {
      setupDashIndicator();
    } else if (checkedId == R.id.rb_round_rect) {
      setupRoundRectIndicator();
    }
  }

  private void setupRoundRectIndicator() {
    int checkedWidth = getResources().getDimensionPixelOffset(R.dimen.dp_10);
    int normalWidth = getNormalWidth();
    mViewPager.setIndicatorStyle(IndicatorStyle.ROUND_RECT)
        .setIndicatorSliderGap(BannerUtils.dp2px(4))
        .setIndicatorSlideMode(mSlideMode)
        .setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.dp_4))
        .setIndicatorSliderColor(getColor(R.color.red_normal_color),
            getColor(R.color.red_checked_color))
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
        .setIndicatorSliderColor(getColor(R.color.red_normal_color),
            getColor(R.color.red_checked_color)).refreshData(getPicList(4));
  }

  private void setupDashIndicator() {
    int checkedWidth = getResources().getDimensionPixelOffset(R.dimen.dp_10);
    int normalWidth = getNormalWidth();
    mViewPager.setIndicatorStyle(IndicatorStyle.DASH)
        .setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.dp_3))
        .setIndicatorSlideMode(mSlideMode)
        .setIndicatorSliderGap(getResources().getDimensionPixelOffset(R.dimen.dp_3))
        .setIndicatorSliderWidth(normalWidth, checkedWidth)
        .setIndicatorSliderColor(getColor(R.color.red_normal_color),
            getColor(R.color.red_checked_color))
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
