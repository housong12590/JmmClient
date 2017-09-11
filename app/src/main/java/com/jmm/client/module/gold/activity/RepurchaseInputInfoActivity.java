package com.jmm.client.module.gold.activity;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmm.client.R;
import com.jmm.client.image.activity.ImagePreviewActivity;
import com.jmm.client.image.load.ImageLoaderUtils;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.widget.ToolBarLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 黄金回购--填写信息
 */
public class RepurchaseInputInfoActivity extends BaseActivity {
    public static final int FRONT = 0;
    public static final int REVERSE = 1;
    public static final String AUDIT_STATE = "audit_state";
    ArrayList<String> alist = new ArrayList<>();

    @BindView(R.id.toolbar)
    ToolBarLayout mToolbar;
    @BindView(R.id.iv_front)
    ImageView mIvFront;
    @BindView(R.id.iv_reverse)
    ImageView mIvReverse;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.fl_front)
    FrameLayout mFlFront;
    @BindView(R.id.fl_reverse)
    FrameLayout mFlReverse;
    @BindView(R.id.ib_front)
    ImageButton mIbFront;
    @BindView(R.id.ib_reverse)
    ImageButton mIbReverse;

    @Override
    public int getLayoutId() {
        return R.layout.activity_repurchase_input_info;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.fl_front, R.id.fl_reverse, R.id.tv_commit, R.id.ib_front, R.id.ib_reverse, R.id.iv_reverse, R.id.iv_front})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_front:
                PictureSelector.create(mContext)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)
                        .imageSpanCount(3)
                        .forResult(FRONT);
                break;
            case R.id.fl_reverse:
                PictureSelector.create(mContext)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)
                        .imageSpanCount(3)
                        .forResult(REVERSE);
                break;
            case R.id.ib_front:
                mIvFront.setImageDrawable(null);
                mIvFront.setVisibility(View.GONE);
                mIbFront.setImageDrawable(null);
                alist.remove(0);
                break;
            case R.id.ib_reverse:
                mIvReverse.setVisibility(View.GONE);
                mIvReverse.setImageDrawable(null);
                mIbReverse.setImageDrawable(null);
                if (alist.size() == 1) {
                    alist.remove(0);
                } else {
                    alist.remove(1);
                }
                break;
            case R.id.iv_front:
                ImagePreviewActivity.start(this, alist, 0);
                break;
            case R.id.iv_reverse:
                if (alist.size() == 1) {
                    ImagePreviewActivity.start(this, alist, 0);
                } else {
                    ImagePreviewActivity.start(this, alist, 1);
                }
                break;
            case R.id.tv_commit:
                Intent intent = new Intent(this, RcOrderDetailsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case FRONT:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    String path = selectList.get(0).getPath();
                    if (alist.size() == 0) {
                        alist.add(path);
                    } else {
                        alist.add(0, path);
                    }
                    ImageLoaderUtils.getInstance().loadImage(this, path, mIvFront, false);
                    mIvFront.setVisibility(View.VISIBLE);
                    mIbFront.setImageDrawable(getResources().getDrawable(R.drawable.icon_delete));
                    break;
                case REVERSE:
                    // 图片选择结果回调
                    List<LocalMedia> selectList1 = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    String path1 = selectList1.get(0).getPath();
                    alist.add(path1);
                    ImageLoaderUtils.getInstance().loadImage(this, path1, mIvReverse, false);
                    mIvReverse.setVisibility(View.VISIBLE);
                    mIbReverse.setImageDrawable(getResources().getDrawable(R.drawable.icon_delete));
                    break;
            }
        }
    }
}
