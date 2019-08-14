package com.example.zhpan.circleviewpager.net;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public interface ApiService {
    @GET("banner/json")
    Observable<List<BannerData>> getBannerData();

}
