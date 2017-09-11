package com.jmm.client.module.base.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.jmm.client.R;
import com.jmm.client.module.base.adapter.BaseRVAdapter;
import com.jmm.client.module.base.OnRefreshCallback;
import com.jmm.client.http.bean.LoadStatus;
import com.jmm.client.http.BaseObserver;
import com.jmm.client.widget.NetworkStateView;

import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import io.reactivex.Observable;


public abstract class BaseRVFragment<T> extends BaseFragment implements OnRefreshCallback {

    @BindView(R.id.ptrFrameLayout) PtrFrameLayout ptrFrameLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    protected BaseRVAdapter<T> mAdapter;
    //    protected LoadingAndRetryManager loadingAndRetryManager;
    //    protected int mCurrentCount;
    protected int mPageCount;
    protected int mCurrentPage = 1;

    protected static int PAGE_SIZE = 10;
    private NetworkStateView vml;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rv;
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener());
        initPtrFrameLayout();//初始化下拉刷新控件
        mAdapter = getRecyclerViewAdapter();
        mAdapter.setEnableLoadMore(true);//打开加载更多
        RecyclerView.ItemDecoration itemDecoration = getItemDecoration();
        if (itemDecoration != null) {
            recyclerView.addItemDecoration(itemDecoration);
        }
        recyclerView.setAdapter(mAdapter);
        vml = new NetworkStateView(ptrFrameLayout);
        mAdapter.setOnLoadMoreListener(() -> onLoadMore(), recyclerView);
//        vml.getErrorView().findViewById(R.id.id_btn_retry).setOnClickListener(v -> onRetry());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    protected void initPtrFrameLayout() {
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getActivity());
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                onRefresh();
            }
        });
    }


    @Override
    protected void requestData(LoadStatus status) {
        if (status == LoadStatus.MORE) {
            String currentPage = getCurrentPage();
            if (currentPage != null) {
                mPageCount = Integer.parseInt(currentPage);
            }
            mCurrentPage = status == LoadStatus.MORE ? ++mCurrentPage : 1;
        } else {
            mCurrentPage = 1;
        }
        getApi(String.valueOf(mCurrentPage)).subscribe(new BaseObserver<List<T>>() {


            @Override
            public void onNext(List<T> t) {
                if (status == LoadStatus.MORE) {
                    if (mCurrentPage >= mPageCount) {
                        mAdapter.loadMoreEnd(); //没有更多数据了
                    }
                    mAdapter.addData(t);
//                        mCurrentCount = mAdapter.getData().size();
                } else {
//                    if (t.size() == 0) {
//                        vml.showEmptyView();
//                        vml.showContentView();
//                    } else {
                    vml.showContentView();
                    mAdapter.setNewData(t); // 刷新或者第一次加载成功
//                    }
                    if (t.size() < PAGE_SIZE) {
                        mAdapter.loadMoreEnd();
                    }
                    if (!hasLoadMore()) {
                        mAdapter.loadMoreEnd();
                    }
                }
            }

            @Override
            public void onComplete() {
                super.onComplete();
                switch (status) {
                    case REFRESH:
                        ptrFrameLayout.refreshComplete();//刷新完成
                        break;
                    case MORE:
                        mAdapter.loadMoreComplete(); // 加载更多完成
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                switch (status) {
                    case LOADING:
                        vml.showErrorView();//失败重试
                        break;
                    case REFRESH:
                        ptrFrameLayout.refreshComplete(); //下拉刷新完成
                        break;
                    case MORE:
                        mAdapter.loadMoreFail(); // 加载更多失败
                        break;
                }
//                e.printStackTrace();
//                onSucceed();
            }


        });
    }

    protected abstract String getCurrentPage();

    public NetworkStateView getVisibilityLayout() {
        return vml;
    }

    public boolean hasLoadMore() {
        return true;
    }

    @Override
    public void onRefresh() {
        requestData(LoadStatus.REFRESH);
        System.out.println("onRefresh");
    }

    @Override
    public void onLoadMore() {
        requestData(LoadStatus.MORE);
        System.out.println("onLoadMore");
    }

    @Override
    public void onRetry() {
        requestData(LoadStatus.LOADING);
        System.out.println("onRetry");
    }

    protected void onFailure(String msg) {
//        ToastUtils.showMsg(msg);
    }

    protected abstract Observable<List<T>> getApi(String currNum);

    protected abstract BaseRVAdapter<T> getRecyclerViewAdapter();

    protected abstract void onItemClick(BaseQuickAdapter adapter, View view, int position);

    protected void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    protected void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    protected void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    private class RecyclerItemClickListener extends SimpleClickListener {

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            BaseRVFragment.this.onItemClick(adapter, view, position);
        }

        @Override
        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
            BaseRVFragment.this.onItemLongClick(adapter, view, position);
        }

        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            BaseRVFragment.this.onItemChildClick(adapter, view, position);
        }

        @Override
        public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
            BaseRVFragment.this.onItemChildLongClick(adapter, view, position);
        }
    }
}
