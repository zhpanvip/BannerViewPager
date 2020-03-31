package com.zhpan.bannerview.holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zhpan on 2017/10/30.
 * Description:
 */

public interface HolderCreator2<VH extends RecyclerView.ViewHolder> {
    /**
     * 创建ViewHolder
     */
    VH createViewHolder();

    void bindViewHolder(@NonNull VH holder, int position);

}
