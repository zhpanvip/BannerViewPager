package com.example.zhpan.circleviewpager.adapter;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.zhpan.circleviewpager.fragment.BaseFragment;
import com.example.zhpan.circleviewpager.fragment.IndicatorFragment;
import com.example.zhpan.circleviewpager.fragment.PageFragment;
import com.example.zhpan.circleviewpager.fragment.HomeFragment;

public class AdapterFragmentPager extends FragmentPagerAdapter {

    public static final int PAGE_HOME = 0;

    public static final int PAGE_FIND = 1;

    public static final int PAGE_OTHERS = 2;

    private SparseArray<BaseFragment> fragmentList;

    public AdapterFragmentPager(FragmentManager fm) {
        super(fm);
        fragmentList = getFragments();
    }

    private SparseArray<BaseFragment> getFragments() {
        SparseArray<BaseFragment> fragmentList = new SparseArray<>();
        fragmentList.put(PAGE_HOME, HomeFragment.getInstance());
        fragmentList.put(PAGE_FIND, PageFragment.getInstance());
        fragmentList.put(PAGE_OTHERS, IndicatorFragment.getInstance());
        return fragmentList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragmentList.put(position, (BaseFragment) fragment);
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        if (fragmentList != null) {
            return fragmentList.size();
        } else {
            return 0;
        }
    }

}
