package com.example.zhpan.circleviewpager.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.zhpan.circleviewpager.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_page_style:
                intent = new Intent(this, PageStyleActivity.class);
                break;
            case R.id.btn_custom_indicator:
                intent = new Intent(this, CustomIndicatorActivity.class);
                break;
            case R.id.btn_view_pager3:
                intent = new Intent(this, PhotoViewActivity.class);
                break;
            case R.id.btn_view_pager4:
                intent = new Intent(this, NetworkBannerActivity.class);
                break;
            case R.id.btn_view_pager5:
                intent = new Intent(this, PageTransformerActivity.class);
                break;
            case R.id.btn_indicator_style:
                intent = new Intent(this, IndicatorStyleActivity.class);
                break;
            default:
                intent = new Intent(this, CustomerBannerPageActivity.class);
                break;
        }
        startActivity(intent);
    }
}
