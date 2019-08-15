package com.example.zhpan.circleviewpager.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.TransformerViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.transform.AccordionTransformer;
import com.zhpan.bannerview.transform.BackgroundToForegroundTransformer;
import com.zhpan.bannerview.transform.CubeOutTransformer;
import com.zhpan.bannerview.transform.DepthPageTransformer;
import com.zhpan.bannerview.transform.FlipHorizontalTransformer;
import com.zhpan.bannerview.transform.RotateDownTransformer;
import com.zhpan.bannerview.transform.RotateUpTransformer;
import com.zhpan.bannerview.transform.ZoomOutSlideTransformer;
import com.zhpan.bannerview.transform.ZoomOutTranformer;

import java.util.ArrayList;

public class PageTransformerActivity extends AppCompatActivity {

    private BannerViewPager<Integer, TransformerViewHolder> mViewpager;
    private ArrayList<Integer> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_transformer);
        setTitle(R.string.title_transformer);
        initData();
        initViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.transformer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ViewPager.PageTransformer pageTransformer = null;
        switch (item.getItemId()) {
            case R.id.menu0:
                pageTransformer = new AccordionTransformer();
                break;
            case R.id.menu1:
                pageTransformer = new RotateDownTransformer();
                break;
            case R.id.menu2:
                pageTransformer = new RotateUpTransformer();
                break;
            case R.id.menu3:
                pageTransformer = new DepthPageTransformer();
                break;
            case R.id.menu4:
                pageTransformer = new ZoomOutSlideTransformer();
                break;
            case R.id.menu5:
                pageTransformer = new ZoomOutTranformer();
                break;
            case R.id.menu6:
                pageTransformer = new BackgroundToForegroundTransformer();
                break;
            case R.id.menu7:
                pageTransformer = new CubeOutTransformer();
                break;
            case R.id.menu8:
                pageTransformer = new FlipHorizontalTransformer();
                break;
        }
        if (pageTransformer != null)
            mViewpager.setPageTransformer(pageTransformer);
        return super.onOptionsItemSelected(item);
    }

    private void initViewPager() {
        mViewpager = findViewById(R.id.viewpager);
        mViewpager.showIndicator(false)
                .setCanLoop(false)
                .setAutoPlay(false)
                .setScrollDuration(1000)
                .setData(mList)
                .setHolderCreator(TransformerViewHolder::new)
                .setOnPageClickListener(position -> Toast.makeText(PageTransformerActivity.this,
                        "立即体验", Toast.LENGTH_SHORT).show())
                .create();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewpager.stopLoop();
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            int drawable = getResources().getIdentifier("guide" + i, "drawable", getPackageName());
            mList.add(drawable);
        }
    }
}
