package com.example.zhpan.circleviewpager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zhpan.circleviewpager.R;


public class MainActivity extends AppCompatActivity {
    private String[] picUrls = {"http://pic31.nipic.com/20130801/11604791_100539834000_2.jpg",
            "http://pic37.nipic.com/20140115/7430301_100825571157_2.jpg",
            "http://pic29.nipic.com/20130507/8952533_183922555000_2.jpg",
            "http://b-ssl.duitang.com/uploads/item/201706/10/20170610095055_G5LM8.jpeg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_view_pager1:
                intent = new Intent(this, ViewPagerActivity.class);
                break;
            case R.id.btn_view_pager2:
                intent = new Intent(this, CustomerBannerActivity.class);
                break;
            case R.id.btn_view_pager3:
                intent = new Intent(this, BannerPhotoViewActivity.class);
                break;
            case R.id.btn_view_pager4:
                intent = new Intent(this, NetworkBannerActivity.class);
                break;
            case R.id.btn_view_pager5:
                intent = new Intent(this, PageTransformerActivity.class);
                break;
            default:
                intent = new Intent(this, ViewPagerActivity.class);
                break;
        }
        startActivity(intent);
    }
}
