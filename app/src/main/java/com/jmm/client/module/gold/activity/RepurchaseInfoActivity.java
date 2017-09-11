package com.jmm.client.module.gold.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmm.client.R;
import com.jmm.client.image.activity.ImagePreviewActivity;
import com.jmm.client.image.bean.MediaItem;
import com.jmm.client.image.load.ImageLoaderUtils;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.widget.MediaSelectLayout;
import com.jmm.common.utils.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.rxbus2.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RepurchaseInfoActivity extends BaseActivity {

    @BindView(R.id.tv_revise)
    TextView mTvRevise;
    @BindView(R.id.tv_next)
    TextView mTvNext;
    @BindView(R.id.edit_gold_weight)
    EditText mEditGoldWeight;
    @BindView(R.id.iv_category1)
    ImageView mIvCategory1;
    @BindView(R.id.iv_category2)
    ImageView mIvCategory2;
    @BindView(R.id.iv_style1)
    ImageView mIvStyle1;
    @BindView(R.id.iv_style2)
    ImageView mIvStyle2;
    @BindView(R.id.media_select)
    MediaSelectLayout mMediaSelect;
    Boolean mChooseCategory = true;
    Boolean mChooseStyle = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_repurchase_info;
    }

    @Override
    public void initView() {
        initEditText();
        initMediaSelect();
        mIvCategory1.setSelected(true);
        mIvStyle1.setSelected(true);
    }

    @Override
    public void initData() {
    }

    private void initEditText() {
        mEditGoldWeight.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
        mEditGoldWeight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEditGoldWeight.setFocusableInTouchMode(true);
                mEditGoldWeight.setFocusable(true);
                return false;
            }
        });
    }

    private void initMediaSelect() {
        mMediaSelect.setInitList(new MediaSelectLayout.InitListener() {
            @Override
            public void addImageClick() {
                PictureSelector.create(mContext)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(mMediaSelect.getImageSurplusCount())
                        .imageSpanCount(4)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }

            @Override
            public void addVideoClick() {

            }

            @Override
            public void imageItemClick(View view, int position) {
                ArrayList<String> list = new ArrayList<>();
                for (MediaItem item : mMediaSelect.getImageList()) {
                    list.add(item.getPath());
                }
                ImagePreviewActivity.start(mContext, list, mMediaSelect.indexOfChild(view));
            }

            @Override
            public void videoItemClick(View view, int position) {

            }

            @Override
            public View getImageItemView(Object val, int position) {
                View view = View.inflate(mContext, R.layout.item_select_image_preview_layout, null);
                ImageView ivImage = (ImageView) view.findViewById(R.id.iv_image);
                ImageView ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
                ivDelete.setOnClickListener(v -> mMediaSelect.removeView(view));
                MediaItem item = (MediaItem) val;
                ImageLoaderUtils.getInstance().loadImage(mContext, item.getPath(), ivImage, false);
                return view;
            }

            @Override
            public View getVideoItemView(Object val, int position) {
                return null;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    List<MediaItem> list = new ArrayList<>();
                    for (LocalMedia localMedia : selectList) {
                        MediaItem item = new MediaItem();
                        item.setPath(localMedia.getPath());
                        if (localMedia.getPictureType().contains("image")) {

                            item.setType(MediaItem.MediaType.image);
                        } else {
                            item.setType(MediaItem.MediaType.video);
                        }
                        list.add(item);
                    }
                    mMediaSelect.addItemList(list);
                    break;
            }
        }
    }

    @OnClick({R.id.iv_category1, R.id.iv_category2, R.id.iv_style1, R.id.iv_style2, R.id.tv_revise, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_revise:
                ToastUtils.showShort("修改");
                break;
            case R.id.tv_next:
                startActivity(new Intent(this, RepurchaseInputInfoActivity.class));
                RxBus.getDefault().postSticky(mMediaSelect.getImageList());
                break;
            case R.id.iv_category1:
                if (mChooseCategory) {
                } else {
                    mChooseCategory = true;
                    mIvCategory1.setSelected(true);
                    mIvCategory2.setSelected(false);
                }
                break;
            case R.id.iv_category2:
                if (mChooseCategory) {
                    mChooseCategory = false;
                    mIvCategory1.setSelected(false);
                    mIvCategory2.setSelected(true);
                }
                break;
            case R.id.iv_style1:
                if (mChooseStyle) {
                } else {
                    mChooseStyle = true;
                    mIvStyle1.setSelected(true);
                    mIvStyle2.setSelected(false);
                }
                break;
            case R.id.iv_style2:
                if (mChooseStyle) {
                    mChooseStyle = false;
                    mIvStyle1.setSelected(false);
                    mIvStyle2.setSelected(true);
                }
                break;
        }
    }
}
