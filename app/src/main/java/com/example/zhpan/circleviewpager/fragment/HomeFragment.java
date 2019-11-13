package com.example.zhpan.circleviewpager.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.example.zhpan.circleviewpager.R;


/**
 * Created by zhpan on 2018/7/24.
 */
public class HomeFragment extends BaseFragment {
    //    @BindView(R2.id.tv_fragment)
    TextView mTextView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }
}
