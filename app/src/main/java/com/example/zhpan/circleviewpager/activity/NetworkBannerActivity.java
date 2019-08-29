package com.example.zhpan.circleviewpager.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
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
    private SmartRefreshLayout mSmartRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_banner);
        setTitle(R.string.load_data);
        initRefreshLayout();
        initBanner();
        fetchData(true);
    }

    private void initRefreshLayout() {
        mSmartRefreshLayout = findViewById(R.id.refresh_layout);
        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> fetchData(false));
    }

    private void fetchData(boolean showLoading) {
        Observable.zip(getBannerObserver(), getArticleObserver(), (bannerData, articles) -> {
            DataWrapper dataWrapper = new DataWrapper();
            dataWrapper.setArticleList(articles.getDatas());
            dataWrapper.setDataBeanList(bannerData);
            return dataWrapper;
        }).compose(RxUtil.rxSchedulerHelper(this, showLoading))
                .subscribe(new DefaultObserver<DataWrapper>() {
                    @Override
                    public void onSuccess(DataWrapper response) {
                        mBannerViewPager.setData(response.getDataBeanList());
                        articleAdapter.setData(response.getArticleList());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mSmartRefreshLayout.finishRefresh();
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
        recyclerView.addItemDecoration(new DividerItemDecoration(NetworkBannerActivity.this,
                DividerItemDecoration.VERTICAL));
        articleAdapter = new ArticleAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(articleAdapter);
        mBannerViewPager.showIndicator(true)
                .setInterval(3000)
                .setData(new ArrayList<>())
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
