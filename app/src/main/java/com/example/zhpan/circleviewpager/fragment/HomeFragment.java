package com.example.zhpan.circleviewpager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.zhpan.bannerview.adapter.OnPageChangeListenerAdapter;
import com.zhpan.idea.net.common.ResponseObserver;
import com.zhpan.idea.utils.LogUtils;
import com.zhpan.idea.utils.RxUtil;
import com.zhpan.indicatorview.enums.IndicatorSlideMode;
import com.zhpan.indicatorview.IndicatorView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2018/7/24.
 */
public class HomeFragment extends BaseFragment {

    private BannerViewPager<BannerData, NetViewHolder> mViewPager;
    private CustomRecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private SmartRefreshLayout mSmartRefreshLayout;
    private IndicatorView mIndicatorView;
    private TextView mTvTitle;
    private RelativeLayout mRlIndicator;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.e("HomeFragment","onPause");
        if (mViewPager != null) {
            mViewPager.stopLoop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("HomeFragment","onResume");
        if (mViewPager != null) {
            mViewPager.startLoop();
        }
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
                        mViewPager.create(response.getDataBeanList());
                        articleAdapter.setData(response.getArticleList());
                        if (response.getDataBeanList().size() > 0) {
                            mTvTitle.setText(response.getDataBeanList().get(0).getTitle());
                            mRlIndicator.setVisibility(View.VISIBLE);
                        }
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
        mViewPager
                .setAutoPlay(true)
                .setIndicatorSlideMode(IndicatorSlideMode.WORM)
                .setInterval(5000)
                .setScrollDuration(1200)
                .setIndicatorRadius(getResources().getDimensionPixelSize(R.dimen.dp_3))
                .setIndicatorView(mIndicatorView)// 这里为了设置标题故用了自定义Indicator,如果无需标题则没必要添加此行代码
                .setIndicatorColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setHolderCreator(NetViewHolder::new)
                .setOnPageChangeListener(new OnPageChangeListenerAdapter() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        BannerData bannerData = mViewPager.getList().get(position);
                        mTvTitle.setText(bannerData.getTitle());
                    }
                })
                .setOnPageClickListener(this::onPageClicked);
    }

    private void onPageClicked(int position) {
        BannerData bannerData = mViewPager.getList().get(position);
        Toast.makeText(getMContext(), "position:" + position + " " + bannerData.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private View getHeaderView() {
        View view = LayoutInflater.from(getMContext()).inflate(R.layout.item_header_view, recyclerView, false);
        mRlIndicator = view.findViewById(R.id.layout_indicator);
        mViewPager = view.findViewById(R.id.banner_view);
        mTvTitle = view.findViewById(R.id.tv_title);
        mIndicatorView = view.findViewById(R.id.indicator_view);
        return view;
    }
}
