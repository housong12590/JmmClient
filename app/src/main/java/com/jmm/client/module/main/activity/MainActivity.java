package com.jmm.client.module.main.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jmm.client.R;
import com.jmm.client.config.StorageConfig;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.module.issue.activity.IssueActivity;
import com.jmm.client.module.main.fragment.CircleFragment;
import com.jmm.client.module.main.fragment.HomeFragment;
import com.jmm.client.module.main.fragment.MessageFragment;
import com.jmm.client.module.main.fragment.MineFragment;
import com.jmm.client.widget.MessageDialog;
import com.jmm.common.utils.AppUtils;
import com.luck.picture.lib.permissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.radioGroup) RadioGroup radioGroup;
    @BindView(R.id.rb_home) RadioButton mRbHome;
    List<Fragment> fragments = new ArrayList<>();

    private long mExitTime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mRbHome.setChecked(true);
        radioGroup.setOnCheckedChangeListener(this);
        fragments.add(new HomeFragment());
        fragments.add(new CircleFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MineFragment());
        viewPager.setOffscreenPageLimit(fragments.size() - 1);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }


    @Override
    public void initData() {

    }

    @OnClick(R.id.tv_issue)
    public void onClick() {
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        StorageConfig.initAppDirectory();
                        startActivity(new Intent(mContext, IssueActivity.class));
                    } else {
                        new MessageDialog(mContext).setTitle("帮助")
                                .setMessage(getString(R.string.permission_state, "存储权限访问相册"))
                                .setCancelButtonListener("取消", null)
                                .setAffirmButtonListener("设置", v ->
                                        AppUtils.getAppDetailsSettings(getPackageName())).show();
                    }
                });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rb_circle:
                viewPager.setCurrentItem(1);
                break;
            case R.id.rb_message:
                viewPager.setCurrentItem(2);
                break;
            case R.id.rb_mine:
                viewPager.setCurrentItem(3);
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
