package com.jmm.client.helper;

import android.text.InputFilter;
import android.text.Spanned;

import com.jmm.common.utils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EditText Emoji表情过滤
 */

public class EmojiFilter implements InputFilter {


    Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\ud83e\udd00-\ud83e\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            ToastUtils.showShort("不支持Emoji输入");
            return "";
        }
        return null;
    }
}
