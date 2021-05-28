package com.example.zhpan.banner.loading;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.zhpan.banner.R;

public class LoadingDialog extends Dialog {

  private final boolean cancelTouchOutside;
  private LottieAnimationView lottieAnimationView;
  private String message;
  private static final int KEY_NO_DIALOG_STYLE = -1;

  public LoadingDialog(Builder builder) {
    super(builder.context);
    cancelTouchOutside = builder.cancelTouchOutside;
  }

  private LoadingDialog(Builder builder, int themeResId) {
    super(builder.context, themeResId);
    cancelTouchOutside = builder.cancelTouchOutside;
    this.message = builder.message;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_loading_dialog);
    TextView tvMessage = (TextView) findViewById(R.id.tv_loading);
    if (tvMessage != null) {
      tvMessage.setText(message);
    }
    setCanceledOnTouchOutside(cancelTouchOutside);
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    lottieAnimationView = findViewById(R.id.loadingImageView);
    lottieAnimationView.playAnimation();
  }

  @Override
  protected void onStop() {
    super.onStop();
    lottieAnimationView.pauseAnimation();
  }

  public static final class Builder {
    Context context;
    private int themeResId = KEY_NO_DIALOG_STYLE;
    private String message;
    private boolean cancelTouchOutside;

    public Builder(Context context) {
      this.context = context;
    }

    /**
     * 设置主题
     *
     * @param themeResId theme res id
     * @return CustomProgressDialog.Builder
     */
    public Builder setTheme(int themeResId) {
      this.themeResId = themeResId;
      return this;
    }

    public Builder setMessage(String message) {
      this.message = message;
      return this;
    }

    /**
     * 设置点击dialog外部是否取消dialog
     *
     * @param val 点击外部是否取消dialog
     * @return Builder
     */
    public Builder cancelTouchOutside(boolean val) {
      cancelTouchOutside = val;
      return this;
    }

    public LoadingDialog build() {
      if (themeResId == KEY_NO_DIALOG_STYLE) {
        return new LoadingDialog(this);
      } else {
        return new LoadingDialog(this, themeResId);
      }
    }
  }
}
