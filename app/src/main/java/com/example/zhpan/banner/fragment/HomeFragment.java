package com.example.zhpan.banner.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.LogUtils;
import com.example.zhpan.banner.R;
import com.example.zhpan.banner.activity.WebViewActivity;
import com.example.zhpan.banner.adapter.others.ArticleAdapter;
import com.example.zhpan.banner.adapter.MultiViewTypesAdapter;
import com.example.zhpan.banner.adapter.ViewBindingSampleAdapter;
import com.example.zhpan.banner.bean.ArticleWrapper;
import com.example.zhpan.banner.bean.DataWrapper;
import com.example.zhpan.banner.net.BannerData;
import com.example.zhpan.banner.net.RetrofitGnerator;
import com.example.zhpan.banner.net.RxUtil;
import com.example.zhpan.banner.net.common.ResponseObserver;
import com.example.zhpan.banner.recyclerview.ui.CustomRecyclerView;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static com.example.zhpan.banner.net.BannerData.TYPE_NEW;

/**
 * Created by zhpan on 2018/7/24.
 */
public class HomeFragment extends BaseFragment {

    private BannerViewPager<BannerData> mViewPagerHorizontal;
    private BannerViewPager<Integer> mViewPagerVertical;
    private CustomRecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private SmartRefreshLayout mSmartRefreshLayout;
    private IndicatorView mIndicatorView;
    private TextView mTvTitle;
    private RelativeLayout mRlIndicator;
    private View headerView;

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
        LogUtils.e("HomeFragment", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("HomeFragment", "onResume");
    }

    @Override
    protected void initView(Bundle savedInstanceState, @NonNull View view) {
        initRecyclerView(view);
        initRefreshLayout(view);
        initHorizontalBanner();
        initVerticalBanner();
        fetchData(true);
    }

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    private void initRecyclerView(View view) {
        mViewPagerHorizontal = view.findViewById(R.id.banner_view);
        mRlIndicator = view.findViewById(R.id.layout_indicator);
        mTvTitle = view.findViewById(R.id.tv_title);
        mIndicatorView = view.findViewById(R.id.indicator_view);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getMContext()));
        recyclerView.addHeaderView(getHeaderView(), true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getMContext(),
            DividerItemDecoration.VERTICAL));
        articleAdapter = new ArticleAdapter(requireActivity(), new ArrayList<>());
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
                    ArticleWrapper.Article article = new ArticleWrapper.Article();
                    article.setType(1001);
                    article.setBannerData(new ArrayList<>(response.getDataBeanList()));
                    headerView.setVisibility(View.VISIBLE);
                    List<BannerData> dataList = response.getDataBeanList();
                    BannerData bannerData = new BannerData();
                    bannerData.setDrawable(R.drawable.bg_card3);
                    bannerData.setType(TYPE_NEW);
                    bannerData.setTitle("这是一个自定义类型");
                    dataList.add(1, bannerData);
                    mViewPagerHorizontal.refreshData(dataList);
                    List<ArticleWrapper.Article> articleList = response.getArticleList();

                    articleList.add(4, article);
                    articleAdapter.setData(articleList);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    if (response.getDataBeanList().size() > 0) {
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

    private void initHorizontalBanner() {
        mViewPagerHorizontal
            .setScrollDuration(600)
            .setOffScreenPageLimit(2)
            .registerLifecycleObserver(getLifecycle())
            .setIndicatorStyle(IndicatorStyle.CIRCLE)
            .setIndicatorSlideMode(IndicatorSlideMode.WORM)
            .setInterval(3000)
            .setIndicatorGravity(IndicatorGravity.END)
            .setIndicatorSliderRadius(getResources().getDimensionPixelSize(R.dimen.dp_3))
            .disallowParentInterceptDownEvent(true)
            .setIndicatorView(mIndicatorView)   // 这里为了设置标题故用了自定义Indicator,如果无需标题则没必要添加此行代码
            .setIndicatorSliderColor(getColor(R.color.red_normal_color),
                getColor(R.color.red_checked_color))
            .setAdapter(new MultiViewTypesAdapter())
            .registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    BannerData bannerData = mViewPagerHorizontal.getData().get(position);
                    mTvTitle.setText(bannerData.getTitle());
                    BannerUtils.log("position:" + mViewPagerHorizontal.getCurrentItem());
                }
            })
            .setOnPageClickListener(this::onPageClicked).create();
    }

    private void initVerticalBanner() {
        int dp16 = getResources().getDimensionPixelOffset(R.dimen.dp_16);
        int dp40 = getResources().getDimensionPixelOffset(R.dimen.dp_50);
        mViewPagerVertical
            .setAutoPlay(true)
            .setScrollDuration(500)
            .stopLoopWhenDetachedFromWindow(true)
            .registerLifecycleObserver(getLifecycle())
            .setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            .setIndicatorSlideMode(IndicatorSlideMode.SCALE)
            .setIndicatorSliderGap(getResources().getDimensionPixelOffset(R.dimen.dp_4))
            .setIndicatorMargin(dp16, dp16, dp16, dp40)
            .setIndicatorGravity(IndicatorGravity.START)
            .setIndicatorSliderWidth(getResources().getDimensionPixelOffset(R.dimen.dp_4),
                getResources().getDimensionPixelOffset(R.dimen.dp_10))
            .setIndicatorSliderColor(getColor(R.color.red_normal_color),
                getColor(R.color.red_checked_color))
            .setOrientation(ViewPager2.ORIENTATION_VERTICAL)
            .setInterval(2000)
            .setAdapter(new ViewBindingSampleAdapter(0)).create(getPicList(4));
    }

    private void onPageClicked(View clickedView, int position) {
        BannerData bannerData = mViewPagerHorizontal.getData().get(position);
        if (bannerData.getType() != TYPE_NEW) {
            if (getActivity() != null) {
                WebViewActivity.start(getActivity(), bannerData.getTitle(), bannerData.getUrl());
            }
        } else {
            Toast.makeText(getMContext(), "position:"
                + position
                + " "
                + bannerData.getTitle()
                + "currentItem:"
                + mViewPagerHorizontal.getCurrentItem(), Toast.LENGTH_SHORT).show();
        }
    }

    private View getHeaderView() {
        headerView =
            LayoutInflater.from(getMContext())
                .inflate(R.layout.item_header_view, recyclerView, false);
        mViewPagerVertical = headerView.findViewById(R.id.banner_view2);
        return headerView;
    }
}
