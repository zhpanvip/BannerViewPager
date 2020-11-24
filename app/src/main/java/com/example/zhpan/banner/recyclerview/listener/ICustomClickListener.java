package com.example.zhpan.banner.recyclerview.listener;

import android.view.View;

public interface ICustomClickListener {
    void onClick(View view, int position, int type, Object... data);

    void onLongClick(View view, int position, int type, Object... data);
}
