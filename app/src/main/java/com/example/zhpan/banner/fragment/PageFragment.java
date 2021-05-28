package com.example.zhpan.banner.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.example.zhpan.banner.R;
import com.example.zhpan.banner.adapter.ViewBindingSampleAdapter;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.annotation.APageStyle;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.indicator.enums.IndicatorSlideMode;

/**
 * Created by zhpan on 2018/7/24.
 */
public class PageFragment extends BaseFragment {
  private BannerViewPager<Integer> mViewPager;
  private RadioGroup mRadioGroupPageStyle;

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
  protected void initView(Bundle savedInstanceState, View view) {
    mViewPager = view.findViewById(R.id.banner_view);
    mRadioGroupPageStyle = view.findViewById(R.id.rg_page_style);
    mViewPager
        .setIndicatorSlideMode(IndicatorSlideMode.SCALE)
        .setIndicatorSliderColor(getColor(R.color.red_normal_color),
            getColor(R.color.red_checked_color))
        .setIndicatorSliderRadius(getResources().getDimensionPixelOffset(R.dimen.dp_4),
            getResources().getDimensionPixelOffset(R.dimen.dp_5))
        .setLifecycleRegistry(getLifecycle())
        .setOnPageClickListener(this::pageClick)
        .setAdapter(
            new ViewBindingSampleAdapter(getResources().getDimensionPixelOffset(R.dimen.dp_8)))
        .setInterval(5000);
    initRadioGroup();
    view.findViewById(R.id.rb_multi_page_overlap).performClick();
  }

  private void pageClick(View view, int position) {
    if (position != mViewPager.getCurrentItem()) {
      mViewPager.setCurrentItem(position, true);
    }
    ToastUtils.showShort("position:" + position);
  }

  private void initRadioGroup() {
    mRadioGroupPageStyle.setOnCheckedChangeListener((group, checkedId) -> {
      if (checkedId == R.id.rb_multi_page) {
        setupMultiPageBanner();
      } else if (checkedId == R.id.rb_right_page_reveal) {
        setupRightPageReveal();
      } else if (checkedId == R.id.rb_multi_page_scale) {
        setupBanner(PageStyle.MULTI_PAGE_SCALE);
      } else if (checkedId == R.id.rb_multi_page_overlap) {
        setupBanner(PageStyle.MULTI_PAGE_OVERLAP);
      } else if (checkedId == R.id.rb_netease_music_style) {
        setNetEaseMusicStyle();
      } else if (checkedId == R.id.rb_qq_music_style) {
        setQQMusicStyle();
      }
    });
  }

  private void setupMultiPageBanner() {
    mViewPager
        .setPageMargin(getResources().getDimensionPixelOffset(R.dimen.dp_10))
        .setRevealWidth(getResources().getDimensionPixelOffset(R.dimen.dp_10))
        .create(getPicList(4));
    mViewPager.removeDefaultPageTransformer();
  }

  private void setupRightPageReveal() {
    mViewPager
        .setPageMargin(getResources().getDimensionPixelOffset(R.dimen.dp_10))
        .setRevealWidth(0, getResources().getDimensionPixelOffset(R.dimen.dp_30))
        .create(getPicList(4));

    mViewPager.removeDefaultPageTransformer();
  }

  private void setupBanner(@APageStyle int pageStyle) {
    mViewPager
        .setPageMargin(getResources().getDimensionPixelOffset(R.dimen.dp_15))
        .setRevealWidth(getResources().getDimensionPixelOffset(R.dimen.dp_10))
        .setPageStyle(pageStyle)
        .create(getPicList(4));
  }

  // 网易云音乐样式
  private void setNetEaseMusicStyle() {
    mViewPager
        .setPageMargin(getResources().getDimensionPixelOffset(R.dimen.dp_20))
        .setRevealWidth(getResources().getDimensionPixelOffset(R.dimen.dp_m_10))
        .setIndicatorSliderColor(getColor(R.color.red_normal_color),
            getColor(R.color.red_checked_color))
        .setOnPageClickListener((view, position) -> ToastUtils.showShort("position:" + position))
        .setInterval(5000).create(getPicList(4));
    mViewPager.removeDefaultPageTransformer();
  }

  //  仿QQ音乐的Banner
  private void setQQMusicStyle() {
    mViewPager
        .setPageMargin(getResources().getDimensionPixelOffset(R.dimen.dp_15))
        .setRevealWidth(BannerUtils.dp2px(0))
        .setIndicatorSliderColor(getColor(R.color.red_normal_color),
            getColor(R.color.red_checked_color))
        .setOnPageClickListener((view, position) -> ToastUtils.showShort("position:" + position))
        .setInterval(5000).create(getPicList(4));
    mViewPager.removeDefaultPageTransformer();
  }
}
