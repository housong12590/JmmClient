package com.jmm.client.module.issue.activity;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jmm.client.R;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.widget.NoScrollRecyclerView;
import com.jmm.client.widget.ShareDialog;
import com.jmm.client.widget.ToolBarLayout;
import com.jmm.common.utils.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品详情
 */
public class ProducDetailstActivity extends BaseActivity {

    @BindView(R.id.produc_detailst_rv)
    NoScrollRecyclerView mProducDetailstRv;
    ArrayList<String> alist = new ArrayList<>();
    @BindView(R.id.produc_detailst_rv_message)
    RecyclerView mProducDetailstRvMessage;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.toolbar)
    ToolBarLayout mToolbar;
    @BindView(R.id.produc_detailst_ll)
    LinearLayout mProducDetailLl;
    @BindView(R.id.tv_leave_message)
    TextView mTvLeaveMessage;
    @BindView(R.id.pd_rl)
    RelativeLayout mPdRl;
    @BindView(R.id.pd_edit)
    EditText mPdEdit;
    @BindView(R.id.pd_iv_keydown)
    ImageView mPdIvKeydown;

    @Override
    public int getLayoutId() {
        return R.layout.activity_produc_detailst;
    }

    @Override
    public void initView() {
        mToolbar.addRightImageButton(R.drawable.icon_share, R.id.menu_share);
        mToolbar.setOnActionListener(new ToolBarLayout.OnActionListener() {
            @Override
            public void onClick(View view) {
                new ShareDialog(ProducDetailstActivity.this, "url").show();
            }
        });
        mScrollView.smoothScrollTo(0, 0);
    }

    @Override
    public void initData() {
        for (int i = 0; i < 10; i++) {
            alist.add("http://imgsrc.baidu.com/forum/pic/item/a8773912b31bb051284cbc8c367adab44aede02e.jpg");
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (alist.size() % 2 == 0) {
                    return position > 3 ? 1 : 2;
                } else {
                    return position > 2 ? 1 : 2;
                }
            }
        });
        layoutManager.setAutoMeasureEnabled(true);
        mProducDetailstRv.setLayoutManager(layoutManager);
        MyAdapter adapter = new MyAdapter();
        mProducDetailstRv.setAdapter(adapter);
        initMessageRv();
    }

    private void initMessageRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mProducDetailstRvMessage.setLayoutManager(linearLayoutManager);
        MyMessageAdapter myMessageAdapter = new MyMessageAdapter(alist);
        mProducDetailstRvMessage.setAdapter(myMessageAdapter);
        myMessageAdapter.setEnableLoadMore(true);
        myMessageAdapter.disableLoadMoreIfNotFullPage(mProducDetailstRvMessage);
        myMessageAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mProducDetailstRvMessage.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            alist.add("1");
                        }
                        myMessageAdapter.setNewData(alist);
                        myMessageAdapter.loadMoreComplete();
                    }
                }, 2000);
            }
        }, mProducDetailstRvMessage);
    }

    @OnClick({R.id.pd_iv_keydown, R.id.tv_leave_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pd_iv_keydown:
                KeyboardUtils.hideSoftInput(this);
                mPdRl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mProducDetailLl.setVisibility(View.GONE);
                        mPdRl.setVisibility(View.VISIBLE);
                    }
                }, 100);
                break;
            case R.id.tv_leave_message:
                KeyboardUtils.showSoftInput(this);
                mProducDetailLl.setVisibility(View.VISIBLE);
                mPdRl.setVisibility(View.GONE);
                mPdEdit.setFocusable(true);
                mPdEdit.setFocusableInTouchMode(true);
                mPdEdit.requestFocus();//获取焦点 光标出现
                break;
        }
    }

    class MyMessageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


        public MyMessageAdapter(@Nullable List<String> data) {
            super(R.layout.item_produc_message, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_produc_detailst, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
//            holder.mItemIvDetailst.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
            holder.mItemIvDetailst.setAdjustViewBounds(true);
            Glide.with(ProducDetailstActivity.this)
                    .load("http://imgsrc.baidu.com/forum/pic/item/a8773912b31bb051284cbc8c367adab44aede02e.jpg")
                    .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                    .into(holder.mItemIvDetailst);

        }

        @Override
        public int getItemCount() {
            return alist.size();
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

}
