package com.example.zhpan.circleviewpager.adapter;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.zhpan.circleviewpager.fragment.BaseFragment;
import com.example.zhpan.circleviewpager.fragment.HomeFragment;

import java.util.List;

public class AdapterFragmentPager extends FragmentPagerAdapter {


    public static final int PAGE_HOME = 0;

    public static final int PAGE_PUBLISH = 2;


    public static final int PAGE_FIND = 1;


    private SparseArray<BaseFragment> fragmentList;


    public AdapterFragmentPager(FragmentManager fm) {
        super(fm);
        fragmentList = getFragments();
    }

    private SparseArray<BaseFragment> getFragments() {
        SparseArray<BaseFragment> fragmentList = new SparseArray<>();
        fragmentList.put(PAGE_HOME, HomeFragment.getInstance());
        fragmentList.put(PAGE_FIND, HomeFragment.getInstance());
        fragmentList.put(PAGE_PUBLISH, HomeFragment.getInstance());
        return fragmentList;
    }

    public AdapterFragmentPager(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
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
