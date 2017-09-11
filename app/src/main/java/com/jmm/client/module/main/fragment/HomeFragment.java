package com.jmm.client.module.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jmm.client.R;
import com.jmm.client.helper.TabLayoutHelper;
import com.jmm.client.http.bean.LoadStatus;
import com.jmm.client.module.TestFragment;
import com.jmm.client.module.base.fragment.DelayFragment;
import com.jmm.client.module.gold.activity.RepurchaseSingleActivity;
import com.jmm.client.module.main.activity.SearchActivity;
import com.jmm.client.widget.ChildViewPager;
import com.jmm.client.widget.StickyScrollViewList;
import com.jmm.client.widget.ToolBarLayout;
import com.jmm.client.widget.banner.MZBannerView;
import com.jmm.client.widget.banner.holder.MZHolderCreator;
import com.jmm.client.widget.banner.holder.MZViewHolder;
import com.jmm.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;



public class HomeFragment extends DelayFragment {


    @BindView(R.id.banner)
    MZBannerView mBanner;
    @BindView(R.id.home_tablayout)
    TabLayout mHomeTablayout;
    @BindView(R.id.home_viewpager)
    ChildViewPager mHomeViewpager;
    @BindView(R.id.home_scrollview)
    StickyScrollViewList mHomeScrollview;
    @BindView(R.id.toolbar)
    ToolBarLayout mToolbar;
    @BindView(R.id.home_ll_single)
    LinearLayout mHomeLlSingle;

    private TestFragment mTestFragment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        System.out.println("initView");
        initBanner();
        initToolbar();
        mHomeTablayout.postDelayed(() -> TabLayoutHelper.setIndicator(mHomeTablayout, mContext, 40, 40), 1);
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getChildFragmentManager());
        mHomeViewpager.setAdapter(adapter);
        mHomeTablayout.setupWithViewPager(mHomeViewpager);
        if (Build.VERSION.SDK_INT <= 21) {
            mHomeScrollview.smoothScrollTo(0, 0);
        }
        mHomeLlSingle.setOnClickListener(v -> startActivity(new Intent(getActivity(), RepurchaseSingleActivity.class)));
    }

    @Override
    public void initData() {
    }

    @Override
    protected void requestData(LoadStatus status) {

    }


    private void initBanner() {
        List list = new ArrayList();
        for (int i = 0; i < 5; i++) {
            list.add(R.drawable.icon_delete);
        }

        // 设置数据
        mBanner.setPages(list, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }

    public class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_produc_detailst, null);
            mImageView = (ImageView) view.findViewById(R.id.item_iv_detailst);
            ViewGroup.LayoutParams layoutParams = mImageView.getLayoutParams();
            layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
            mImageView.setBackgroundColor(getRandomColor());
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mBanner.start();//开始轮播
    }

    class HomeViewPagerAdapter extends FragmentPagerAdapter {

        public HomeViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            mTestFragment = new TestFragment();
            return mTestFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "最新的" : "附近的";
        }
    }

    public int getRandomColor() {
        int max = 230, min = 30;
        Random random = new Random();
        int red = random.nextInt(max) % (max - min + 1) + min;
        int green = random.nextInt(max) % (max - min + 1) + min;
        int blue = random.nextInt(max) % (max - min + 1) + min;
        int alpha = random.nextInt(max) % (max - min + 1) + min;
        return Color.argb(alpha, red, green, blue);
    }

    private void initToolbar() {
        //初始化左边导航
        mToolbar.addRightTextAndImageButton("消息", R.drawable.icon_message, R.id.menu_message,
                ToolBarLayout.Type.TOP, 0);
        mToolbar.addLeftTextAndImageButton("更多", R.drawable.icon_more, R.id.menu_more,
                ToolBarLayout.Type.TOP, 0);

        View searchView = View.inflate(getActivity(), R.layout.item_home_search_view, null);
        mToolbar.addCenterView(searchView);
        //设置点击事件
        mToolbar.setOnActionListener(view -> {
            switch (view.getId()) {
                case R.id.menu_message:
                    ToastUtils.showShort("消息");
                    break;
                case R.id.menu_more:
                    ToastUtils.showShort("更多");
                    break;
            }
        });
        searchView.setOnClickListener(v -> startActivity(new Intent(getActivity(), SearchActivity.class)));
    }
}
