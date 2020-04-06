package com.zhpan.bannerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *   Created by zhpan on 2020/4/5.
 *   Description:
 * </pre>
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindData(T data, int position, int pageSize);
}
