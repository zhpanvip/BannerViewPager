package com.example.zhpan.circleviewpager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.TransformerViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.enums.TransformerStyle;

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
        switch (item.getItemId()) {
            case R.id.menu0:
                mViewpager.setPageTransformerStyle(TransformerStyle.STACK);
                break;
            case R.id.menu1:
                mViewpager.setPageTransformerStyle(TransformerStyle.ROTATE);
                break;
            case R.id.menu2:
                mViewpager.setPageTransformerStyle(TransformerStyle.DEPTH);
                break;
            case R.id.menu3:
                mViewpager.setPageTransformerStyle(TransformerStyle.ACCORDION);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViewPager() {
        mViewpager = findViewById(R.id.viewpager);
        mViewpager.showIndicator(false)
                .setCanLoop(false)
                .setAutoPlay(false)
                .setScrollDuration(1000)
                .setHolderCreator(TransformerViewHolder::new)
                .setOnPageClickListener(position -> Toast.makeText(PageTransformerActivity.this,
                        "立即体验", Toast.LENGTH_SHORT).show())
                .create(mList);
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
