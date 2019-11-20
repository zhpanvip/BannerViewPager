package com.zhpan.bannerview.manager;

import android.content.Context;
import android.util.AttributeSet;

public class BannerManager {

    private BannerOptions mBannerOptions;

    private AttributeController mAttributeController;

    public BannerManager() {
        mBannerOptions = new BannerOptions();
        mAttributeController = new AttributeController(mBannerOptions);
    }

    public BannerOptions bannerOptions() {
        if (mBannerOptions == null) {
            mBannerOptions = new BannerOptions();
        }
        return mBannerOptions;
    }

    public void initAttrs(Context context, AttributeSet attrs) {
        mAttributeController.init(context, attrs);
    }
}
