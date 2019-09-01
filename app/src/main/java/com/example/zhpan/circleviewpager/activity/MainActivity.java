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
            case R.id.btn_view_pager3:
                intent = new Intent(this, BannerPhotoViewActivity.class);
                break;
            case R.id.btn_view_pager4:
                intent = new Intent(this, NetworkBannerActivity.class);
                break;
            case R.id.btn_view_pager5:
                intent = new Intent(this, PageTransformerActivity.class);
                break;
            case R.id.btn_slide_mode:
                intent = new Intent(this, IndicatorSlideModeActivity.class);
                break;
            default:
                intent = new Intent(this, CustomerBannerActivity.class);
                break;
        }
        startActivity(intent);
    }
}
