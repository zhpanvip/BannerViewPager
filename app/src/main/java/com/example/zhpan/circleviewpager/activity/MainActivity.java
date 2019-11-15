package com.example.zhpan.circleviewpager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.widget.RadioGroup;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.adapter.AdapterFragmentPager;
import com.zhpan.bannerview.view.CatchViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rg_tab)
    RadioGroup rgTab;
    @BindView(R.id.vp_fragment)
    CatchViewPager mViewPager;

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
        mViewPager.disableTouchScroll(true);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rgTab.check(getCheckedId(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private int getCheckedId(int position) {
        int checkedId = R.id.rb_home;
        switch (position) {
            case 0:
                checkedId = R.id.rb_home;
                break;
            case 1:
                checkedId = R.id.rb_find;
                break;
            case 2:
                checkedId = R.id.rb_add;
                break;
        }
        return checkedId;
    }

    private void setListener() {
        rgTab.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_home) {
                mViewPager.setCurrentItem(AdapterFragmentPager.PAGE_HOME, false);

            } else if (checkedId == R.id.rb_find) {
                mViewPager.setCurrentItem(AdapterFragmentPager.PAGE_FIND, false);

            } else if (checkedId == R.id.rb_add) {
                mViewPager.setCurrentItem(AdapterFragmentPager.PAGE_OTHERS, false);
            }
        });
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
