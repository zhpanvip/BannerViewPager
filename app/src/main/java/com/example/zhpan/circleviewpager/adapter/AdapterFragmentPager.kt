package com.example.zhpan.circleviewpager.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.example.zhpan.circleviewpager.fragment.BaseFragment
import com.example.zhpan.circleviewpager.fragment.HomeFragment
import com.example.zhpan.circleviewpager.fragment.IndicatorFragment
import com.example.zhpan.circleviewpager.fragment.OthersFragment
import com.example.zhpan.circleviewpager.fragment.PageFragment

/**
 * <pre>
 * Created by zhangpan on 2019-12-05.
 * Description:
</pre> *
 */
class AdapterFragmentPager(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments: SparseArray<BaseFragment> = SparseArray()

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment
        when (position) {
            PAGE_HOME -> {
                if (fragments.get(PAGE_HOME) == null) {
                    fragment = HomeFragment.getInstance();
                    fragments.put(PAGE_HOME, fragment)
                } else {
                    fragment = fragments.get(PAGE_HOME)
                }
            }
            PAGE_FIND -> {
                if (fragments.get(PAGE_FIND) == null) {
                    fragment = PageFragment.getInstance();
                    fragments.put(PAGE_FIND, fragment)
                } else {
                    fragment = fragments.get(PAGE_FIND)
                }
            }

            PAGE_INDICATOR -> {
                if (fragments.get(PAGE_INDICATOR) == null) {
                    fragment = IndicatorFragment.getInstance();
                    fragments.put(PAGE_INDICATOR, fragment)
                } else {
                    fragment = fragments.get(PAGE_INDICATOR)
                }
            }

            PAGE_OTHERS -> {
                if (fragments.get(PAGE_OTHERS) == null) {
                    fragment = OthersFragment.getInstance();
                    fragments.put(PAGE_OTHERS, fragment)
                } else {
                    fragment = fragments.get(PAGE_OTHERS)
                }
            }
            else -> {
                if (fragments.get(PAGE_HOME) == null) {
                    fragment = HomeFragment.getInstance();
                    fragments.put(PAGE_HOME, fragment)
                } else {
                    fragment = fragments.get(PAGE_HOME)
                }
            }
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 4
    }

    companion object {

        const val PAGE_HOME = 0

        const val PAGE_FIND = 1

        const val PAGE_INDICATOR = 2

        const val PAGE_OTHERS = 3

    }

}
