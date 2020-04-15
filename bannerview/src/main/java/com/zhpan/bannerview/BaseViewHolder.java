package com.zhpan.bannerview;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *   Created by zhpan on 2020/4/5.
 *   Attention:Don't use {@link RecyclerView.ViewHolder#getAdapterPosition}
 *   method to get position,this method will return a fake position.
 * </pre>
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews = new SparseArray<>();

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindData(T data, int position, int pageSize);

    @SuppressWarnings("unchecked")
    protected <V extends View> V findView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (V) view;
    }


    protected void setText(int viewId, String text) {
        View view = findView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    protected void setText(int viewId, @StringRes int textId) {
        View view = findView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setText(textId);
        }
    }

    protected void setTextColor(int viewId, @ColorInt int colorId) {
        View view = findView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(colorId);
        }
    }

    protected void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        findView(viewId).setOnClickListener(clickListener);
    }

    protected void setBackgroundResource(int viewId, @DrawableRes int resId) {
        findView(viewId).setBackgroundResource(resId);
    }

    protected void setBackgroundColor(int viewId, @ColorInt int colorId) {
        findView(viewId).setBackgroundColor(colorId);
    }

    protected void setImageResource(@IdRes int viewId, @DrawableRes int resId) {
        View view = findView(viewId);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(resId);
        }
    }
}
