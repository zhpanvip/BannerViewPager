package com.example.zhpan.banner.loading;

import android.app.Activity;
import android.content.Context;

import com.example.zhpan.banner.R;

/**
 * Created by zhpan on 2017/5/26.
 */
public class LoadingUtils {

  private LoadingDialog mProgressDialog;

  /**
   * 显示带有文字的ProgressDialog
   */
  public void showLoading(Context context, String msg) {
    if (context == null || context instanceof Activity && ((Activity) context).isFinishing()) {
      return;
    }

    if (mProgressDialog == null) {
      mProgressDialog = new LoadingDialog.Builder(context)
          .setTheme(R.style.ProgressDialogStyle)
          .setMessage(msg)
          .build();
    }
    if (!mProgressDialog.isShowing()) {
      mProgressDialog.show();
    }
  }

  /**
   * 显示ProgressDialog
   */
  public void showLoading(Context context) {
    if (context == null || context instanceof Activity && ((Activity) context).isFinishing()) {
      return;
    }
    if (mProgressDialog == null) {
      mProgressDialog = new LoadingDialog.Builder(context)
          .setTheme(R.style.ProgressDialogStyle)
          .build();
    }
    if (!mProgressDialog.isShowing()) {
      mProgressDialog.show();
    }
  }

  /**
   * 取消ProgressDialog
   */
  public void dismiss() {
    if (mProgressDialog != null && mProgressDialog.isShowing()) {
      mProgressDialog.dismiss();
    }
  }
}
