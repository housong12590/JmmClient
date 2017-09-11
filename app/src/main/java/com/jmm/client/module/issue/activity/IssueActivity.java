package com.jmm.client.module.issue.activity;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmm.client.R;
import com.jmm.client.helper.LocationHelper;
import com.jmm.client.helper.TabLayoutHelper;
import com.jmm.client.image.activity.ImagePreviewActivity;
import com.jmm.client.image.bean.MediaItem;
import com.jmm.client.image.load.ImageLoaderUtils;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.module.issue.fragment.FixedPriceFragment;
import com.jmm.client.module.video.VideoRecordActivity;
import com.jmm.client.widget.ChildViewPager;
import com.jmm.client.widget.MediaSelectLayout;
import com.jmm.client.widget.MessageDialog;
import com.jmm.client.widget.ToolBarLayout;
import com.jmm.common.utils.AppUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class IssueActivity extends BaseActivity implements MediaSelectLayout.InitListener, ViewPager.OnPageChangeListener {

    private static final int VIDEO_RECORD_CODE = 1001;

    @BindView(R.id.toolbar)
    ToolBarLayout toolbar;
    @BindView(R.id.customLayout)
    MediaSelectLayout customLayout;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ChildViewPager viewPager;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    private String[] issueType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_issue;
    }

    @Override
    public void initView() {
        customLayout.setInitList(this);
        IssueAdapter issueAdapter = new IssueAdapter(getSupportFragmentManager());
        issueType = getResources().getStringArray(R.array.issue_type_array);
        viewPager.setAdapter(issueAdapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.postDelayed(() -> TabLayoutHelper.setIndicator(tabLayout, mContext, 20, 20), 1);
    }

    @Override
    public void initData() {
        //定位
        LocationHelper
                .getInstance()
                .initLocationHelper(mContext)
                .setLocationListener(location -> {
            mTvAddress.setText(location.getProvince() + location.getCity() + location.getDistrict());
        });
    }

    @Override
    public void addImageClick() {
        PictureSelector.create(mContext)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(customLayout.getImageSurplusCount())
                .imageSpanCount(4)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void addVideoClick() {
        new RxPermissions(mContext).request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        start();
                    } else {
                        new MessageDialog(mContext)
                                .setTitle("帮助")
                                .setMessage(getString(R.string.permission_state, "相机权限或者录音权限"))
                                .setCancelButtonListener("取消", null)
                                .setAffirmButtonListener("设置", v -> AppUtils.getAppDetailsSettings(getPackageName()))
                                .show();
                    }
                });
    }

    private void start() {
        startActivityForResult(new Intent(this, VideoRecordActivity.class), VIDEO_RECORD_CODE);
    }

    @Override
    public void imageItemClick(View view, int position) {
        ArrayList<String> list = new ArrayList<>();
        for (MediaItem item : customLayout.getImageList()) {
            list.add(item.getPath());
        }
        ImagePreviewActivity.start(mContext, list, customLayout.indexOfChild(view));
    }

    @Override
    public void videoItemClick(View view, int position) {

    }

    @Override
    public View getImageItemView(Object val, int position) {
        View view = View.inflate(mContext, R.layout.item_select_image_preview_layout, null);
        ImageView ivImage = (ImageView) view.findViewById(R.id.iv_image);
        ImageView ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
        ivDelete.setOnClickListener(v -> customLayout.removeView(view));
        MediaItem item = (MediaItem) val;
        ImageLoaderUtils.getInstance().loadImage(mContext, item.getPath(), ivImage, true);
        return view;
    }


    @Override
    public View getVideoItemView(Object val, int position) {
        View view = View.inflate(mContext, R.layout.item_select_image_preview_layout, null);
        ImageView ivImage = (ImageView) view.findViewById(R.id.iv_image);
        ImageView ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
        ivDelete.setOnClickListener(v -> customLayout.removeView(view));
        MediaItem item = (MediaItem) val;
        ImageLoaderUtils.getInstance().loadImage(mContext, item.getPath(), ivImage, true);
        return view;
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
                    customLayout.addItemList(list);
                    break;
                case VIDEO_RECORD_CODE:
                    MediaItem item = new MediaItem();
                    item.setPath(data.getStringExtra("path"));
                    item.setType(MediaItem.MediaType.video);
                    customLayout.addItem(item);
                    break;
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        viewPager.resetHeight(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class IssueAdapter extends FragmentPagerAdapter {

        public IssueAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new FixedPriceFragment();
        }

        @Override
        public int getCount() {
            return issueType.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return issueType[position];
        }
    }
}
