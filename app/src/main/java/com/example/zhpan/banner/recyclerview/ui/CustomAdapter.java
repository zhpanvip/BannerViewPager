package com.example.zhpan.banner.recyclerview.ui;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.zhpan.banner.R;
import com.example.zhpan.banner.recyclerview.listener.ICustomClickListener;
import com.example.zhpan.banner.recyclerview.module.RecyclerViewConfig;

import java.util.ArrayList;
import java.util.List;

final class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private final List<RecyclerViewConfig> headConfig;
  private final List<RecyclerViewConfig> footConfig;
  private final LayoutInflater inflater;
  private final RecyclerView.Adapter mAdapter;
  private int headCount = 0;
  private int footerCount = 0;
  private ICustomClickListener customClickListener;

  public CustomAdapter(List<RecyclerViewConfig> headConfig, List<RecyclerViewConfig> footConfig,
      RecyclerView.Adapter mAdapter, Context mContext, RecyclerView recyclerView) {
    this.mAdapter = mAdapter;
    this.inflater = LayoutInflater.from(mContext);
    if (headConfig == null) {
      this.headConfig = new ArrayList<>();
    } else {
      this.headConfig = headConfig;
    }
    if (footConfig == null) {
      this.footConfig = new ArrayList<>();
    } else {
      this.footConfig = footConfig;
    }
  }

  /**
   * 设置监听事件
   */
  public void setCustomClickListener(ICustomClickListener customClickListener) {
    this.customClickListener = customClickListener;
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int index) {
    if (index == RecyclerViewConfig.HEADVIEW_TYPE) {
      FrameLayout contentView =
          (FrameLayout) inflater.inflate(R.layout.item_head_foot_parent, viewGroup, false);
      contentView.setTag(contentView.getClass() + "_head_" + headCount);
      View cView = headConfig.get(headCount).getContentView();
      ViewGroup vg = (ViewGroup) cView.getParent();
      if (vg != null) {
        vg.removeView(cView);
      }
      contentView.addView(cView);
      CustomViewHolder customViewHolder = new CustomViewHolder(contentView);
      customViewHolder.setIsRecyclable(headConfig.get(headCount).isCache());
      headCount += 1;
      if (headCount > headConfig.size() - 1) {
        headCount = 0;
      }
      return customViewHolder;
    } else if (index == RecyclerViewConfig.FOOTVIEW_TYPE) {
      FrameLayout contentView =
          (FrameLayout) inflater.inflate(R.layout.item_head_foot_parent, viewGroup, false);
      contentView.setTag(contentView.getClass() + "_foot_" + footerCount);
      View cView = footConfig.get(footerCount).getContentView();
      ViewGroup vg = (ViewGroup) cView.getParent();
      if (vg != null) {
        vg.removeView(cView);
      }
      contentView.addView(cView);
      CustomViewHolder customViewHolder = new CustomViewHolder(contentView);
      customViewHolder.setIsRecyclable(footConfig.get(footerCount).isCache());
      footerCount += 1;
      if (footerCount > footConfig.size() - 1) {
        footerCount = 0;
      }
      return customViewHolder;
    } else {
      return mAdapter.onCreateViewHolder(viewGroup, index);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
    CustomViewHolder customViewHolder;
    if (viewHolder instanceof CustomViewHolder) {
      customViewHolder = (CustomViewHolder) viewHolder;
      customViewHolder.setIsRecyclable(true);
      View mParent = customViewHolder.mParent;
      //设置view的点击事件
      if (mParent instanceof ViewGroup) {
        ViewGroup parentGroup = (ViewGroup) mParent;
        int childCount = parentGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
          parentGroup.getChildAt(i).setOnClickListener(v -> {
            if (customClickListener != null) {
              if (position < getHeadSize()) {
                customClickListener.onClick(v, position, 0);
              } else {
                customClickListener.onClick(v, position - getHeadSize() - mAdapter.getItemCount(),
                    1);
              }
            }
          });
        }
      }
      mParent.setOnClickListener(v -> {
        if (position < getHeadSize()) {
          customClickListener.onClick(v, position, 0);
        } else {
          customClickListener.onClick(v, position - getHeadSize() - mAdapter.getItemCount(), 1);
        }
      });
    } else {
      mAdapter.onBindViewHolder(viewHolder, position - getHeadSize());
    }
  }

  @Override
  public int getItemCount() {
    return mAdapter != null
        ? headConfig.size() + mAdapter.getItemCount() + footConfig.size()
        : headConfig.size() + footConfig.size();
  }

  public static class CustomViewHolder extends RecyclerView.ViewHolder {
    View mParent;

    public CustomViewHolder(@NonNull View itemView) {
      super(itemView);
      mParent = itemView;
    }
  }

  @Override
  public int getItemViewType(int position) {
    int headSize = getHeadSize();
    int adapterCount = getItemCount();//获取实际的个数
    if (position < headSize) {
      return RecyclerViewConfig.HEADVIEW_TYPE;
    } else if (position >= headSize + mAdapter.getItemCount() && position < adapterCount) {
      return RecyclerViewConfig.FOOTVIEW_TYPE;
    }
    return mAdapter.getItemViewType(position - headSize);
  }

  public int getHeadSize() {
    return headConfig.size();
  }

  public View getIndexHeadView(int index) {
    return headConfig.get(index).getContentView();
  }

  public View getIndexFootView(int index) {
    return footConfig.get(index).getContentView();
  }

  public int getFootSize() {
    return footConfig.size();
  }

  /**
   * 适配GridLayoutManager
   */
  @Override
  public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
    if (layoutManager instanceof GridLayoutManager) {
      final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
      final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
      gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
          if (getHeadSize() > position) {
            return gridLayoutManager.getSpanCount();
          } else if (position >= getHeadSize() + mAdapter.getItemCount()) {
            return gridLayoutManager.getSpanCount();
          }
          if (spanSizeLookup != null) return spanSizeLookup.getSpanSize(position);
          return 1;
        }
      });
      gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
    }
  }

  /**
   * 适配StaggeredGridLayoutManager
   */
  @Override
  public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
    super.onViewAttachedToWindow(holder);
    int position = holder.getLayoutPosition();
    if (getHeadSize() > position || position >= getHeadSize() + mAdapter.getItemCount()) {
      ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
      if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
        StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
        p.setFullSpan(true);
      }
    }
  }
}
