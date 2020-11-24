package com.example.zhpan.banner;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;
import com.zhpan.bannerview.utils.BannerUtils;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BannerUtils.setDebugMode(true);
        CrashReport.initCrashReport(getApplicationContext(), "69b176a2b6", true);
    }
}
