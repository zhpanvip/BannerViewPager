package com.example.viewpager.holder;

import android.content.Context;
import android.view.View;

/**
 * Created by zhpan on 2017/10/30.
 * Description:
 */

public interface ViewHolder<T> {
    View createView(Context context);

   // void onBind(Context context, int position, T data);

    void onBind(Context context,T data);
}
