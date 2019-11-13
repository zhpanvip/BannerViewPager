package com.example.zhpan.circleviewpager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.widget.RadioGroup;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.adapter.AdapterFragmentPager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rg_tab)
    RadioGroup rgTab;
    @BindView(R.id.vp_fragment)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        setListener();
    }

    private void initData() {
        AdapterFragmentPager mAdapter = new AdapterFragmentPager(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    private void setListener() {
        rgTab.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_home) {
                mViewPager.setCurrentItem(AdapterFragmentPager.PAGE_HOME, false);

            } else if (checkedId == R.id.rb_find) {
                mViewPager.setCurrentItem(AdapterFragmentPager.PAGE_FIND, false);

            } else if (checkedId == R.id.rb_add) {
                mViewPager.setCurrentItem(AdapterFragmentPager.PAGE_PUBLISH, false);
            }
        });
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

}
