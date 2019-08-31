package com.example.zhpan.circleviewpager.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.bean.CustomBean;
import com.example.zhpan.circleviewpager.viewholder.CustomPageViewHolder;
import com.zhpan.bannerview.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

public class CustomerBannerActivity extends AppCompatActivity {
    private List<CustomBean> mList = new ArrayList<>();
    private BannerViewPager<CustomBean, CustomPageViewHolder> mViewPager;
    private String[] picUrls = {"http://pic31.nipic.com/20130801/11604791_100539834000_2.jpg",
            "http://pic37.nipic.com/20140115/7430301_100825571157_2.jpg",
            "http://pic29.nipic.com/20130507/8952533_183922555000_2.jpg",
            "http://b-ssl.duitang.com/uploads/item/201706/10/20170610095055_G5LM8.jpeg"};
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
                .setRoundCorner(R.dimen.banner_corner)
                .setIndicatorGravity(BannerViewPager.END)
                .setIndicatorColor(Color.parseColor("#6C6D72"),
                        Color.parseColor("#FFFFFF"))
                .setOnPageClickListener(position -> Toast.makeText(CustomerBannerActivity.this,
                        "图片" + (position + 1), Toast.LENGTH_SHORT).show())
                .setHolderCreator(CustomPageViewHolder::new).create(mList);
    }

    private void initData() {

        for (int i = 0; i < picUrls.length; i++) {
            CustomBean customBean = new CustomBean();
            customBean.setImgUrl(picUrls[i]);
            customBean.setImageDescription("这是第" + (i + 1) + "张图片的描述");
            mList.add(customBean);
        }
    }
}
