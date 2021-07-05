
package com.example.zhpan.banner.transform

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class RotateTransformer  : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.also {
            if (abs(position) >= 1f) {
                it.alpha = 0f
                return@transformPage
            }
            val scale = (1 - abs(position)).coerceAtLeast(MIN_SCALE)
            it.scaleX = scale
            it.scaleY = scale
            it.alpha = 1f
            it.translationX = (1 - scale) * it.width / 2 * if (position > 0) -1 else 1
            it.translationZ = if (scale == MIN_SCALE) -1f else 0f
            it.rotation = -position * 45
        }
    }

    companion object{
        private const val MIN_SCALE = 0.5f
    }
}