
package com.example.zhpan.banner.transform

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class DepthScaleTransformer :  ViewPager2.PageTransformer {

    companion object {
        private const val MIN_SCALE = 0.9f
        private const val MIN_ALPHA = 0.7f
    }

    override fun transformPage(page: View, position: Float) {
        page.also {
            if (kotlin.math.abs(position) >= 1f) {
                it.alpha = 0f
                return@transformPage
            }
            val scale = (1 - kotlin.math.abs(position) / 2).coerceAtLeast(MIN_SCALE)
            it.scaleX = scale
            it.scaleY = scale
            it.alpha = (1 - kotlin.math.abs(position)).coerceAtLeast(MIN_ALPHA)
            it.translationX = (1 - scale) * it.width / 2 * if (position > 0) -1 else 1
        }
    }
}