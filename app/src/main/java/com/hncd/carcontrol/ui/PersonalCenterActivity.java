package com.hncd.carcontrol.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hncd.carcontrol.R;
import com.hncd.carcontrol.base.CarBaseActivity;
import com.hncd.carcontrol.dig_pop.LogoutDialog;
import com.hncd.carcontrol.utils.CarShareUtil;
import com.hncd.carcontrol.views.CircleImageView;
import com.ljy.devring.DevRing;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalCenterActivity extends CarBaseActivity {

    @BindView(R.id.mine_num)
    TextView mMineNum;
    @BindView(R.id.mine_head)
    CircleImageView mMineHead;
    @BindView(R.id.mine_name)
    TextView mMineName;
    @BindView(R.id.mine_accounts)
    TextView mMineAccounts;
    private LogoutDialog mLogoutDialog;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_personal_center;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        TitleUtils.setStatusTextColor(false, this);
        mLogoutDialog = new LogoutDialog(this);
        mLogoutDialog.setOnLogoutClickListener(new LogoutDialog.OnLogoutClickListener() {
            @Override
            public void onLogoutClickListener() {
                loginOut();
            }
        });
        getData();
    }

    @OnClick({R.id.mine_back, R.id.mine_ll_msg, R.id.mine_changepwd, R.id.mine_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_back:
                finish();
                break;
            case R.id.mine_ll_msg:
                statActivity(MessageActivity.class);
                break;
            case R.id.mine_changepwd:
                statActivity(ChangepwdActivity.class);
                break;
            case R.id.mine_logout:
                mLogoutDialog.show();
                break;
        }
    }

    private void getData() {
        mMineName.setText("我没有名字");
        mMineAccounts.setText("12342");
        mMineNum.setText("99");
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.icon_default).error(R.drawable.icon_default).circleCrop();
        Glide.with(this).load(R.drawable.icon_default).apply(requestOptions).into(mMineHead);

    }

    private void loginOut() {
        CarShareUtil.getInstance().clear();
        startActivity(new Intent(this, LoginActivity.class));
        DevRing.activityListManager().killAllExclude(LoginActivity.class);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ToastShow("获取消息数量进行重置");

    }

}
