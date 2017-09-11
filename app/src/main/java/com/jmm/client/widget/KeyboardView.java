package com.jmm.client.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jmm.client.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class KeyboardView extends LinearLayout implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener, View.OnFocusChangeListener {

    private EditText editText;

    public KeyboardView(Context context) {
        this(context, null);
    }

    public KeyboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void bindEditText(EditText editText) {
        this.editText = editText;
        Activity activity = (Activity) getContext();
        hideSystemKeyboard(activity);
        editText.setOnFocusChangeListener(this);
        editText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getVisibility() == View.INVISIBLE) {
                    show();
                }
            }
        });
    }

    private void init() {
        setOrientation(VERTICAL);
        View view = new View(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        view.setBackgroundColor(Color.parseColor("#f1f1f1"));
        view.setLayoutParams(params);
        View view1 = new View(getContext());
        view1.setBackgroundColor(Color.parseColor("#f1f1f1"));
        view1.setLayoutParams(params);
        addView(view);
        View.inflate(getContext(), R.layout.keyboard_layout, this);
        addView(view1);
        setClick();
    }

    private void setClick() {
        findViewById(R.id.tv_number_1).setOnClickListener(this);
        findViewById(R.id.tv_number_2).setOnClickListener(this);
        findViewById(R.id.tv_number_3).setOnClickListener(this);
        findViewById(R.id.tv_number_4).setOnClickListener(this);
        findViewById(R.id.tv_number_5).setOnClickListener(this);
        findViewById(R.id.tv_number_6).setOnClickListener(this);
        findViewById(R.id.tv_number_7).setOnClickListener(this);
        findViewById(R.id.tv_number_8).setOnClickListener(this);
        findViewById(R.id.tv_number_9).setOnClickListener(this);
        findViewById(R.id.tv_number_0).setOnClickListener(this);
        findViewById(R.id.tv_number_x).setOnClickListener(this);
        findViewById(R.id.tv_number_dot).setOnClickListener(this);
        findViewById(R.id.tv_finish).setOnClickListener(this);
        View tvDelete = findViewById(R.id.tv_delete);
        tvDelete.setOnClickListener(this);
        tvDelete.setOnLongClickListener(this);
        tvDelete.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        if (editText == null) {
            throw new NullPointerException("please bind editText reuse");
        }
        switch (v.getId()) {
            case R.id.tv_delete:
                removeText();
                break;
            case R.id.tv_finish:
                if (listener != null) {
                    listener.onComplete();
                }
                hide(v);
                break;
            default:
                addText(((TextView) v).getText().toString());
                break;
        }
    }

    private void addText(String text) {
        String oldText = editText.getText().toString();
        if (oldText.length() >= getMaxLength(editText)) {

        } else {
            String newText = oldText + text;
            editText.setText(newText);
            editText.setSelection(newText.length());
        }
    }

    private void removeText() {
        String text = editText.getText().toString();
        int length = text.length();
        if (length != 0) {
            text = text.substring(0, length - 1);
        }
        editText.setText(text);
        editText.setSelection(text.length());
    }

    private void hideSystemKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setSoftInputShownOnFocus;
            setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setSoftInputShownOnFocus.setAccessible(true);
            setSoftInputShownOnFocus.invoke(editText, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        isUp = false;
        post(r);
        return false;
    }

    private boolean isUp;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isUp = false;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isUp = true;
                break;
        }
        return false;
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            if (!isUp) {
                removeText();
                postDelayed(r, 50);
            }
        }
    };

    public void show() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_to_up);
        setAnimation(animation);
        setVisibility(VISIBLE);
    }

    public void hide(View v) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.up_to_hide);
        v.setEnabled(false);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        setAnimation(animation);
        setVisibility(INVISIBLE);
    }

    private OnCompleteClickListener listener;

    public void setOnCompleteClickListener(OnCompleteClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
//        if (hasFocus) {
//            show();
//        }
    }

    public interface OnCompleteClickListener {

        void onComplete();
    }


    public int getMaxLength(EditText et) {
        int length = Integer.MAX_VALUE;
        try {
            InputFilter[] inputFilters = et.getFilters();
            for (InputFilter filter : inputFilters) {
                Class<?> c = filter.getClass();
                if (c.getName().equals("android.text.InputFilter$LengthFilter")) {
                    Field[] f = c.getDeclaredFields();
                    for (Field field : f) {
                        if (field.getName().equals("mMax")) {
                            field.setAccessible(true);
                            length = (Integer) field.get(filter);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

}
