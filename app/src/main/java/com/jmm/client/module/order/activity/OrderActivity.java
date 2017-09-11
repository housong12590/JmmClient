package com.jmm.client.module.order.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jmm.client.R;
import com.jmm.client.helper.TabLayoutHelper;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.module.main.fragment.MineFragment;
import com.jmm.client.widget.ToolBarLayout;

import butterknife.BindView;

public class OrderActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    ToolBarLayout mToolbar;
    @BindView(R.id.order_tablayout)
    TabLayout mOrderTablayout;
    @BindView(R.id.order_viewpager)
    ViewPager mOrderViewpager;

    String[] orderTitles = {"全部", "待付款", "待收货", "已关闭"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void initView() {
        OrderViewPagerAdapter adapter = new OrderViewPagerAdapter(getSupportFragmentManager());
        mOrderViewpager.setAdapter(adapter);
        mOrderTablayout.postDelayed(() -> TabLayoutHelper.setIndicator(mOrderTablayout, mContext, 20, 20), 1);
        mOrderTablayout.setupWithViewPager(mOrderViewpager);
    }

    @Override
    public void initData() {

    }

    private class OrderViewPagerAdapter extends FragmentPagerAdapter {

        public OrderViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getFragment(position);
        }

        @Override
        public int getCount() {
            return orderTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return orderTitles[position];
        }
    }

    private Fragment getFragment(int position) {
        return new MineFragment();
    }

}
