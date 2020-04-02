package com.example.zhpan.circleviewpager.activity


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.zhpan.circleviewpager.R
import com.example.zhpan.circleviewpager.bean.CustomBean
import com.example.zhpan.circleviewpager.viewholder.CustomPageViewHolder
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.TransformerStyle
import com.zhpan.bannerview.holder.HolderCreator2
import com.zhpan.bannerview.utils.BannerUtils
import com.zhpan.indicator.enums.IndicatorSlideMode
import kotlinx.android.synthetic.main.activity_welcome.*
import java.util.*

class WelcomeActivity : BaseDataActivity(), HolderCreator2<CustomPageViewHolder> {

    private lateinit var mViewPager: BannerViewPager<CustomBean, CustomPageViewHolder>

    private val des = arrayOf("在这里\n你可以听到周围人的心声", "在这里\nTA会在下一秒遇见你", "在这里\n不再错过可以改变你一生的人")

    private val transforms = intArrayOf(TransformerStyle.NONE, TransformerStyle.ACCORDION, TransformerStyle.STACK, TransformerStyle.DEPTH, TransformerStyle.ROTATE, TransformerStyle.SCALE_IN)

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
        setContentView(R.layout.activity_welcome)
        setupViewPager()
        updateUI(0)
        mViewPager.create(data)
    }

    private fun setupViewPager() {
        mViewPager = findViewById(R.id.viewpager)
        mViewPager.setAutoPlay(true)
                .setCanLoop(true)
                .setPageTransformerStyle(transforms[Random().nextInt(6)])
                .setScrollDuration(ANIMATION_DURATION)
                .setIndicatorMargin(0, 0, 0, resources.getDimension(R.dimen.dp_100).toInt())
                .setIndicatorSliderGap(resources.getDimension(R.dimen.dp_10).toInt())
                .setIndicatorSliderColor(ContextCompat.getColor(this, R.color.white),
                        ContextCompat.getColor(this, R.color.white_alpha_75))
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorSliderRadius(resources.getDimension(R.dimen.dp_3).toInt(), resources.getDimension(R.dimen.dp_4_5).toInt())
                .setOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        BannerUtils.log("position:$position")
                        updateUI(position)
                    }
                })
                .setHolderCreator(this)
                .create(data)
    }

    fun onClick(view: View) {
        MainActivity.start(this)
        finish()
    }

    private fun updateUI(position: Int) {
        tv_describe?.text = des[position]
        val translationAnim = ObjectAnimator.ofFloat(tv_describe, "translationX", -120f, 0f)
        translationAnim.duration = ANIMATION_DURATION.toLong()
        translationAnim.interpolator = DecelerateInterpolator()
        val alphaAnimator1 = ObjectAnimator.ofFloat(tv_describe, "alpha", 0f, 1f)
        alphaAnimator1.duration = ANIMATION_DURATION.toLong()
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationAnim, alphaAnimator1)
        animatorSet.start()

        if (position == mViewPager.list.size - 1 && btn_start?.visibility == View.GONE) {
            btn_start?.visibility = View.VISIBLE
            ObjectAnimator
                    .ofFloat(btn_start, "alpha", 0f, 1f)
                    .setDuration(ANIMATION_DURATION.toLong()).start()
        } else {
            btn_start?.visibility = View.GONE
        }
    }

    override fun createViewHolder(): CustomPageViewHolder {
        var itemView = layoutInflater.inflate(R.layout.item_custom_view, mViewPager, false)
        val customPageViewHolder = CustomPageViewHolder(itemView)
        customPageViewHolder.setOnSubViewClickListener { _, position ->
            Toast.makeText(this, "Logo Clicked Item: $position,currentItem:${mViewPager.currentItem}", Toast.LENGTH_SHORT).show()
        }
        return customPageViewHolder
    }

    companion object {

        private const val ANIMATION_DURATION = 1300
    }

    override fun bindViewHolder(holder: CustomPageViewHolder, position: Int) {
        var imageView =holder.itemView.findViewById<ImageView>(R.id.banner_image);
        var mImageStar=holder.itemView.findViewById<ImageView>(R.id.iv_logo)

        imageView.setImageResource(R.drawable.bg_card0)
        //        ImageView mImageView = itemView.findViewById(R.id.banner_image);
//        ImageView  mImageStart = itemView.findViewById(R.id.iv_logo);
//
//        mImageView.setImageResource(data.getImageRes());
//        mImageStart.setOnClickListener(view -> {
//            if (null != mOnSubViewClickListener)
//                mOnSubViewClickListener.onViewClick(view, position);
//        });
//        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mImageStart, "alpha", 0, 1);
//        alphaAnimator.setDuration(1500);
//        alphaAnimator.start();

    }
}
