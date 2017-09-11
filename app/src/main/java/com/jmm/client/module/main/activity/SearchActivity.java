package com.jmm.client.module.main.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jmm.client.R;
import com.jmm.client.module.base.activity.BaseActivity;
import com.jmm.client.widget.FlowLayout;
import com.jmm.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.search_flowlayout)
    FlowLayout mSearchFlowlayout;

    public static final String EXTRA_KEY_TYPE = "extra_key_type";
    public static final String EXTRA_KEY_KEYWORD = "extra_key_keyword";
    public static final String KEY_SEARCH_HISTORY_KEYWORD = "key_search_history_keyword";
    private SharedPreferences mPref;//使用SharedPreferences记录搜索历史
    private String mType;
    private EditText input;
    private TextView btn_search;
    private List<String> mHistoryKeywords;//记录文本
    private ArrayAdapter<String> mArrAdapter;//搜索历史适配器
    private LinearLayout mSearchHistoryLl;

    private String[] mDatas = new String[]{"邮票", "生肖币", "纪念邮票", "国外币", "其他币种"};
    private ImageView mIvDelete;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        initHistoryView();
        input.setFocusable(true);
        input.setFocusableInTouchMode(true);
        input.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initData() {
        for (int i = 0; i < mDatas.length; i++) {
            final String data = mDatas[i];
            TextView tv = new TextView(this);
            tv.setText(data);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tv.setGravity(Gravity.CENTER);
            int roundRadius = 15;
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(getResources().getColor(R.color.edit_bg));
            gd.setCornerRadius(roundRadius);
            tv.setBackgroundDrawable(gd);
            tv.setOnClickListener(v -> {
            });
            mSearchFlowlayout.addView(tv);
        }
    }

    /**
     * 初始化历史搜索
     */

    private void initHistoryView() {
        input = (EditText) findViewById(R.id.et_input);
        mIvDelete = (ImageView) findViewById(R.id.search_iv_delete);
        btn_search = (TextView) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(v -> {
            search();
        });
        mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
            }
        });
        mPref = getSharedPreferences("input", Activity.MODE_PRIVATE);
        mType = getIntent().getStringExtra(EXTRA_KEY_TYPE);
        String keyword = getIntent().getStringExtra(EXTRA_KEY_KEYWORD);
        if (!TextUtils.isEmpty(keyword)) {
            input.setText(keyword);
        }
        mHistoryKeywords = new ArrayList<String>();

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    if (mHistoryKeywords.size() > 0) {
                        mSearchHistoryLl.setVisibility(View.VISIBLE);
                    } else {
                        mSearchHistoryLl.setVisibility(View.GONE);
                    }
                    mIvDelete.setVisibility(View.GONE);
                } else {
                    mIvDelete.setVisibility(View.VISIBLE);
                    mSearchHistoryLl.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        initSearchHistory();
        initEditListener();
    }

    /**
     * 软键盘监听
     */
    private void initEditListener() {
        input.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                //写入搜索操作和数据库插入搜索记录操作
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                search();
            }
            return false;
        });
    }

    /**
     * 搜索
     */
    private void search() {
        String searchContext = input.getText().toString().trim();
        if (TextUtils.isEmpty(searchContext)) {
            ToastUtils.showShort("输入框为空，请输入");
        } else {
            save();
//            searchUser(searchContext);
        }
    }

    /**
     * 初始化搜索历史记录
     */

    public void initSearchHistory() {
        mSearchHistoryLl = (LinearLayout) findViewById(R.id.search_history_ll);
        ListView listView = (ListView) findViewById(R.id.search_history_lv);
        findViewById(R.id.clear_history_btn).setOnClickListener(v -> cleanHistory());
        String history = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");
        if (!TextUtils.isEmpty(history)) {
            List<String> list = new ArrayList<String>();
            for (Object o : history.split(",")) {
                list.add((String) o);
            }
            mHistoryKeywords = list;
        }
        if (mHistoryKeywords.size() > 0) {
            mSearchHistoryLl.setVisibility(View.VISIBLE);
        } else {
            mSearchHistoryLl.setVisibility(View.GONE);
        }
        mArrAdapter = new ArrayAdapter<String>(this, R.layout.item_search_history, mHistoryKeywords);
        listView.setAdapter(mArrAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            input.setText(mHistoryKeywords.get(i));
            mSearchHistoryLl.setVisibility(View.GONE);
        });
        mArrAdapter.notifyDataSetChanged();
    }

    /**
     * 储存搜索历史
     */
    public void save() {
        String text = input.getText().toString();
        String oldText = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");
        if (!TextUtils.isEmpty(text) && !(oldText.contains(text))) {
            if (mHistoryKeywords.size() > 5) {
                //最多保存条数
                return;
            }
            SharedPreferences.Editor editor = mPref.edit();
            editor.putString(KEY_SEARCH_HISTORY_KEYWORD, text + "," + oldText);
            editor.commit();
            mHistoryKeywords.add(0, text);
        }
        mArrAdapter.notifyDataSetChanged();
    }

    /**
     * 清除历史纪录
     */
    public void cleanHistory() {
        mPref = getSharedPreferences("input", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPref.edit();
        editor.remove(KEY_SEARCH_HISTORY_KEYWORD).commit();
        mHistoryKeywords.clear();
        mArrAdapter.notifyDataSetChanged();
        mSearchHistoryLl.setVisibility(View.GONE);
    }
}
