package com.example.zhpan.banner.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.SizeUtils
import com.example.zhpan.banner.R
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.bannerview.constants.PageStyle
import com.zhpan.bannerview.manager.BannerOptions
import com.zhpan.indicator.enums.IndicatorOrientation
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

/**
 * @author DBoy
 * @date 2021/1/24
 * Class 描述 : 通用配置
 */
object CommBannerOptions {

    /**
     * 通用
     */
    fun getCommOptionOne(context: Context): BannerOptions {
        return BannerOptions().apply {
            scrollDuration = 600
            offScreenPageLimit = 2
            isCanLoop = true
            indicatorStyle = IndicatorStyle.CIRCLE
            indicatorSlideMode = IndicatorSlideMode.WORM
            interval = 3000
            isAutoPlay = true
            isDisallowParentInterceptDownEvent = true
            indicatorGravity = IndicatorGravity.CENTER
            setIndicatorSliderWidth(context.resources.getDimensionPixelSize(R.dimen.dp_4), context.resources.getDimensionPixelSize(R.dimen.dp_4))
            setIndicatorSliderColor(ContextCompat.getColor(context, R.color.red_normal_color), ContextCompat.getColor(context, R.color.red_checked_color))
        }
    }

    /**
     * 通用垂直
     */
    fun getCommOptionOneVertical(context: Context): BannerOptions {
        return BannerOptions().apply {
            scrollDuration = 600
            offScreenPageLimit = 2
            isCanLoop = true
            orientation = IndicatorOrientation.INDICATOR_VERTICAL
            indicatorStyle = IndicatorStyle.CIRCLE
            indicatorSlideMode = IndicatorSlideMode.WORM
            interval = 3000
            isAutoPlay = true
            isDisallowParentInterceptDownEvent = true
            indicatorGravity = IndicatorGravity.END
            setIndicatorSliderWidth(context.resources.getDimensionPixelSize(R.dimen.dp_4), context.resources.getDimensionPixelSize(R.dimen.dp_4))
            setIndicatorMargin(0, 0, SizeUtils.dp2px(24f), SizeUtils.dp2px(24f))
            setIndicatorSliderColor(ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.red_checked_color))
        }
    }

    /**
     * 通用右边
     */
    fun getCommOptionTwo(): BannerOptions {
        return BannerOptions().apply {
            indicatorGravity = IndicatorGravity.END
            indicatorStyle = IndicatorStyle.ROUND_RECT
            isCanLoop = true
            indicatorSlideMode = IndicatorSlideMode.SCALE
            indicatorGap = SizeUtils.dp2px(4f).toFloat()
            isAutoPlay = true
            pageStyle = PageStyle.MULTI_PAGE_SCALE
            interval = 2000
            scrollDuration = 500
            pageMargin = SizeUtils.dp2px(1f)
            setIndicatorSliderWidth(SizeUtils.dp2px(2f), SizeUtils.dp2px(2f))
            setIndicatorSliderWidth(SizeUtils.dp2px(4f), SizeUtils.dp2px(12f))
            setIndicatorMargin(0, 0, SizeUtils.dp2px(26f), SizeUtils.dp2px(9f))
            setIndicatorSliderColor(Color.parseColor("#66FFFFFF"), Color.parseColor("#FFFFFF"))
            setIndicatorHeight(SizeUtils.dp2px(4f))
        }
    }


}