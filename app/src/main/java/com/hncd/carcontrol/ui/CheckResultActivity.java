package com.hncd.carcontrol.ui;

import android.content.Intent;
import android.view.View;

import com.hncd.carcontrol.R;
import com.hncd.carcontrol.adapter.CheckAdapter;
import com.hncd.carcontrol.base.CarBaseActivity;
import com.hncd.carcontrol.utils.ItemRecyDecoration;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.superc.yyfflibrary.utils.titlebar.TitleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckResultActivity extends CarBaseActivity {


    @BindView(R.id.check_result_recy)
    RecyclerView mCheckResultRecy;
    @BindView(R.id.check_result_smart)
    SmartRefreshLayout mSmart;
    private String data_result = "";
    private List<Map<String, Object>> mMapList;
    private CheckAdapter mCheckAdapter;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_check_result;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        TitleUtils.setStatusTextColor(false, this);
        Intent intent = getIntent();
        data_result = intent.getStringExtra("data");
        initView();
        getData();
    }

    @OnClick({R.id.check_back, R.id.check_result_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_back:
                finish();
                break;
            case R.id.check_result_start:
                statActivity(CheckItemActivity.class);
                finish();
                break;
        }
    }

    private void initView() {
        mSmart.setEnablePureScrollMode(true);//是否启用纯滚动模式
        mSmart.setEnableNestedScroll(true);//是否启用嵌套滚动;
        mSmart.setEnableOverScrollDrag(true);//是否启用越界拖动（仿苹果效果）1.0.4
        mSmart.setEnableOverScrollBounce(true);//是否启用越界回弹
        mMapList = new ArrayList<>();
        mCheckAdapter = new CheckAdapter(this, mMapList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mCheckResultRecy.setLayoutManager(linearLayoutManager);
        mCheckResultRecy.addItemDecoration(new ItemRecyDecoration(this,LinearLayoutManager.VERTICAL));
        mCheckResultRecy.setAdapter(mCheckAdapter);


    }

    private String[] mStrings = new String[]{"车管流水号：", "发证机关：", "品牌种类：", "号牌号码：", "所有人：", "使用性质：",
            "是否是新能源汽车：", "新能源汽车种类：", "状态：", "行驶证编号：", "初次登记日期：", "有效期止：", "强制报废期止：",
            "拉结尔：", "老家东莞市：", "为了：", "啊刚问了：", "家里：", "就完了：", "咖喱："};

    private void getData() {
        for (int i = 0; i < 20; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", mStrings[i]);
            map.put("content", "那五个呢" + i);
            mMapList.add(map);

        }
        mCheckAdapter.notifyDataSetChanged();

    }

}
