package com.example.zhpan.banner.net;

import com.example.zhpan.banner.net.common.IdeaApi;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class RetrofitGnerator {
    private static ApiService mApiservice;

    public static ApiService getApiSerVice() {
        if (mApiservice == null) {
            mApiservice = IdeaApi.getApiService(ApiService.class, Constants.BASE_URL);
        }
        return mApiservice;
    }
}
