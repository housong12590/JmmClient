package com.jmm.client.helper;


import com.hyphenate.chat.EMClient;

public class EMChatHelper {

    private static EMChatHelper instance = null;

    public synchronized static EMChatHelper getInstance() {
        if (instance == null) {
            instance = new EMChatHelper();
        }
        return instance;
    }

    public boolean isLogin(){
        return EMClient.getInstance().isLoggedInBefore();
    }
}
