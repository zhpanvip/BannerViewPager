package com.zhpan.bannerview.manager;

import android.content.Context;
import android.util.AttributeSet;

/**
 * <pre>
 *   Created by zhpan on 2019/11/20.
 *   Description:
 * </pre>
 */
public class BannerManager {

    private BannerOptions mBannerOptions;

    private AttributeController mAttributeController;

    public BannerManager() {
        mBannerOptions = new BannerOptions();
        mAttributeController = new AttributeController(mBannerOptions);
    }

    public BannerOptions getBannerOptions() {
        if (mBannerOptions == null) {
            mBannerOptions = new BannerOptions();
        }
        return mBannerOptions;
    }

    public void initAttrs(Context context, AttributeSet attrs) {
        mAttributeController.init(context, attrs);
    }
}
