
package com.example.zhpan.banner.transform

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class DepthPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        page.also {
            if (kotlin.math.abs(position) >= 1f) {
                it.alpha = 0f
                return@transformPage
            }
            if (position > 0) {
                it.alpha = 1 - position
                val scale = 1f - position / 4f
                it.scaleX = scale
                it.scaleY = scale
                it.translationX = -it.width * position
                it.translationZ = -1f
            } else {
                it.alpha = 1f
                it.scaleX = 1f
                it.scaleY = 1f
                it.translationX = 0f
                it.translationZ = 0f
            }
        }
    }
}