package com.example.zhpan.banner.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.SizeUtils
import com.example.zhpan.banner.R
import com.example.zhpan.banner.adapter.ViewBindingSampleAdapter
import com.example.zhpan.banner.utils.CommBannerOptions
import com.zhpan.bannerview.BannerViewPager

/**
 * @author DBoy
 * @date 2021/1/24
 * Class 描述 : 展示更多效果
 */
class MoreEffectFragment : BaseFragment() {

    companion object {
        fun getInstance(): MoreEffectFragment {
            return MoreEffectFragment()
        }
    }

    override val layout: Int
        get() = R.layout.fragment_more_effect

    override fun initTitle() {
        requireActivity().actionBar?.title = "More Effect"
    }

    override fun initView(savedInstanceState: Bundle?, view: View) {
        val bannerOne = view.findViewById<BannerViewPager<Int>>(R.id.banner_more_one)
        initOne(bannerOne)

        val bannerTwo = view.findViewById<BannerViewPager<Int>>(R.id.banner_more_two)
        initTwo(bannerTwo)

        val bannerThree = view.findViewById<BannerViewPager<Int>>(R.id.banner_more_three)
        initThree(bannerThree)

        val bannerFour = view.findViewById<BannerViewPager<Int>>(R.id.banner_more_four)
        initFour(bannerFour)
    }

    private fun initOne(bannerOne: BannerViewPager<Int>) {
        bannerOne.apply {
            setLifecycleRegistry(lifecycle)
            applyBannerOption(CommBannerOptions.getCommOptionOne(requireContext()))
            adapter = ViewBindingSampleAdapter(0)
            create(getPicList())
        }
    }

    private fun initTwo(bannerTwo: BannerViewPager<Int>) {
        bannerTwo.apply {
            setLifecycleRegistry(lifecycle)
            applyBannerOption(CommBannerOptions.getCommOptionOne(requireContext()))
            setOrientation(ViewPager2.ORIENTATION_VERTICAL)
            adapter = ViewBindingSampleAdapter(SizeUtils.dp2px(10f), Rect(16,8,16,8))
            create(getPicList(3))
        }
    }

    private fun initThree(bannerThree: BannerViewPager<Int>) {
        bannerThree.apply {
            setLifecycleRegistry(lifecycle)
            applyBannerOption(CommBannerOptions.getCommOptionTwo())
            setPageMargin(SizeUtils.dp2px(8f))
            setRevealWidth(SizeUtils.dp2px(16f))
            adapter = ViewBindingSampleAdapter(0)
            create(getPicList())
        }
    }

    private fun initFour(bannerFour: BannerViewPager<Int>) {
        bannerFour.apply {
            setLifecycleRegistry(lifecycle)
            applyBannerOption(CommBannerOptions.getCommOptionTwo())
            setPageMargin(SizeUtils.dp2px(8f))
            setRevealWidth(SizeUtils.dp2px(16f))
            adapter = ViewBindingSampleAdapter(SizeUtils.dp2px(10f))
            create(getPicList(3))
        }
    }
}