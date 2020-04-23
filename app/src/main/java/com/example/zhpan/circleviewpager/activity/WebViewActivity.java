package com.example.zhpan.circleviewpager.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.zhpan.circleviewpager.R;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * Created by zhpan on 2017/4/16.
 * Describe: 通用 WebView 考虑以后所有展示 WebView 的界面都从这个类继承
 */
@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends BaseCompatActivity {

    protected static final String INTENT_KEY_TITLE = "title";
    public static final String INTENT_KEY_URL = "url";
    protected WebView mWebView;
    private String mTitle;
    private String mUrl;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
        initData();
        initWebView();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mWebView = findViewById(R.id.webView);
        mRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        MaterialHeader materialHeader = findViewById(R.id.material_header);
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra(INTENT_KEY_TITLE);
            mUrl = intent.getStringExtra(INTENT_KEY_URL);
        }
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
        mToolbar.setTitle(mTitle);
        setRefreshLayout(true);
    }


    @Override
    protected boolean needRefreshHeader() {
        return false;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        mWebView.clearHistory();
        mWebView.reload();
    }

    private void initWebView() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return false;
            }
        });
        // mWebView.setBackgroundColor(getResources().getColor(R.color.background));
        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDisplayZoomControls(false);
        setting.setSupportZoom(false);
        setting.setBuiltInZoomControls(false);
//        setting.setSavePassword(false); // 安全要求

        if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mRefreshLayout.finishRefresh();
            }
        });
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }
        mWebView = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断是否可以返回操作
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            finish();
            return true;
        }
    }

    public static void start(@NonNull Context context, @NonNull String title, @NonNull String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(INTENT_KEY_TITLE, title);
        intent.putExtra(INTENT_KEY_URL, url);
        context.startActivity(intent);
    }

    public static void start(@NonNull Context context, @NonNull String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(INTENT_KEY_TITLE, "");
        intent.putExtra(INTENT_KEY_URL, url);
        context.startActivity(intent);
    }

    public static void start(@NonNull Context context, Class<? extends WebViewActivity> baseActivity, @NonNull String url) {
        Intent intent = new Intent(context, baseActivity);
        intent.putExtra(INTENT_KEY_TITLE, "");
        intent.putExtra(INTENT_KEY_URL, url);
        context.startActivity(intent);
    }
}
