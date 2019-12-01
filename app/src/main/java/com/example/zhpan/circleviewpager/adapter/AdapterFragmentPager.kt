package com.example.zhpan.circleviewpager.adapter

import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.zhpan.circleviewpager.fragment.*

class AdapterFragmentPager(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragmentList: SparseArray<BaseFragment>?

    private val fragments: SparseArray<BaseFragment>
        get() {
            val fragmentList = SparseArray<BaseFragment>()
            fragmentList.put(PAGE_HOME, HomeFragment.getInstance())
            fragmentList.put(PAGE_FIND, PageFragment.getInstance())
            fragmentList.put(PAGE_INDICATOR, IndicatorFragment.getInstance())
            fragmentList.put(PAGE_OTHERS, OthersFragment.getInstance())
            return fragmentList
        }

    init {
        fragmentList = fragments
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        fragmentList?.put(position, fragment as BaseFragment)
        return fragment
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList!!.get(position)
    }

    override fun getCount(): Int {
        return fragmentList?.size() ?: 0
    }

    companion object {

        const val PAGE_HOME = 0

        const val PAGE_FIND = 1

        const val PAGE_INDICATOR = 2

        const val PAGE_OTHERS = 3

    }

}
