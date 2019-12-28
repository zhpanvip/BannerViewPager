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

    init {
        fragments.put(PAGE_HOME, HomeFragment.getInstance())
        fragments.put(PAGE_FIND, PageFragment.getInstance())
        fragments.put(PAGE_INDICATOR, IndicatorFragment.getInstance())
        fragments.put(PAGE_OTHERS, OthersFragment.getInstance())
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size()
    }

    companion object {

        const val PAGE_HOME = 0

        const val PAGE_FIND = 1

        const val PAGE_INDICATOR = 2

        const val PAGE_OTHERS = 3

    }

}
