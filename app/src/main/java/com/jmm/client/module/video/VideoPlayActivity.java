package com.jmm.client.module.video;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.jmm.client.R;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.widget.SurfaceVideoView;

import butterknife.BindView;
import butterknife.OnClick;


public class VideoPlayActivity extends BaseActivity implements MediaPlayer.OnPreparedListener,
        SurfaceVideoView.OnPlayStateListener, MediaPlayer.OnErrorListener, View.OnClickListener,
        MediaPlayer.OnInfoListener, MediaPlayer.OnCompletionListener {

    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.videoView) SurfaceVideoView mVideoView;
    private String mVideoPath;
    private boolean mNeedResume;

    @Override
    public void parseIntent(Intent intent) {
        mVideoPath = getIntent().getStringExtra("path");
    }

    @Override
    public int getLayoutId() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return R.layout.activity_video_play;
    }


    @Override
    public void initView() {
        mProgressBar.setMax(100);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnPlayStateListener(this);
        mVideoView.setOnErrorListener(this);
        mVideoView.setOnClickListener(this);
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setVideoPath(mVideoPath);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mVideoView.setVolume(SurfaceVideoView.getSystemVolumn(this));
        mVideoView.start();
        int duration = mVideoView.getDuration();
        mProgressBar.setMax(duration);
        mVideoView.post(progressTask);
    }

    @Override
    public void onStateChanged(boolean isPlaying) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (!isFinishing()) {
            // 播放失败
        }
        finish();
        return false;
    }

    @OnClick({R.id.tv_affirm})
    public void onClick(View v) {
        setResult(RESULT_OK, getIntent());
        finish();
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                // 音频和视频数据不正确
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (!isFinishing())
                    mVideoView.pause();
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                if (!isFinishing())
                    mVideoView.start();
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
//                if (DeviceUtils.hasJellyBean()) {
//                    mVideoView.setBackground(null);
//                } else {
//                    mVideoView.setBackgroundDrawable(null);
//                }
                break;
        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (!isFinishing())
            mVideoView.reOpen();
    }

    private Runnable progressTask = new Runnable() {
        @Override
        public void run() {
            int currentPosition = mVideoView.getCurrentPosition();
            mProgressBar.setProgress(currentPosition);
            mVideoView.postDelayed(progressTask, 20);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (mVideoView != null && mNeedResume) {
            mNeedResume = false;
            if (mVideoView.isRelease())
                mVideoView.reOpen();
            else
                mVideoView.start();
            mVideoView.post(progressTask);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null) {
            if (mVideoView.isPlaying()) {
                mNeedResume = true;
                mVideoView.pause();
                mVideoView.removeCallbacks(progressTask);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mVideoView != null) {
            mVideoView.release();
            mVideoView = null;
        }
        super.onDestroy();
    }
}
