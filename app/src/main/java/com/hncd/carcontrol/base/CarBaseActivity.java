package com.hncd.carcontrol.base;

import android.os.Bundle;

import com.hncd.carcontrol.utils.CarShareUtil;
import com.ljy.devring.DevRing;
import com.superc.yyfflibrary.base.BaseActivity;

import androidx.annotation.Nullable;

public abstract class CarBaseActivity extends BaseActivity {

    public String mUser_id ="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mUser_id = (String) CarShareUtil.getInstance().get(CarShareUtil.APP_USERID, "");
        super.onCreate(savedInstanceState);
        if(DevRing.activityListManager()!=null)
        DevRing.activityListManager().addActivity(this);

    }
}
