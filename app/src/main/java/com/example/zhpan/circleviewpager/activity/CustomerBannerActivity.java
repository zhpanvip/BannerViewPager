package com.example.zhpan.circleviewpager.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.bean.CustomBean;
import com.example.zhpan.circleviewpager.viewholder.CustomPageViewHolder;
import com.zhpan.bannerview.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

public class CustomerBannerActivity extends AppCompatActivity {
    //    private String[] picUrls = {"http://pic31.nipic.com/20130801/11604791_100539834000_2.jpg",
//            "http://pic37.nipic.com/20140115/7430301_100825571157_2.jpg",
//            "http://pic29.nipic.com/20130507/8952533_183922555000_2.jpg",
//            "http://b-ssl.duitang.com/uploads/item/201706/10/20170610095055_G5LM8.jpeg"};
    private List<CustomBean> mList = new ArrayList<>();
    private BannerViewPager<CustomBean, CustomPageViewHolder> mViewPager;
    private int[] imgRes = {R.drawable.guide0, R.drawable.guide1, R.drawable.guide2};
    private String[] des = {"在这里\n你可以听到周围人的心声", "在这里\nTA会在下一秒遇见你", "在这里\n不再错过可以改变你一生的人"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_banner);
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
        mViewPager.setAutoPlay(false)
                .setCanLoop(false)
                .setData(mList)
                .showIndicator(false)
                .setOnPageClickListener(position -> Toast.makeText(CustomerBannerActivity.this,
                        "立即体验" + (position + 1), Toast.LENGTH_SHORT).show())
                .setHolderCreator(CustomPageViewHolder::new).create();
    }

    private void initData() {
        for (int i = 0; i < imgRes.length; i++) {
            CustomBean customBean = new CustomBean();
            customBean.setImageRes(imgRes[i]);
            customBean.setImageDescription(des[i]);
            mList.add(customBean);
        }
    }
}
