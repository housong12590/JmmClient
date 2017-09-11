package com.jmm.client.module.main.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.jmm.client.R;
import com.jmm.client.RxUtils;
import com.jmm.client.helper.DividerItemDecoration;
import com.jmm.client.http.BaseObserver;
import com.jmm.client.http.bean.LoadStatus;
import com.jmm.client.module.base.fragment.DelayFragment;
import com.jmm.client.module.chat.ChatActivity;
import com.jmm.client.module.main.adapter.MessageAdapter;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


public class MessageFragment extends DelayFragment {

    @BindView(R.id.message_rv) RecyclerView messageRv;
    private MessageAdapter messageAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView() {
        messageRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        messageRv.addItemDecoration(new DividerItemDecoration());
        messageAdapter = new MessageAdapter(Collections.emptyList());
        messageRv.setAdapter(messageAdapter);
        messageRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String userId = messageAdapter.getData().get(position);
                startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, userId));
            }
        });
    }

    @Override
    public void initData() {
        Observable.create((ObservableOnSubscribe<List<String>>) e -> {
            List<String> list = EMClient.getInstance().contactManager().getAllContactsFromServer();
            e.onNext(list);
            e.onComplete();
        }).compose(RxUtils.rxSchedulerHelper())
                .subscribe(new BaseObserver<List<String>>() {
                    @Override
                    public void onNext(@NonNull List<String> strings) {
                        messageAdapter.setNewData(strings);
                    }
                });
    }

    @Override
    protected void requestData(LoadStatus status) {

    }
}
