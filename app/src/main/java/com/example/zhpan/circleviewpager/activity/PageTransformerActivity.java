package com.example.zhpan.circleviewpager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.TransformerViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.TransformerStyle;
import com.zhpan.bannerview.transform.StackTransformer;

import java.util.ArrayList;

public class PageTransformerActivity extends AppCompatActivity {

    private static final String TAG = "PageTransformerActivity";
    private BannerViewPager<Integer, TransformerViewHolder> mViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_transformer);
        setTitle(R.string.title_transformer);
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
                        "this is item:" + mViewpager.getCurrentItem(), Toast.LENGTH_SHORT).show())
                .create(getData());
        mViewpager.setPageTransformer(new StackTransformer());
    }

    private ArrayList<Integer> getData() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            int drawable = getResources().getIdentifier("guide" + i, "drawable", getPackageName());
            list.add(drawable);
        }
        return list;
    }
}
