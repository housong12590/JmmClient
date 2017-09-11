package com.jmm.client.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jmm.client.R;


/**
 * 统计字数文本输入框
 */

public class StatisticsEditText extends LinearLayout implements TextWatcher {


    private TextView tvCount;
    private int maxLength;
    private String hint;

    public StatisticsEditText(Context context) {
        this(context, null);
    }

    public StatisticsEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StatisticsEditText);
        maxLength = ta.getInt(R.styleable.StatisticsEditText_max_length, 20);
        hint = ta.getString(R.styleable.StatisticsEditText_hint);

        ta.recycle();
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.statistics_edit_layout, this);
        EditText etContent = (EditText) findViewById(R.id.et_content);
        etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        etContent.setHint(hint);
        etContent.addTextChangedListener(this);

        tvCount = (TextView) findViewById(R.id.tv_count);
        tvCount.setText("0/" + maxLength);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        tvCount.setText(s.length() + "/" + maxLength);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
