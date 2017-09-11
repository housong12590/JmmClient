package com.jmm.client.http;

import com.jmm.client.http.dialog.ProgressCancelListener;
import com.jmm.client.http.dialog.ProgressDialogHandler;
import com.jmm.common.utils.ActivityUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public abstract class ProgressObserver<T> extends BaseObserver<T> implements ProgressCancelListener {


    private final ProgressDialogHandler dialog;

    public ProgressObserver() {
        dialog = new ProgressDialogHandler(ActivityUtils.getTopActivity(), this, true);
    }

    private void showProgressDialog() {
        dialog.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
    }

    private void dismissProgressDialog() {
        dialog.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        super.onSubscribe(d);
        if (d.isDisposed()) {
            showProgressDialog();
        }
    }


    @Override
    public void onComplete() {
        super.onComplete();
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        e.printStackTrace();
        dismissProgressDialog();
    }

    @Override
    public void onCancelProgress() {
        if (!disposable.isDisposed()) {
            this.disposable.dispose();
        }
    }
}
