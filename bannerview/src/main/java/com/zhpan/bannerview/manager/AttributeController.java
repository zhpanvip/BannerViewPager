package com.zhpan.bannerview.manager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zhpan.bannerview.R;
import com.zhpan.bannerview.utils.BannerUtils;

import static com.zhpan.bannerview.manager.BannerOptions.DEFAULT_REVEAL_WIDTH;

/**
 * <pre>
 *   Created by zhpan on 2019/11/20.
 *   Description:初始化xml的自定义属性
 * </pre>
 */
public class AttributeController {

    private BannerOptions mBannerOptions;

    public AttributeController(BannerOptions bannerOptions) {
        mBannerOptions = bannerOptions;
    }

    public void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerViewPager);
            initBannerAttrs(typedArray);
            initIndicatorAttrs(typedArray);
            typedArray.recycle();
        }
    }

    private void initIndicatorAttrs(TypedArray typedArray) {
        int indicatorCheckedColor = typedArray.getColor(R.styleable.BannerViewPager_bvp_indicator_checked_color, Color.parseColor("#8C18171C"));
        int indicatorNormalColor = typedArray.getColor(R.styleable.BannerViewPager_bvp_indicator_normal_color, Color.parseColor("#8C6C6D72"));
        int normalIndicatorWidth = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_indicator_radius, BannerUtils.dp2px(8));
        int indicatorGravity = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_gravity, 0);
        int indicatorStyle = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_style, 0);
        int indicatorSlideMode = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_slide_mode, 0);
        int indicatorVisibility = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_visibility, 0);
        mBannerOptions.setIndicatorSliderColor(indicatorNormalColor, indicatorCheckedColor);
        mBannerOptions.setIndicatorSliderWidth(normalIndicatorWidth, normalIndicatorWidth);
        mBannerOptions.setIndicatorGravity(indicatorGravity);
        mBannerOptions.setIndicatorStyle(indicatorStyle);
        mBannerOptions.setIndicatorSlideMode(indicatorSlideMode);
        mBannerOptions.setIndicatorVisibility(indicatorVisibility);
        mBannerOptions.setIndicatorGap(normalIndicatorWidth);
        mBannerOptions.setIndicatorHeight(normalIndicatorWidth / 2);
    }

    private void initBannerAttrs(TypedArray typedArray) {
        int interval = typedArray.getInteger(R.styleable.BannerViewPager_bvp_interval, 3000);
        boolean isAutoPlay = typedArray.getBoolean(R.styleable.BannerViewPager_bvp_auto_play, true);
        boolean isCanLoop = typedArray.getBoolean(R.styleable.BannerViewPager_bvp_can_loop, true);
        int pageMargin = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_page_margin, 0);
        int roundCorner = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_round_corner, 0);
        int revealWidth = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_reveal_width, DEFAULT_REVEAL_WIDTH);
        int pageStyle = typedArray.getInt(R.styleable.BannerViewPager_bvp_page_style, 0);
        int scrollDuration = typedArray.getInt(R.styleable.BannerViewPager_bvp_scroll_duration, 0);
        mBannerOptions.setInterval(interval);
        mBannerOptions.setAutoPlay(isAutoPlay);
        mBannerOptions.setCanLoop(isCanLoop);
        mBannerOptions.setPageMargin(pageMargin);
        mBannerOptions.setRoundRectRadius(roundCorner);
        mBannerOptions.setRightRevealWidth(revealWidth);
        mBannerOptions.setPageStyle(pageStyle);
        mBannerOptions.setScrollDuration(scrollDuration);
    }

}
