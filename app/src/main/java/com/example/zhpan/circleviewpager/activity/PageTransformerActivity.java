package com.example.zhpan.circleviewpager.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.TransformerViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.transform.TransformerStyle;

import java.util.ArrayList;

public class PageTransformerActivity extends AppCompatActivity {

    private static final String TAG = "PageTransformerActivity";
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
                mViewpager.setPageTransformerStyle(TransformerStyle.ROTATE_DOWN);
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
                .setData(mList)
                .setHolderCreator(() -> {
                    TransformerViewHolder transformerViewHolder = new TransformerViewHolder();
                    transformerViewHolder.setSubViewClickListener(view -> Toast.makeText(PageTransformerActivity.this,
                            "立即体验", Toast.LENGTH_SHORT).show());
                    return transformerViewHolder;
                }).create();
//        mViewpager.setOnPageChangedListener(new BannerViewPager.OnPageChangedListener() {
//            @Override
//            public void onPageSelected(int position) {
//                Log.e(TAG,"onPageSelected------------------------------->"+position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                Log.e(TAG,"onPageScrollStateChanged--------->"+state);
//            }
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.e(TAG,"onPageScrolled------------------>"+position);
//            }
//        });
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
