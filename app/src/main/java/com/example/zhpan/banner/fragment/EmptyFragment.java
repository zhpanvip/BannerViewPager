package com.example.zhpan.banner.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.zhpan.banner.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author zhangpan
 * @date 2020/12/9
 */
public class EmptyFragment extends BaseFragment {

    @Override
    protected int getLayout() {
        return R.layout.fragment_empty;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @NotNull View view) {

    }

    public static EmptyFragment getInstance() {
        return new EmptyFragment();
    }
}
