package com.example.zhpan.banner.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ToastUtils
import com.example.zhpan.banner.R
import com.example.zhpan.banner.adapter.SimpleAdapter
import com.example.zhpan.banner.bean.CustomBean
import com.example.zhpan.banner.databinding.ActivityWelcomeBinding
import com.example.zhpan.banner.transform.PageTransformerFactory
import com.zhpan.bannerview.BannerViewPager
import com.example.zhpan.banner.transform.TransformerStyle
import com.zhpan.bannerview.utils.BannerUtils
import com.zhpan.indicator.enums.IndicatorSlideMode
import java.util.*

class WelcomeActivity : BaseDataActivity() {

    private lateinit var mViewPager: BannerViewPager<CustomBean>
    private lateinit var binding: ActivityWelcomeBinding

    private val des = arrayOf("在这里\n你可以听到周围人的心声", "在这里\nTA会在下一秒遇见你", "在这里\n不再错过可以改变你一生的人")

    private val transforms = intArrayOf(
        TransformerStyle.NONE,
        TransformerStyle.ACCORDION,
        TransformerStyle.DEPTH,
        TransformerStyle.DEPTH_SCALE,
        TransformerStyle.ROTATE,
        TransformerStyle.SCALE_IN,
        TransformerStyle.ROTATE_UP
    )

    private val data: List<CustomBean>
        get() {
            val list = ArrayList<CustomBean>()
            for (i in mDrawableList.indices) {
                val customBean = CustomBean()
                customBean.imageRes = mDrawableList[i]
                customBean.imageDescription = des[i]
                list.add(customBean)
            }
            return list
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewPager()
        updateUI(0)
    }

    private fun setupViewPager() {
        mViewPager = findViewById(R.id.viewpager)
        mViewPager.apply {
            setCanLoop(false)
            setPageTransformer(
                PageTransformerFactory.createPageTransformer(transforms[Random().nextInt(7)])
            )
            setIndicatorMargin(
                0, 0, 0, resources.getDimension(R.dimen.dp_100)
                    .toInt()
            )
            setIndicatorSliderGap(
                resources.getDimension(R.dimen.dp_10)
                    .toInt()
            )
            setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
            setIndicatorSliderRadius(
                resources.getDimension(R.dimen.dp_3)
                    .toInt(), resources.getDimension(R.dimen.dp_4_5)
                    .toInt()
            )
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    BannerUtils.log("position:$position")
                    updateUI(position)
                }
            })
            adapter = SimpleAdapter().apply {
                mOnSubViewClickListener = object : SimpleAdapter.OnSubViewClickListener {
                    override fun onViewClick(
                        view: View?,
                        position: Int
                    ) {
                        ToastUtils.showShort("Logo Clicked,position:$position")
                    }
                }
            }
            setIndicatorSliderColor(
                ContextCompat.getColor(this@WelcomeActivity, R.color.white),
                ContextCompat.getColor(this@WelcomeActivity, R.color.white_alpha_75)
            )
        }
            .create(data)
    }

    fun onClick(view: View) {
        MainActivity.start(this)
        finish()
    }

    private fun updateUI(position: Int) {
        binding.tvDescribe?.text = des[position]
        val translationAnim = ObjectAnimator.ofFloat(binding.tvDescribe, "translationX", -120f, 0f)
        translationAnim.apply {
            duration = ANIMATION_DURATION.toLong()
            interpolator = DecelerateInterpolator()
        }
        val alphaAnimator = ObjectAnimator.ofFloat(binding.tvDescribe, "alpha", 0f, 1f)
        alphaAnimator.apply {
            duration = ANIMATION_DURATION.toLong()
        }
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationAnim, alphaAnimator)
        animatorSet.start()

        if (position == mViewPager.data.size - 1 && binding.btnStart.visibility == View.GONE) {
            binding.btnStart.visibility = View.VISIBLE
            ObjectAnimator
                .ofFloat(binding.btnStart, "alpha", 0f, 1f)
                .setDuration(ANIMATION_DURATION.toLong())
                .start()
        } else {
            binding.btnStart.visibility = View.GONE
        }
    }

    companion object {
        private const val ANIMATION_DURATION = 1300
        private const val MIN_SCALE = 0.9f
        private const val MIN_ALPHA = 0.7f
    }
}
