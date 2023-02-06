package com.example.zhpan.banner.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import com.blankj.utilcode.util.ToastUtils
import com.example.zhpan.banner.R
import com.example.zhpan.banner.R.color
import com.example.zhpan.banner.R.dimen
import com.example.zhpan.banner.adapter.ViewBindingSampleAdapter
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.annotation.APageStyle
import com.zhpan.bannerview.constants.PageStyle
import com.zhpan.bannerview.utils.BannerUtils
import com.zhpan.indicator.enums.IndicatorSlideMode

/**
 * Created by zhpan on 2018/7/24.
 */
class PageFragment : BaseFragment() {
  private lateinit var mViewPager: BannerViewPager<Int>
  private lateinit var mRadioGroupPageStyle: RadioGroup
  private lateinit var mRadioGroupMoreStyle: RadioGroup
  override val layout: Int
    get() = R.layout.fragment_find

  override fun initTitle() {}
  override fun initView(savedInstanceState: Bundle?, view: View) {
    mViewPager = view.findViewById(R.id.banner_view)
    mRadioGroupPageStyle = view.findViewById(R.id.rg_page_style)
    mRadioGroupMoreStyle = view.findViewById(R.id.rg_more_page_style)
    view.findViewById<Button>(R.id.btn_next).setOnClickListener {
      mViewPager.currentItem = mViewPager.currentItem + 1
      itemClick(mViewPager.currentItem)
    }
    view.findViewById<Button>(R.id.btn_pre).setOnClickListener {
      mViewPager.currentItem = mViewPager.currentItem - 1
      itemClick(mViewPager.currentItem)
    }
    initBVP()
    initRadioGroup()
    view.findViewById<View>(R.id.rb_multi_page_overlap).performClick()
  }

  private fun initBVP() {
    mViewPager.apply {
      registerLifecycleObserver(lifecycle)
      adapter = ViewBindingSampleAdapter(resources.getDimensionPixelOffset(dimen.dp_8))
      setIndicatorSlideMode(IndicatorSlideMode.SCALE)
      setIndicatorSliderColor(
        getColor(color.red_normal_color),
        getColor(color.red_checked_color)
      )
      setIndicatorSliderRadius(
        resources.getDimensionPixelOffset(dimen.dp_4),
        resources.getDimensionPixelOffset(dimen.dp_5)
      )
        .setAutoPlay(false)
      setOnPageClickListener({ _: View, position: Int -> itemClick(position) }, true)
      setInterval(5000)
    }
  }

  private fun initRadioGroup() {
    mRadioGroupPageStyle.setOnCheckedChangeListener { _: RadioGroup?, checkedId: Int ->
      when (checkedId) {
        R.id.rb_multi_page_overlap ->
          setupBanner(
            PageStyle.MULTI_PAGE_OVERLAP,
            resources.getDimensionPixelOffset(dimen.dp_10)
          )
        R.id.rb_multi_page_overlap1 ->
          setupBanner(
            PageStyle.MULTI_PAGE_OVERLAP,
            resources.getDimensionPixelOffset(dimen.dp_100)
          )
        R.id.rb_multi_page_scale ->
          setupBanner(
            PageStyle.MULTI_PAGE_SCALE,
            resources.getDimensionPixelOffset(dimen.dp_10)
          )
        R.id.rb_multi_scale_page2 ->
          setupBanner(
            PageStyle.MULTI_PAGE_SCALE,
            resources.getDimensionPixelOffset(dimen.dp_120)
          )
        R.id.rb_multi_scale_page3 -> {
          setupBanner(
            PageStyle.MULTI_PAGE_SCALE,
            resources.getDimensionPixelOffset(dimen.dp_0),
            resources.getDimensionPixelOffset(dimen.dp_200)
          )
        }
      }
    }
    mRadioGroupMoreStyle.setOnCheckedChangeListener { _: RadioGroup?, checkedId: Int ->
      when (checkedId) {
        R.id.rb_multi_page3 ->
          setupMultiPageBanner()
        R.id.rb_multi_page4 ->
          setupRightPageReveal()
        R.id.rb_netease_music_style ->
          setNetEaseMusicStyle()
        R.id.rb_qq_music_style ->
          setQQMusicStyle()
      }
    }
  }

  /**
   * Different page styles can be implement by use [BannerViewPager.setPageStyle] and
   * [BannerViewPager.setRevealWidth]
   *
   * @param pageStyle Optional params [PageStyle.MULTI_PAGE_SCALE] and [PageStyle.MULTI_PAGE_OVERLAP]
   * @param revealWidth In the multi-page mode, The exposed width of the items on the left and right sides
   */
  private fun setupBanner(@APageStyle pageStyle: Int, revealWidth: Int) {
    setupBanner(pageStyle, revealWidth, revealWidth)
  }

  private fun setupBanner(
    @APageStyle pageStyle: Int,
    leftRevealWidth: Int,
    rightRevealWidth: Int
  ) {
    mViewPager
      .setPageMargin(resources.getDimensionPixelOffset(dimen.dp_15))
      // .setScrollDuration(800)
      .setRevealWidth(leftRevealWidth, rightRevealWidth)
      .setPageStyle(pageStyle)
      .create(getPicList(4))
  }

  /**
   * Multi Page Style 1
   */
  private fun setupMultiPageBanner() {
    mViewPager
      .setPageMargin(resources.getDimensionPixelOffset(dimen.dp_10))
      .setRevealWidth(resources.getDimensionPixelOffset(dimen.dp_10))
      .create(getPicList(4))
    mViewPager.removeDefaultPageTransformer()
  }

  /**
   * Multi Page Style 2
   */
  private fun setupRightPageReveal() {
    mViewPager
      .setPageMargin(resources.getDimensionPixelOffset(dimen.dp_10))
      .setRevealWidth(0, resources.getDimensionPixelOffset(dimen.dp_30))
      .create(getPicList(4))
    mViewPager.removeDefaultPageTransformer()
  }

  /**
   * QQ Music Banner Style
   */
  private fun setNetEaseMusicStyle() {
    mViewPager
      .setPageMargin(resources.getDimensionPixelOffset(dimen.dp_20))
      .setRevealWidth(resources.getDimensionPixelOffset(dimen.dp_m_10))
      .setIndicatorSliderColor(
        getColor(color.red_normal_color),
        getColor(color.red_checked_color)
      )
      .setOnPageClickListener { view: View?, position: Int ->
        ToastUtils.showShort(
          "position:$position"
        )
      }
      .setInterval(5000).create(getPicList(4))
    mViewPager.removeDefaultPageTransformer()
  }

  /**
   * NetEase Music Banner Style
   */
  private fun setQQMusicStyle() {
    mViewPager
      .setPageMargin(resources.getDimensionPixelOffset(dimen.dp_15))
      .setRevealWidth(BannerUtils.dp2px(0f))
      .setIndicatorSliderColor(
        getColor(color.red_normal_color),
        getColor(color.red_checked_color)
      )
      .setOnPageClickListener { _: View?, position: Int ->
        ToastUtils.showShort(
          "position:$position"
        )
      }
      .setInterval(5000).create(getPicList(4))
    mViewPager.removeDefaultPageTransformer()
  }

  private fun itemClick(position: Int) {
    ToastUtils.showShort("position:$position")
  }

  companion object {
    val instance: PageFragment
      get() = PageFragment()
  }
}