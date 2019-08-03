package com.example.zhpan.circleviewpager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.bean.DataBean;
import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.DataViewHolder;
import com.zhpan.bannerview.holder.HolderCreator;
import com.zhpan.bannerview.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    private BannerViewPager<DataBean, DataViewHolder> mViewpager;
    private List<DataBean> mDataList = new ArrayList<>();
    private String[] picUrls = {"http://pic31.nipic.com/20130801/11604791_100539834000_2.jpg",
            "http://pic37.nipic.com/20140115/7430301_100825571157_2.jpg",
            "http://pic29.nipic.com/20130507/8952533_183922555000_2.jpg",
            "http://b-ssl.duitang.com/uploads/item/201706/10/20170610095055_G5LM8.jpeg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        setTitle(R.string.title_view_pager);
        initData();
        initViewPager();
    }

    private void initViewPager() {
        mViewpager = findViewById(R.id.viewpager);
        mViewpager.showIndicator(true)
                .setInterval(3000)
                .setRoundCorner(R.dimen.banner_corner)
                .setScrollDuration(1000)
                .setData(mDataList)
                .setHolderCreator(new HolderCreator<DataViewHolder>() {
                    @Override
                    public DataViewHolder createViewHolder() {
                        return new DataViewHolder();
                    }
                })
                .setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
                    @Override
                    public void onPageClick(int position) {
                        Toast.makeText(ViewPagerActivity.this, "点击了图片" + position, Toast.LENGTH_SHORT).show();
                    }
                }).create();
    }

    private void initData() {
        for (int i = 0; i < picUrls.length; i++) {
            DataBean dataBean = new DataBean(picUrls[i]);
            mDataList.add(dataBean);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewpager.stopLoop();
    }
}
