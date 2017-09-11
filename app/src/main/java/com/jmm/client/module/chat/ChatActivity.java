package com.jmm.client.module.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;
import com.jmm.client.R;
import com.jmm.client.module.main.activity.MainActivity;

public class ChatActivity extends EaseBaseActivity {

    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    private String toChatUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        activityInstance = this;
        toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        chatFragment = new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra(EaseConstant.EXTRA_USER_ID);
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public String getToChatUsername() {
        return toChatUsername;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
//        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }
}
