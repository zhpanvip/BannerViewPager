package com.example.zhpan.circleviewpager.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.bean.CustomBean;
import com.example.zhpan.circleviewpager.viewholder.CustomPageViewHolder;
import com.zhpan.bannerview.holder.HolderCreator;
import com.zhpan.bannerview.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

public class CustomerBannerActivity extends AppCompatActivity {
    private List<CustomBean> mList = new ArrayList<>();
    private BannerViewPager<CustomBean, CustomPageViewHolder> mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_banner);
        setTitle(R.string.title_custom_page);
        initData();
        initViewPager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.stopLoop();
    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAutoPlay(true)
                .setCanLoop(true)
                .setData(mList)
                .setRoundCorner(R.dimen.banner_corner)
                .setIndicatorGravity(BannerViewPager.END)
                .setIndicatorColor(Color.parseColor("#6C6D72"),
                        Color.parseColor("#FFFFFF"))
                .setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
                    @Override
                    public void onPageClick(int position) {
                        Toast.makeText(CustomerBannerActivity.this, "图片" + (position + 1), Toast.LENGTH_SHORT).show();
                    }
                })
                .setHolderCreator(new HolderCreator<CustomPageViewHolder>() {
                    @Override
                    public CustomPageViewHolder createViewHolder() {
                        return new CustomPageViewHolder();
                    }
                }).create();
    }

    private void initData() {
        for (int i = 0; i <= 3; i++) {
            int drawable = getResources().getIdentifier("b" + i, "drawable", getPackageName());
            CustomBean customBean = new CustomBean();
            customBean.setImageRes(drawable);
            customBean.setImageDescription("这是第" + (i + 1) + "张图片的描述");
            mList.add(customBean);
        }
    }
}
