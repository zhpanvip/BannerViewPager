package com.example.zhpan.circleviewpager.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * MVC模式的Base fragment
 */
public abstract class BaseFragment extends RxFragment {
    protected List<Integer> mDrawableList = new ArrayList<>();
    protected Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context.getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        initData();
        initTitle();
        initView(savedInstanceState, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void initData() {
        for (int i = 0; i < 4; i++) {
            int drawable = getResources().getIdentifier("t" + i, "drawable", mContext.getPackageName());
            mDrawableList.add(drawable);
        }
    }

    protected @ColorInt
    int getColor(@ColorRes int colorRes) {
        return getContext().getResources().getColor(colorRes);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayout();

    /**
     * 初始化标题
     */
    protected abstract void initTitle();

    /**
     * 初始化数据
     */
    protected abstract void initView(Bundle savedInstanceState, View view);

}
