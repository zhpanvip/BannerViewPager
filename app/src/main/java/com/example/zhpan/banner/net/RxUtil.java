package com.example.zhpan.banner.net;

import com.example.zhpan.banner.net.common.LoadingTransformerCreator;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("unused")
public class RxUtil {

  /**
   * 统一线程处理
   *
   * @return ObservableTransformer
   */
  public static <T> ObservableTransformer<T, T> rxSchedulerHelper(
      final RxAppCompatActivity activity, final boolean showLoading) {    //compose简化线程
    return observable -> {
      Observable<T> compose = observable.subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .compose(activity.bindUntilEvent(ActivityEvent.DESTROY));
      if (showLoading) {
        return compose.compose(LoadingTransformerCreator.applyLoading(activity));
      } else {
        return compose;
      }
    };
  }

  /**
   * 统一线程处理
   *
   * @return ObservableTransformer
   */
  public static <T> ObservableTransformer<T, T> rxSchedulerHelper(final RxFragment fragment,
      final boolean showLoading) {
    return observable -> {
      Observable<T> compose = observable.subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY));
      if (showLoading) {
        return compose.compose(LoadingTransformerCreator.applyLoading(fragment.getActivity()));
      } else {
        return compose;
      }
    };
  }

  /**
   * 统一线程处理
   *
   * @return ObservableTransformer
   */
  public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
    return observable -> observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
