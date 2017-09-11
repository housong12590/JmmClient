package com.jmm.client.image.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jmm.client.R;
import com.jmm.client.image.load.ImageLoaderUtils;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.common.utils.DensityUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ImagePreviewActivity extends BaseActivity {

    private static final String IMAGE_LIST = "imageList";
    private static final String CURR_POSITION = "currentPosition";
    private ArrayList<String> imageList;
    private int currentPosition;

    @BindView(R.id.viewPager) ViewPager viewPager;

    @Override
    public void parseIntent(Intent intent) {
        imageList = intent.getStringArrayListExtra(IMAGE_LIST);
        currentPosition = intent.getIntExtra(CURR_POSITION, 0);
    }

    public static void start(Context context, ArrayList<String> list, int currentPosition) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.putExtra(IMAGE_LIST, list);
        intent.putExtra(CURR_POSITION, currentPosition);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_preview;
    }

    @Override
    public void initView() {
        viewPager.setPageMargin(DensityUtils.dp2px(mContext, 5));
        viewPager.setAdapter(new ImagePreviewAdapter());
        viewPager.setCurrentItem(currentPosition);
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.iv_back)
    public void onClick(View view) {
        finish();
    }

    private class ImagePreviewAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.item_image_preview, null);
            ImageView ivImage = (ImageView) view.findViewById(R.id.iv_image);
            ImageLoaderUtils.getInstance().loadImage(mContext, imageList.get(position), ivImage, false);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
