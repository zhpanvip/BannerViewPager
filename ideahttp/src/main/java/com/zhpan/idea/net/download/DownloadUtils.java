package com.zhpan.idea.net.download;


import com.zhpan.idea.net.common.CommonService;
import com.zhpan.idea.net.common.RetrofitUtils;
import com.zhpan.idea.net.common.Constants;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.http.Url;

/**
 * Created by zhpan on 2018/3/21.
 */

public class DownloadUtils {
    private static final String TAG = "DownloadUtils";
    private DownloadListener mDownloadListener;
    private CompositeDisposable mDisposables;

    public DownloadUtils() {
        mDisposables = new CompositeDisposable();
    }

    /**
     * 开始下载
     *
     * @param url
     */
    public void download(@Url String url, DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
        getApiService().download(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(getConsumer())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    public void download(Map<String,Object> map, DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
        getApiService()
                .downloadFile(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(getConsumer())
                .subscribe(getObserver());
    }

    public void download(DwonloadRequest request, DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
        getApiService()
                .downloadFile(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(getConsumer())
                .subscribe(getObserver());
    }



    /**
     * 取消下载
     */
    public void cancelDownload() {
        mDisposables.clear();
    }

    private CommonService getApiService() {
        OkHttpClient.Builder httpClientBuilder = RetrofitUtils.getOkHttpClientBuilder();
        ProgressHelper.addProgress(httpClientBuilder);
        CommonService ideaApiService = RetrofitUtils.getRetrofitBuilder(Constants.API_SERVER_URL)
                .client(httpClientBuilder.build())
                .build()
                .create(CommonService.class);
        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                mDownloadListener.onProgress((int) ((100 * bytesRead) / contentLength));
            }
        });
        return ideaApiService;
    }


    private Consumer<ResponseBody> getConsumer() {
        return new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody responseBody) throws Exception {
                mDownloadListener.onSuccess(responseBody);
            }
        };
    }

    private Observer<ResponseBody> getObserver() {
        return new Observer<ResponseBody>() {

            @Override
            public void onSubscribe(Disposable d) {
                mDisposables.add(d);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
               // mDownloadListener.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                mDownloadListener.onFail(e.getMessage());
                mDisposables.clear();
            }

            @Override
            public void onComplete() {
                mDownloadListener.onComplete();
                mDisposables.clear();
            }
        };
    }
}
