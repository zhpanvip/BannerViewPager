package com.example.zhpan.circleviewpager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.idea.net.common.ResponseObserver;
import com.zhpan.idea.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2018/7/24.
 */
public class HomeFragment extends BaseFragment {

    private BannerViewPager<BannerData, NetViewHolder> mBannerViewPager;
    private CustomRecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private SmartRefreshLayout mSmartRefreshLayout;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView(Bundle savedInstanceState, View view) {
        initRecyclerView(view);
        initRefreshLayout(view);
        initBanner();
        fetchData(true);
    }

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getMContext()));
        recyclerView.addHeadView(getHeaderView());
        recyclerView.addItemDecoration(new DividerItemDecoration(getMContext(),
                DividerItemDecoration.VERTICAL));
        articleAdapter = new ArticleAdapter(getMContext(), new ArrayList<>());
        recyclerView.setAdapter(articleAdapter);
    }

    private void initRefreshLayout(View view) {
        mSmartRefreshLayout = view.findViewById(R.id.refresh_layout);
        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(getMContext()));
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> fetchData(false));
    }

    private void fetchData(boolean showLoading) {
        Observable.zip(getBannerObserver(), getArticleObserver(), (bannerData, articles) ->
                new DataWrapper(articles.getDatas(), bannerData))
                .compose(RxUtil.rxSchedulerHelper(this, showLoading))
                .subscribe(new ResponseObserver<DataWrapper>() {
                    @Override
                    public void onSuccess(DataWrapper response) {
                        mBannerViewPager.create(response.getDataBeanList());
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
        mBannerViewPager
                .setAutoPlay(true)
                .setInterval(5000)
                .setRevealWidth(BannerUtils.dp2px(10))
                .setPageMargin(BannerUtils.dp2px(10))
                .setPageStyle(PageStyle.MULTI_PAGE_OVERLAP)
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setHolderCreator(NetViewHolder::new)
                .setIndicatorMargin(0,0,0, (int) getResources().getDimension(R.dimen.dp_18))
                .setOnPageClickListener(position -> {
                    BannerData bannerData = mBannerViewPager.getList().get(position);
                    Toast.makeText(getMContext(),
                            "点击了position:" + position + " " + bannerData.getTitle(), Toast.LENGTH_SHORT).show();

                });
    }

    private View getHeaderView() {
        View view = LayoutInflater.from(getMContext()).inflate(R.layout.item_header_view, recyclerView, false);
        mBannerViewPager = view.findViewById(R.id.banner_view);
        return view;
    }
}
