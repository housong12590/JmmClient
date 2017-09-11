package com.jmm.client.module.user.fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jmm.client.R;
import com.jmm.client.module.base.fragment.BaseFragment;
import com.jmm.client.http.bean.LoadStatus;
import com.jmm.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/8/008.
 */

public class SetPwdFragment extends BaseFragment {

    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.iv_wacth)
    ImageView mIvWacth;
    boolean flag = true;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_set_pwd;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void requestData(LoadStatus status) {

    }

    @OnClick({R.id.iv_wacth, R.id.tv_changepassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_wacth:
                if(flag){
                    //如果选中，显示密码
                    mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                flag = !flag;
                mEtPassword.setSelection(mEtPassword.getText().toString().length());
                break;
            case R.id.tv_changepassword:
                ToastUtils.showShort("你的密码为: " + mEtPassword.getText().toString());
                break;
        }
    }
}
