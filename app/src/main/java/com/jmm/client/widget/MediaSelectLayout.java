package com.jmm.client.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmm.client.R;
import com.jmm.client.image.bean.MediaItem;

import java.util.ArrayList;
import java.util.List;


public class MediaSelectLayout extends FlowLayout implements View.OnClickListener {

    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_VIDEO = 1;
    private static final int TYPE_ALL = 2;
    private int column = 3;
    private int imageMaxCount = 5;
    private int videoMaxCount = 1;
    public ViewGroup.LayoutParams params;
    private View addImageView;
    private View addVideoView;
    private int selectType;
    private List<MediaItem> imageList = new ArrayList<>();
    private List<MediaItem> videoList = new ArrayList<>();
    private boolean isMeasure;

    public MediaSelectLayout(Context context) {
        this(context, null);
    }

    public MediaSelectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MediaSelectLayout);
        column = ta.getInteger(R.styleable.MediaSelectLayout_column, column); //现在的列数,默认为3
        imageMaxCount = ta.getInteger(R.styleable.MediaSelectLayout_image_maxCount, imageMaxCount); // 图片选择的最大数量 默认为5
        videoMaxCount = ta.getInteger(R.styleable.MediaSelectLayout_video_maxCount, videoMaxCount); // 视频选择的最大数量 默认为1
        selectType = ta.getInt(R.styleable.MediaSelectLayout_select_type, TYPE_IMAGE); // 选择类型 默认为图片选择
        ta.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isMeasure) {
            int childWidth = (getMeasuredWidth() - (getPaddingLeft() + getPaddingRight() +
                    (column - 1) * mHorizontalChildGap)) / column;
            params = new LayoutParams(childWidth, childWidth);

            if (selectType == TYPE_IMAGE || selectType == TYPE_ALL) {
                addImageButtonView();
            }

            if (selectType == TYPE_VIDEO || selectType == TYPE_ALL) {
                addVideoButtonView();
            }
        }
        isMeasure = true;
    }

    /**
     * 添加图片选择按钮
     */
    private void addImageButtonView() {
        addImageView = inflate(getContext(), R.layout.select_add_layout, null);
        ((TextView) addImageView.findViewById(R.id.tv_text)).setText("添加图片");
        addImageView.setLayoutParams(params);
        addView(addImageView);
        addImageView.setOnClickListener(imageAddClickListener);
    }

    /**
     * 添加视频选择按钮
     */
    private void addVideoButtonView() {
        addVideoView = inflate(getContext(), R.layout.select_add_layout, null);
        ((TextView) addVideoView.findViewById(R.id.tv_text)).setText("添加视频");
        addVideoView.setLayoutParams(params);
        addView(addVideoView);
        addVideoView.setOnClickListener(videoAddClickListener);
    }


//    public void setColumnNum(int column) {
//        this.column = column;
//        removeView(addImageView);
//        init();
//    }


    public void addItem(MediaItem item) {
        if (initListener == null) return;
        View itemView;
        int insertPosition;
        if (item.getType() == MediaItem.MediaType.image) {
            itemView = initListener.getImageItemView(item, 0);
            insertPosition = imageList.size();
            addView(itemView, insertPosition, params);
            imageList.add(item);
            if (imageList.size() == imageMaxCount) {
                removeView(addImageView);
            }
            itemView.setTag(item);
            itemView.setOnClickListener(this);
        } else {
            itemView = initListener.getVideoItemView(item, 0);
            if (imageList.size() == imageMaxCount) {
                insertPosition = imageList.size() + videoList.size();
            } else {
                if (selectType == TYPE_ALL || selectType == TYPE_IMAGE) {
                    insertPosition = imageList.size() + videoList.size() + 1;
                } else {
                    insertPosition = imageList.size() + videoList.size();
                }
            }
            addView(itemView, insertPosition, params);
            videoList.add(item);
            if (videoList.size() == videoMaxCount) {
                removeView(addVideoView);
            }
            itemView.setTag(item);
            itemView.setOnClickListener(this);
        }

        System.out.println("imageList->" + imageList.size());
        System.out.println("videoList->" + videoList.size());
    }

    public void addItemList(List<MediaItem> list) {
        for (MediaItem mediaItem : list) {
            addItem(mediaItem);
        }
    }

    private View.OnClickListener imageAddClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (initListener != null) {
                initListener.addImageClick();
            }
        }
    };

    private View.OnClickListener videoAddClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (initListener != null) {
                initListener.addVideoClick();
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (initListener == null) return;
        MediaItem item = (MediaItem) v.getTag();
        switch (item.getType()) {
            case image:
                initListener.imageItemClick(v, 0);
                break;
            case video:
                initListener.videoItemClick(v, 0);
                break;
        }
    }

    public interface InitListener {

        void addImageClick();

        void addVideoClick();

        void imageItemClick(View view, int position);

        void videoItemClick(View view, int position);

        View getImageItemView(Object val, int position);

        View getVideoItemView(Object val, int position);
    }

    private InitListener initListener;

    public void setInitList(InitListener listener) {
        this.initListener = listener;
    }


    public List<MediaItem> getData() {
        List<MediaItem> totalList = new ArrayList<>();
        totalList.addAll(imageList);
        totalList.addAll(videoList);
        return totalList;
    }

    /**
     * 获取已选择视频List
     */
    public List<MediaItem> getVideoList() {
        return videoList;
    }

    /**
     * 获取已选择图片List
     */
    public List<MediaItem> getImageList() {
        return imageList;
    }

    /**
     * 获取图片剩余可选择数量
     */
    public int getImageSurplusCount() {
        return imageMaxCount - imageList.size();
    }

    /**
     * 获取视频剩余可选择数量
     */
    public int getVideoSurplusCount() {
        return videoMaxCount - videoList.size();
    }


    @Override
    public void removeView(View view) {
        MediaItem item = (MediaItem) view.getTag();
        if (item == null) {
            super.removeView(view);
            return;
        }
        switch (item.getType()) {
            case image:
                if (addImageView.getParent() == null) {
                    addView(addImageView, imageList.size());
                }
                imageList.remove(item);
                break;
            case video:
                if (addVideoView.getParent() == null) {
                    int insertPosition;
                    if (imageList.size() == imageMaxCount) {
                        insertPosition = imageList.size() + videoList.size();
                    } else {
                        if (selectType == TYPE_ALL || selectType == TYPE_IMAGE) {
                            insertPosition = imageList.size() + videoList.size() + 1;
                        } else {
                            insertPosition = imageList.size() + videoList.size();
                        }
                    }
                    addView(addVideoView, insertPosition);
                }
                videoList.remove(item);
                break;
        }
        super.removeView(view);
    }
}
