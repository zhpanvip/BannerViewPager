package com.example.zhpan.circleviewpager.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.net.BannerData;
import com.example.zhpan.circleviewpager.net.RetrofitGnerator;
import com.example.zhpan.circleviewpager.viewholder.NetViewHolder;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zhpan.bannerview.view.BannerViewPager;
import com.zhpan.idea.net.common.DefaultObserver;
import com.zhpan.idea.utils.RxUtil;

import java.util.List;

public class NetworkBannerActivity extends RxAppCompatActivity {

    BannerViewPager<BannerData, NetViewHolder> mBannerViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_banner);
        mBannerViewPager = findViewById(R.id.banner_view);
        mBannerViewPager.showIndicator(true)
                .setInterval(3000)
                .setIndicatorGravity(BannerViewPager.END)
                .setScrollDuration(1000).setHolderCreator(NetViewHolder::new)
                .setOnPageClickListener(position -> {

                    BannerData bannerData = mBannerViewPager.getList().get(position);
                    Toast.makeText(NetworkBannerActivity.this,
                            "点击了图片" + position + " " + bannerData.getDesc(), Toast.LENGTH_SHORT).show();

                }).create();
        RetrofitGnerator.getApiSerVice().getBannerData().compose(RxUtil.rxSchedulerHelper(this)).subscribe(new DefaultObserver<List<BannerData>>() {
            @Override
            public void onSuccess(List<BannerData> response) {
                mBannerViewPager.setData(response);
            }
        });
    }
}
