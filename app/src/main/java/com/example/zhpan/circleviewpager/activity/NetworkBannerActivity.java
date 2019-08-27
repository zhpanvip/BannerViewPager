package com.example.zhpan.circleviewpager.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.adapter.ArticleAdapter;
import com.example.zhpan.circleviewpager.bean.ArticleWrapper;
import com.example.zhpan.circleviewpager.bean.DataWrapper;
import com.example.zhpan.circleviewpager.net.BannerData;
import com.example.zhpan.circleviewpager.net.RetrofitGnerator;
import com.example.zhpan.circleviewpager.recyclerview.ui.CustomRecyclerView;
import com.example.zhpan.circleviewpager.viewholder.NetViewHolder;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.idea.net.common.DefaultObserver;
import com.zhpan.idea.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class NetworkBannerActivity extends RxAppCompatActivity {

    private BannerViewPager<BannerData, NetViewHolder> mBannerViewPager;
    private CustomRecyclerView recyclerView;
    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_banner);
        setTitle(R.string.load_data);
        initBanner();
        fetchData();
    }

    private void fetchData() {
        Observable.zip(getBannerObserver(), getArticleObserver(), (bannerData, articles) -> {
            DataWrapper dataWrapper = new DataWrapper();
            dataWrapper.setArticleList(articles.getDatas());
            dataWrapper.setDataBeanList(bannerData);
            return dataWrapper;
        }).compose(RxUtil.rxSchedulerHelper(this))
                .subscribe(new DefaultObserver<DataWrapper>() {
                    @Override
                    public void onSuccess(DataWrapper response) {
                        mBannerViewPager.setData(response.getDataBeanList());
                        articleAdapter.addData(response.getArticleList());
                    }
                });
    }

    private Observable<ArticleWrapper> getArticleObserver() {
        return RetrofitGnerator.getApiSerVice().getArticle().subscribeOn(Schedulers.io());
    }

    private Observable<List<BannerData>> getBannerObserver() {
        return RetrofitGnerator.getApiSerVice().getBannerData().subscribeOn(Schedulers.io());
    }

    private void initBanner() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addHeadView(getHeaderView());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        articleAdapter = new ArticleAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(articleAdapter);
        mBannerViewPager.showIndicator(true)
                .setInterval(3000)
                .setIndicatorGravity(BannerViewPager.END)
                .setScrollDuration(1000).setHolderCreator(NetViewHolder::new)
                .setOnPageClickListener(position -> {
                    BannerData bannerData = mBannerViewPager.getList().get(position);
                    Toast.makeText(NetworkBannerActivity.this,
                            "点击了图片" + position + " " + bannerData.getDesc(), Toast.LENGTH_SHORT).show();

                }).create();
    }

    private View getHeaderView() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_header_view, recyclerView, false);
        mBannerViewPager = view.findViewById(R.id.banner_view);
        return view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBannerViewPager.stopLoop();
    }
}
