package com.jmm.client.module.gold.activity;


import android.animation.IntEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jmm.client.R;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.module.gold.RvItemDecoration;
import com.jmm.client.module.gold.adapter.RcSingleAdapter;
import com.jmm.client.widget.ToolBarLayout;
import com.jmm.client.widget.banner.MZBannerView;
import com.jmm.client.widget.banner.holder.MZHolderCreator;
import com.jmm.client.widget.banner.holder.MZViewHolder;
import com.jmm.common.utils.DensityUtils;
import com.jmm.common.utils.SpannableStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * 单品回购详情
 */
public class RepurchaseSingleActivity extends BaseActivity {

    //mText底部距离屏幕顶端的高度
    int mTextOnScreen = 0;
    MZBannerView mBanner;
    TextView mTvPercent;
    ProgressBar mProgressBar2;

    @BindView(R.id.state_bar)
    View mStateBar;
    @BindView(R.id.toolbar)
    ToolBarLayout mToolbar;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.text)
    TextView mText;
    TextView mTvCompletion;
    TextView mTvResidueDays;


    @Override
    public int getLayoutId() {
        return R.layout.activity_repurchase_single;
    }

    @Override
    public void initView() {
        mToolbar.setBackgroundColor(Color.argb(0, 255, 255, 255));
        mStateBar.setBackgroundColor(Color.argb(0, 141, 141, 141));
        mToolbar.setTitleColor(Color.argb(0, 0, 0, 0));
        Window window = getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
    }

    @Override
    public void initData() {
        initRv();
        initBanner();
        initToolbar();

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

    class BannerViewHolder implements MZViewHolder<Integer> {
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

        public int getRandomColor() {
            int max = 230, min = 30;
            Random random = new Random();
            int red = random.nextInt(max) % (max - min + 1) + min;
            int green = random.nextInt(max) % (max - min + 1) + min;
            int blue = random.nextInt(max) % (max - min + 1) + min;
            int alpha = random.nextInt(max) % (max - min + 1) + min;
            return Color.argb(alpha, red, green, blue);
        }
    }

    boolean flag = true;
    int count = 0;      //Tab的计数

    private void initToolbar() {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mBanner.measure(w, h);
        mText.measure(w, h);
        int measuredHeight = mBanner.getMeasuredHeight();
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
                int position = layoutManager.findFirstVisibleItemPosition();
                View firstVisiableChildView = layoutManager.findViewByPosition(position);
                Integer evaluate = Math.min(new IntEvaluator().evaluate((float) rv.computeVerticalScrollOffset() / measuredHeight, 0, 255), 255);
                mToolbar.setBackgroundColor(Color.argb(evaluate, 255, 255, 255));
                mStateBar.setBackgroundColor(Color.argb((int) (evaluate * 0.95), 141, 141, 141));
                mToolbar.setTitleColor(Color.argb(evaluate, 0, 0, 0));
                int bottom = firstVisiableChildView.getBottom();
                if (mTextOnScreen != 0) {
                    flag = count == position;
                    if (bottom < mTextOnScreen && flag) {
                        count++;
                    }
                    if (bottom > mTextOnScreen && !flag) {
                        count--;
                    }
                }
                if (count > 0) {
                    mText.setText("详情---" + count);
                    mText.setVisibility(View.VISIBLE);
                    System.out.println();
                } else {
                    mText.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private void setProgressBar(int progress) {
        mTvPercent.setText(progress + "%");
        mProgressBar2.setProgress(progress);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mTvPercent.measure(w, h);
        int left = mProgressBar2.getWidth() * progress / 100 + DensityUtils.dp2px(mContext, 20) - mTvPercent.getMeasuredWidth() / 2;
        lp.setMargins(left, 0, 0, 0);
        mTvPercent.setLayoutParams(lp);
    }

    private void initRv() {
        List list = new ArrayList();
        for (int i = 0; i < 5; i++) {
            list.add("1");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(linearLayoutManager);
        mRv.addItemDecoration(new RvItemDecoration(mContext));
        RcSingleAdapter myAdapter = new RcSingleAdapter(list);
        View header = LayoutInflater.from(this).inflate(R.layout.item_rcsingle_header, null);
        mTvPercent = (TextView) header.findViewById(R.id.tv_percent);
        mProgressBar2 = (ProgressBar) header.findViewById(R.id.progressBar2);
        mBanner = (MZBannerView) header.findViewById(R.id.banner);
        mTvCompletion = (TextView) header.findViewById(R.id.tv_completion);
        mTvResidueDays = (TextView) header.findViewById(R.id.tv_residue_days);
        myAdapter.addHeaderView(header);
        mRv.setAdapter(myAdapter);
        mTvCompletion.append(new SpannableStringUtils.Builder().setForegroundColor(Color.RED).append("800,000").create());
        mTvResidueDays.append(new SpannableStringUtils.Builder().setForegroundColor(Color.RED).append("2天").create());
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setProgressBar(100);
        mTextOnScreen = mText.getBottom();
    }
}
