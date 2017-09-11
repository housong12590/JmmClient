package com.jmm.client.module.gold.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jmm.client.R;
import com.jmm.client.image.activity.ImagePreviewActivity;
import com.jmm.client.image.bean.MediaItem;
import com.jmm.client.image.load.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 产品外观
 */

public class OutlineImagAdapter extends RecyclerView.Adapter<OutlineImagAdapter.ViewHolder> {

    ArrayList<String> mlist = new ArrayList<>();
    Context mContext;

    public OutlineImagAdapter(List<MediaItem> mlist,Context context) {
        if (this.mlist.size() == 0) {
            for (int i = 0; i < mlist.size(); i++) {
                this.mlist.add(mlist.get(i).getPath());
            }
        }
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_produc_detailst, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mItemIvDetailst.setAdjustViewBounds(true);
        ImageLoaderUtils.getInstance().loadImage(mContext, mlist.get(position), holder.mItemIvDetailst, false);
        holder.mItemIvDetailst.setOnClickListener(v -> ImagePreviewActivity.start(mContext, mlist, position));
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_iv_detailst)
        ImageView mItemIvDetailst;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
