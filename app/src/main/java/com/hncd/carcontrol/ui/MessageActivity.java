package com.hncd.carcontrol.ui;

import android.view.View;

import com.hncd.carcontrol.R;
import com.hncd.carcontrol.adapter.MessageAdapter;
import com.hncd.carcontrol.base.CarBaseActivity;
import com.hncd.carcontrol.utils.ItemRecyDecoration;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends CarBaseActivity {

    @BindView(R.id.message_recy)
    RecyclerView mMessageRecy;
    @BindView(R.id.message_smart)
    SmartRefreshLayout mSmart;
    private List<String> mListMsgs;
    private MessageAdapter mMessageAdapter;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        TitleUtils.setStatusTextColor(false, this);
        mSmart.setEnablePureScrollMode(true);//是否启用纯滚动模式
        mSmart.setEnableNestedScroll(true);//是否启用嵌套滚动;
        mSmart.setEnableOverScrollDrag(true);//是否启用越界拖动（仿苹果效果）1.0.4
        mSmart.setEnableOverScrollBounce(true);//是否启用越界回弹
        initViews();
    }

    @OnClick(R.id.message_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.message_back:
                finish();
                break;
        }
    }

    private void initViews() {
        mListMsgs = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, mListMsgs);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mMessageRecy.setLayoutManager(manager);
        mMessageRecy.addItemDecoration(new ItemRecyDecoration(this, LinearLayoutManager.VERTICAL));
        mMessageRecy.setAdapter(mMessageAdapter);
        getData();

    }

    private void getData() {
        for (int i = 0; i < 20; i++) {
            mListMsgs.add("");
        }
        mMessageAdapter.notifyDataSetChanged();
    }

}
