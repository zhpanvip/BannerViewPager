package com.example.zhpan.circleviewpager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.adapter.ArticleAdapter;
import com.example.zhpan.circleviewpager.adapter.HomeAdapter;
import com.example.zhpan.circleviewpager.adapter.ImageResourceAdapter;
import com.example.zhpan.circleviewpager.bean.ArticleWrapper;
import com.example.zhpan.circleviewpager.bean.DataWrapper;
import com.example.zhpan.circleviewpager.net.BannerData;
import com.example.zhpan.circleviewpager.net.RetrofitGnerator;
import com.example.zhpan.circleviewpager.recyclerview.ui.CustomRecyclerView;
import com.example.zhpan.circleviewpager.viewholder.BaseNetViewHolder;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.idea.net.common.ResponseObserver;
import com.zhpan.idea.utils.LogUtils;
import com.zhpan.idea.utils.RxUtil;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2018/7/24.
 */
public class HomeFragment extends BaseFragment {

    private BannerViewPager<BannerData, BaseNetViewHolder> mViewPagerHorizontal;
    private BannerViewPager<Integer, ImageResourceViewHolder> mViewPagerVertical;
    private BannerViewPager<Integer, ImageResourceViewHolder> mViewPager;
    private CustomRecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private SmartRefreshLayout mSmartRefreshLayout;
    private IndicatorView mIndicatorView;
    private TextView mTvTitle;
    private RelativeLayout mRlIndicator;
    private JzvdStd jzVideo;

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
        if (mViewPagerHorizontal != null) {
            mViewPagerHorizontal.stopLoop();
        }
        if (mViewPagerVertical != null) {
            mViewPagerVertical.stopLoop();
        }
        if (mViewPager != null) {
            mViewPager.stopLoop();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("HomeFragment", "onResume");
        if (mViewPagerHorizontal != null) {
            mViewPagerHorizontal.startLoop();
        }
        if (mViewPagerVertical != null) {
            mViewPagerVertical.startLoop();
        }
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
        View headerView = getHeaderView();
        recyclerView.addHeadView(headerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(getMContext(),
                DividerItemDecoration.VERTICAL));
        articleAdapter = new ArticleAdapter(getMContext(), new ArrayList<>());
        recyclerView.setAdapter(articleAdapter);
        recyclerView.getHeadAndFootAdapter();

        mRlIndicator = headerView.findViewById(R.id.layout_indicator);
        mViewPagerHorizontal = headerView.findViewById(R.id.banner_view);
        mViewPagerVertical = headerView.findViewById(R.id.banner_view2);
        mViewPager = headerView.findViewById(R.id.banner_view3);
        mTvTitle = headerView.findViewById(R.id.tv_title);
        mIndicatorView = headerView.findViewById(R.id.indicator_view);

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                jzVideo = view.findViewById(R.id.jz_home);
                if (jzVideo != null && Jzvd.CURRENT_JZVD != null &&
                        jzVideo.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.getCurrentUrl())) {
                    jzVideo.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","");
                    if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                        Jzvd.releaseAllVideos();
                    }
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        });
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
                        List<BannerData> dataList = response.getDataBeanList();
                        BannerData bannerData = new BannerData();
                        bannerData.setUrl("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4");
                        bannerData.setType(BannerData.TYPE_VIDEO);
                        dataList.add(0, bannerData);
                        mViewPagerHorizontal.setData(dataList);
                        articleAdapter.setData(response.getArticleList());
                        if (response.getDataBeanList().size() > 0) {
                            mTvTitle.setText(response.getDataBeanList().get(0).getTitle());
                            mRlIndicator.setVisibility(View.VISIBLE);
                        }
//                        jzVideo.setUp(bannerData.getUrl(), bannerData.getTitle());
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
        mViewPagerHorizontal
                .setIndicatorSlideMode(IndicatorSlideMode.WORM)
                .setInterval(3000)
                .setIndicatorGravity(IndicatorGravity.END)
                .setIndicatorSliderRadius(getResources().getDimensionPixelSize(R.dimen.dp_3))
                .setIndicatorView(mIndicatorView)// 这里为了设置标题故用了自定义Indicator,如果无需标题则没必要添加此行代码
                .setIndicatorSliderColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setAdapter(new HomeAdapter())
                .registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        BannerData bannerData = mViewPagerHorizontal.getData().get(position);
                        mTvTitle.setText(bannerData.getTitle());
                    }
                })
                .setOnPageClickListener(this::onPageClicked);

        mViewPagerVertical
                .setAutoPlay(true)
                .setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                .setIndicatorSliderGap(getResources().getDimensionPixelOffset(R.dimen.dp_4))
                .setIndicatorSliderWidth(getResources().getDimensionPixelOffset(R.dimen.dp_4), getResources().getDimensionPixelOffset(R.dimen.dp_10))
                .setIndicatorSliderColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setOrientation(ViewPager2.ORIENTATION_VERTICAL)
                .setInterval(2000)
                .setAdapter(new ImageResourceAdapter(0)).create(getPicList(4));
        mViewPager
                .setCanLoop(false)
                .setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                .setIndicatorSliderGap(getResources().getDimensionPixelOffset(R.dimen.dp_4))
                .setIndicatorSliderWidth(getResources().getDimensionPixelOffset(R.dimen.dp_4), getResources().getDimensionPixelOffset(R.dimen.dp_10))
                .setIndicatorSliderColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                .setOrientation(ViewPager2.ORIENTATION_VERTICAL)
                .setInterval(2000)
                .setAdapter(new ImageResourceAdapter(0)).create(getPicList(3));
    }

    private void onPageClicked(int position) {
        BannerData bannerData = mViewPagerHorizontal.getData().get(position);
        Toast.makeText(getMContext(), "position:" + position + " " + bannerData.getTitle() + "currentItem:" + mViewPagerHorizontal.getCurrentItem(), Toast.LENGTH_SHORT).show();
    }

    private View getHeaderView() {
        View view = LayoutInflater.from(getMContext()).inflate(R.layout.item_header_view, recyclerView, false);
        return view;
    }
}
