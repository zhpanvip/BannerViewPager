package com.example.zhpan.circleviewpager.net;

import com.example.zhpan.circleviewpager.bean.ArticleWrapper;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public interface ApiService {

    @Headers("Cache-Control: public, max-age=" + 24 * 3600)
    @GET("banner/json")
    Observable<List<BannerData>> getBannerData();

    @Headers("Cache-Control: public, max-age=" + 24 * 3600)
    @GET("article/list/0/json")
    Observable<ArticleWrapper> getArticle();
}
