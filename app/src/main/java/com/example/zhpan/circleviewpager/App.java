package com.example.zhpan.circleviewpager;

import android.app.Application;

import com.example.zhpan.circleviewpager.imageloader.GlideImageLoader;
import com.example.zhpan.circleviewpager.imageloader.ImageLoaderManager;
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
        ImageLoaderManager.getInstance().init(new GlideImageLoader());
        Utils.init(getApplicationContext());
    }
}
