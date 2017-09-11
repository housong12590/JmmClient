package com.jmm.client.module.video;

import android.app.ProgressDialog;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jmm.client.R;
import com.jmm.client.config.StorageConfig;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.widget.SectionProgressBar;
import com.jmm.common.utils.LogUtils;
import com.jmm.common.utils.ToastUtils;
import com.qiniu.pili.droid.shortvideo.PLAudioEncodeSetting;
import com.qiniu.pili.droid.shortvideo.PLCameraSetting;
import com.qiniu.pili.droid.shortvideo.PLErrorCode;
import com.qiniu.pili.droid.shortvideo.PLMicrophoneSetting;
import com.qiniu.pili.droid.shortvideo.PLRecordSetting;
import com.qiniu.pili.droid.shortvideo.PLRecordStateListener;
import com.qiniu.pili.droid.shortvideo.PLShortVideoRecorder;
import com.qiniu.pili.droid.shortvideo.PLVideoEncodeSetting;
import com.qiniu.pili.droid.shortvideo.PLVideoSaveListener;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoRecordActivity extends BaseActivity implements PLRecordStateListener, View.OnTouchListener
        , PLVideoSaveListener, Toolbar.OnMenuItemClickListener {

    private static final String VIDEO_OUTPUT_PATH = StorageConfig.VIDEO_CACHE_PATH
            + System.currentTimeMillis() + ".mp4";

    private static final int VIDEO_PLAY_REQUEST = 1001;
    private static final int VIDEO_RECORD_MAX_TIME = 10 * 1000; //录制最长时间
    private static final int VIDEO_RECORD_MIN_TIME = 3 * 1000; //录制最小时间
    private static final int VIDEO_ENCODING_BITRATE = 1000 * 1000; //比特率
    private static final int VIDEO_ENCODING_FPS = 20; // 视频帧率

    @BindView(R.id.surfaceView) GLSurfaceView mSurfaceView;
    @BindView(R.id.progressBar) SectionProgressBar mProgressBar;
    @BindView(R.id.iv_record) ImageView mIvRecord;
    @BindView(R.id.iv_delete) ImageView mIvDelete;
    @BindView(R.id.iv_concat) ImageView mIvConcat;

    private PLShortVideoRecorder mShortVideoRecorder;
    private boolean mFlashEnabled;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_record;
    }

    @Override
    public void initView() {
//        mToolbar.inflateMenu(R.menu.toolbar_record_menu);
//        mToolbar.setOnMenuItemClickListener(this);
        mIvRecord.setOnTouchListener(this);
    }

    @Override
    public void initData() {
        initRecordSDK();
    }

    private void initRecordSDK() {
        mShortVideoRecorder = new PLShortVideoRecorder();
        mShortVideoRecorder.setRecordStateListener(this);
        //摄像头采集选项
        PLCameraSetting mCameraSetting = new PLCameraSetting();
        PLCameraSetting.CAMERA_FACING_ID facingId = PLCameraSetting.CAMERA_FACING_ID.CAMERA_FACING_BACK;
        mCameraSetting.setCameraId(facingId);
        mCameraSetting.setCameraPreviewSizeRatio(PLCameraSetting.CAMERA_PREVIEW_SIZE_RATIO.RATIO_4_3);
        mCameraSetting.setCameraPreviewSizeLevel(PLCameraSetting.CAMERA_PREVIEW_SIZE_LEVEL.PREVIEW_SIZE_LEVEL_480P);
        //麦克风采集选项
        PLMicrophoneSetting microphoneSetting = new PLMicrophoneSetting();
        //视频编码选项
        PLVideoEncodeSetting videoEncodeSetting = new PLVideoEncodeSetting(this);
        videoEncodeSetting.setEncodingSizeLevel(PLVideoEncodeSetting.VIDEO_ENCODING_SIZE_LEVEL.VIDEO_ENCODING_SIZE_LEVEL_480P_1);
        videoEncodeSetting.setEncodingBitrate(VIDEO_ENCODING_BITRATE);
        videoEncodeSetting.setEncodingFps(VIDEO_ENCODING_FPS);
        //音频编码选项
        PLAudioEncodeSetting audioEncodeSetting = new PLAudioEncodeSetting();
        //录制选项
        PLRecordSetting recordSetting = new PLRecordSetting();
        recordSetting.setMaxRecordDuration(VIDEO_RECORD_MAX_TIME); // 10s
        recordSetting.setVideoCacheDir(StorageConfig.VIDEO_CACHE_PATH);
        recordSetting.setVideoFilepath(VIDEO_OUTPUT_PATH);

        mShortVideoRecorder.prepare(mSurfaceView, mCameraSetting, microphoneSetting, videoEncodeSetting,
                audioEncodeSetting, null, recordSetting);
        mProgressBar.setFirstPointTime(VIDEO_RECORD_MIN_TIME);
        mProgressBar.setTotalTime(VIDEO_RECORD_MAX_TIME);
    }


    private void updateRecordingButton(boolean isRecording) {
//        mSwitchCameraBtn.setEnabled(!isRecording);
        mIvRecord.setActivated(isRecording);
    }

    private void onSectionCountChanged(final int count, final long totalTime) {
        runOnUiThread(() -> {
//            mIvRecord.setEnabled(count > 0);
//            mIvRecord.setEnabled(totalTime >= 10 * 1000);
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_switch_camera:
                mShortVideoRecorder.switchCamera();
                break;
            case R.id.menu_switch_flash:
                mFlashEnabled = !mFlashEnabled;
                mShortVideoRecorder.setFlashEnabled(mFlashEnabled);
                item.setIcon(getResources().getDrawable(mFlashEnabled ? R.drawable.icon_flash_off : R.drawable.icon_flash_on));
                break;
        }
        return false;
    }

    @Override
    public void onReady() {
        LogUtils.d(TAG, "初始化成功...");
    }

    String mRecordErrorMsg;

    @Override
    public void onError(int i) {
        if (i == PLErrorCode.ERROR_SETUP_CAMERA_FAILED) {
            mRecordErrorMsg = "摄像头配置错误";
        } else if (i == PLErrorCode.ERROR_SETUP_MICROPHONE_FAILED) {
            mRecordErrorMsg = "麦克风配置错误";
        }
        runOnUiThread(() -> ToastUtils.showShort(mRecordErrorMsg));
    }

    @Override
    public void onDurationTooShort() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShort("视频太短了");
            }
        });
    }


    @Override
    public void onRecordStarted() {
        mProgressBar.setCurrentState(SectionProgressBar.State.START);
        LogUtils.d(TAG, "开始录制...");
    }

    @Override
    public void onRecordStopped() {
        mProgressBar.setCurrentState(SectionProgressBar.State.PAUSE);
        LogUtils.d(TAG, "停止录制...");
    }

    @Override
    public void onSectionIncreased(long l, long l1, int i) {
        mProgressBar.addBreakPointTime(l1);
        onSectionCountChanged(i, l1);
    }

    @Override
    public void onSectionDecreased(long l, long l1, int i) {
        onSectionCountChanged(i, l1);
        mProgressBar.removeLastBreakPoint();
    }

    @Override
    public void onRecordCompleted() {
        runOnUiThread(() -> mIvConcat.performClick());
        LogUtils.d(TAG, "视频结束...");
    }

    @Override
    public void onSaveVideoSuccess(String s) {
        runOnUiThread(() -> {
            dismissProgressDialog();
            Intent intent = new Intent(mContext, VideoPlayActivity.class);
            intent.putExtra("path", VIDEO_OUTPUT_PATH);
            startActivityForResult(intent, VIDEO_PLAY_REQUEST);
        });
        LogUtils.d(TAG, "保存视频成功...");
    }

    @Override
    public void onSaveVideoFailed(int i) {
        runOnUiThread(this::dismissProgressDialog);
        LogUtils.d(TAG, "保存视频失败...");
    }

    @Override
    public void onSaveVideoCanceled() {
        runOnUiThread(this::dismissProgressDialog);
        LogUtils.d(TAG, "视频保存被取消...");
    }

    @Override
    public void onProgressUpdate(float v) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mShortVideoRecorder.beginSection()) {
                    updateRecordingButton(true);
                } else {
                    Toast.makeText(VideoRecordActivity.this, "无法开始视频段录制", Toast.LENGTH_SHORT).show();
                }
                break;
            case MotionEvent.ACTION_UP:
                mShortVideoRecorder.endSection();
                updateRecordingButton(false);
                break;
        }
        return true;
    }

    @OnClick({R.id.iv_delete, R.id.iv_concat})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_delete://视频合成
                mShortVideoRecorder.deleteLastSection();
                break;
            case R.id.iv_concat:
                showProgressDialog();
                //延迟1.5秒,因为录制结束,视频还在编码过程中, 不能立即合成
                mIvConcat.postDelayed(() -> mShortVideoRecorder.concatSections(VideoRecordActivity.this), 1500);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == VIDEO_PLAY_REQUEST) {
                setResult(RESULT_OK, data);
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShortVideoRecorder.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShortVideoRecorder.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShortVideoRecorder.destroy();
    }


    private ProgressDialog dialog;

    private void showProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.setTitle("请稍候...");
        dialog.show();
    }

    private void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


}
