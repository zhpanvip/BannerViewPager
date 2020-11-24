package com.example.zhpan.banner.net.common;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.zhpan.banner.loading.LoadingUtils;

import io.reactivex.CompletableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by zhpan on 2018/3/22.
 */
public class LoadingTransformerCreator {

    public static <T> ObservableTransformer<T, T> applyLoading(
            @NonNull final Activity activity, String msg) {
        final LoadingUtils dialogUtils = new LoadingUtils();
        dialogUtils.showLoading(activity, msg);
        return upstream -> upstream.doOnSubscribe(disposable -> {
        }).doOnTerminate(dialogUtils::dismiss)
                .doOnSubscribe((Consumer<Disposable>) disposable -> {
                });
    }

    public static <T> ObservableTransformer<T, T> applyLoading(
            @NonNull final Activity activity) {
        return applyLoading(activity, "");
    }


    public static CompletableTransformer applyLoadingForCompletable(
            @NonNull final Activity activity) {
        return applyLoadingForCompletable(activity, "");
    }

    public static CompletableTransformer applyLoadingForCompletable(
            @NonNull final Activity activity, String msg) {
        final LoadingUtils dialogUtils = new LoadingUtils();
        dialogUtils.showLoading(activity, msg);
        return upstream -> upstream.doOnSubscribe(disposable -> {

        }).doOnTerminate(dialogUtils::dismiss)
                .doOnSubscribe((Consumer<Disposable>) disposable -> {
                });
    }

}
