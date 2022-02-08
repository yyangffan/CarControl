package com.hncd.carcontrol.ui;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hncd.carcontrol.R;
import com.hncd.carcontrol.base.CarApplication;
import com.hncd.carcontrol.base.CarBaseActivity;
import com.hncd.carcontrol.base.Constant;
import com.hncd.carcontrol.utils.CarShareUtil;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfigSetActivity extends CarBaseActivity {


    @BindView(R.id.config_set_ip)
    EditText mConfigSetIp;
    @BindView(R.id.config_set_kou)
    EditText mConfigSetKou;
    @BindView(R.id.config_set_name)
    EditText mConfigSetName;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_config_set;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        TitleUtils.setStatusTextColor(false, this);

    }


    @OnClick({R.id.config_set_back, R.id.config_set_save, R.id.config_set_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.config_set_back:
                finish();
                break;
            case R.id.config_set_save:
               toSetBaseUrl();
                break;
            case R.id.config_set_test:
                ToastShow("测试接口");
                break;
        }
    }

    private void toSetBaseUrl(){
        String url = "http://";
        url+=mConfigSetIp.getText().toString();
        url+=":"+mConfigSetKou.getText().toString();
        url+="/"+mConfigSetName.getText().toString()+"/";
        Constant.BASE_URL = url;
        CarApplication.getInstance().initDevring();
        CarShareUtil.getInstance().put(CarShareUtil.APP_BASEURL,url);
        ToastShow("保存成功");
    }

}
