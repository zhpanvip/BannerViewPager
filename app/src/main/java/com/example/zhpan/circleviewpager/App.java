package com.example.zhpan.circleviewpager;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.idea.utils.Utils;

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
        Utils.init(getApplicationContext());
        BannerUtils.setDebugMode(true);
        CrashReport.initCrashReport(getApplicationContext(), "69b176a2b6", true);
    }
}
