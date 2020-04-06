package com.zhpan.bannerview.transform

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max

/**
 * @param: gives veiwpager2 orientation
 * Source from：https://github.com/KoderLabs/finite-cover-flow/blob/dev/lib/src/main/java/com/saeed/finiteflow/lib/OverlapPageTransformert
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class OverlapPageTransformer(
    private val orientation: Int,
    private val minScale: Float = 0f,
    private val unSelectedItemRotation: Float = 0f,
    private val unSelectedItemAlpha: Float = 0f,
    private val itemGap: Float = 0f
) : ViewPager2.PageTransformer {

    init {
        require(minScale in 0f..1f) { "minScale value should be between 1.0 to 0.0" }
        require(unSelectedItemAlpha in 0f..1f) { "unSelectedItemAlpha value should be between 1.0 to 0.0" }
    }

    private val scalingValue = 0.2f

    override fun transformPage(page: View, position: Float) {
        page.apply {
            elevation = -abs(position)

            val delta = max(1f - abs(position * (1 - 0.5f)), 0.5f)

            if (unSelectedItemRotation != 0f) {
                val rotation =
                    (1 - delta) * if (position > 0) unSelectedItemRotation else -unSelectedItemRotation

                rotationY = rotation
            }

            val scaleDelta = abs(position * scalingValue)
            val scale = max(1f - scaleDelta, minScale)

            scaleX = scale
            scaleY = scale

            when (orientation) {
                ViewPager2.ORIENTATION_HORIZONTAL -> {
                    translationX =
                        position * (itemGap.toInt() / 2).toPx() +
                                if (position > 0) {
                                    (-width * (1f - scale))
                                } else {
                                    (width * (1f - scale))
                                }
                }
                ViewPager2.ORIENTATION_VERTICAL -> {
                    translationY = position * (itemGap.toInt()).toPx() +
                            if (position > 0) {
                                (-width * (1f - scale))
                            } else {
                                (width * (1f - scale))
                            }
                }
                else -> throw IllegalArgumentException("Gives correct orientation value, ViewPager2.ORIENTATION_HORIZONTAL or ViewPager2.ORIENTATION_VERTICAL")
            }

            if (unSelectedItemAlpha != 1f) {
                alpha = when {
                    position >= -1f && position <= 1f -> { // (0, 1]
                        // page move from right to center.
                        0.5f + ((1 - abs(position)) * 0.5f)
                    }
                    else -> {
                        0.5f / abs(position * position)

                    }
                }
            }
        }
    }

}