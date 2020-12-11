package com.example.zhpan.banner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;

import com.example.zhpan.banner.R;
import com.example.zhpan.banner.databinding.ItemSlideModeBinding;
import com.example.zhpan.banner.databinding.ItemSlideModelDataBindingBinding;
import com.example.zhpan.banner.view.CornerImageView;
import com.zhpan.bannerview.BaseBannerQuickAdapter;
import com.zhpan.bannerview.BaseBannerViewHolder;

/**
 * @author DBoy
 * @date 2020/12/11
 * Class 描述 : 简单Adapter示例:
 * 这个adapter使用四种方式进行适配数据，主要是简化了 adapter的构建繁琐的问题。
 * <p>
 * 主要使用方式 继承BaseBannerQuickAdapter<T> 泛型为数据类
 * 实现 {@link #convert(BaseBannerViewHolder, Integer, int, int)} 方法进行数据填充适配
 * <p>
 * 重写{@link #getLayoutId(int)} 设置布局
 * 或者 可以重写{@link #createBindingView(Context, ViewGroup, int)} 使用其他方式:
 * <p>
 * ==== 其他方式 ====
 * 可以使用ViewBinding填充数据;
 * 可以使用DataBinding填充数据;
 * 修改{@link SimpleBannerAdapter#type} 的值进行预览效果;
 * {@link #Use_LayoutId}
 * {@link #Use_ViewBinding}
 * {@link #Use_DataBinding}
 * {@link #Use_fool}
 */
public class SimpleBannerAdapter extends BaseBannerQuickAdapter<Integer> {

    /**
     * 使用布局
     */
    private final int Use_LayoutId = 0;

    /**
     * 使用ViewBinding
     */
    private final int Use_ViewBinding = 1;

    /**
     * 使用DataBinding
     */
    private final int Use_DataBinding = 2;
    /**
     * 自己构建view
     */
    private final int Use_fool = 3;

    /**
     * 示例使用方式
     */
    private final int type = Use_ViewBinding;
    //圆角
    private final int mRoundCorner;
    //示例使用ViewBinding
    private ItemSlideModeBinding mItemViewBinding;
    //示例使用DataBinding
    private ItemSlideModelDataBindingBinding mItemViewDataBinding;

    public SimpleBannerAdapter(int roundCorner) {
        mRoundCorner = roundCorner;
    }

    @Override
    protected void convert(BaseBannerViewHolder<Integer> holder, Integer data, int position, int pageSize) {
        switch (type) {
            case Use_LayoutId:
                convertLayoutId(holder, data, position, pageSize);
                break;
            case Use_ViewBinding:
                convertViewBinding(data, position, pageSize);
                break;
            case Use_DataBinding:
                convertDataBinding(data, position, pageSize);
                break;
            case Use_fool:
                convertLayoutId(holder, data, position, pageSize);
                break;
        }

    }

    @Override
    protected View createBindingView(Context context, ViewGroup parent, int viewType) {
        switch (type) {
            case Use_fool:
                //这我就不说了
                CornerImageView cornerImageView = new CornerImageView(context);
                cornerImageView.setId(R.id.banner_image);
                cornerImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                cornerImageView.setLayoutParams(params);
                return cornerImageView;
            case Use_ViewBinding:
                //使用ViewBinding创建View
                mItemViewBinding = ItemSlideModeBinding.inflate(LayoutInflater.from(context), parent, false);
                return mItemViewBinding.getRoot();
            case Use_DataBinding:
                //使用DataBinding创建View
                mItemViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_slide_model_data_binding, parent, false);
                return mItemViewDataBinding.getRoot();
            default:
                //  LayoutId 返回了确切的布局id 是不执行 createBindingView的
                return null;
        }

    }

    @Override
    public int getLayoutId(int viewType) {
        if (type == Use_LayoutId) {
            return R.layout.item_slide_mode;
        } else {
            return super.getLayoutId(viewType);
        }
    }

    /**
     * 使用布局id填充数据
     */
    private void convertLayoutId(BaseBannerViewHolder<Integer> holder, Integer data, int position, int pageSize) {
        CornerImageView imageView = holder.findView(R.id.banner_image);
        imageView.setRoundCorner(mRoundCorner);
        imageView.setImageResource(data);
    }

    /**
     * 使用ViewBinding填充数据
     */
    private void convertViewBinding(Integer data, int position, int pageSize) {
        mItemViewBinding.bannerImage.setRoundCorner(mRoundCorner);
        mItemViewBinding.bannerImage.setImageResource(data);
    }

    /**
     * 使用DataBinding填充数据
     */
    private void convertDataBinding(Integer data, int position, int pageSize) {
        mItemViewDataBinding.bannerImage.setRoundCorner(mRoundCorner);
        mItemViewDataBinding.bannerImage.setImageResource(data);
    }


}

