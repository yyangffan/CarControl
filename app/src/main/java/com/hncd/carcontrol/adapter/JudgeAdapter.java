package com.hncd.carcontrol.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hncd.carcontrol.R;
import com.hncd.carcontrol.bean.CheckAllBean;
import com.hncd.carcontrol.utils.GlideEngine;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnItemClickListener;
import com.luck.picture.lib.tools.ScreenUtils;

import java.util.List;
import java.util.Map;

import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class JudgeAdapter extends RecyclerView.Adapter<JudgeAdapter.ViewHolder> {
    private Context mContext;
    private List<CheckAllBean.DataBean.CheckItemBean> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public JudgeAdapter(Context context, List<CheckAllBean.DataBean.CheckItemBean> stringList) {
        mContext = context;
        mLists = stringList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recy_judge, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        CheckAllBean.DataBean.CheckItemBean bean = mLists.get(position);
        vh.mItemJudgePos.setText(String.valueOf(position + 1));
        vh.mItemJudgeTitle.setText(bean.getCheckItemName());
        vh.mItemJudgeContent.setText(TextUtils.isEmpty(bean.getCheckItemCode()) ? "" : bean.getCheckItemCode());
        vh.mItemJudgeGroup.setVisibility(View.GONE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        vh.mItemJudgeRecy.setLayoutManager(linearLayoutManager);
//        vh.mItemJudgeRecy.addItemDecoration(new GridSpacingItemDecoration(4, ScreenUtils.dip2px(mContext, 8), false));?????????????????????????????????????????????????????????
        ItemCheckAdapter itemCheckAdapter = new ItemCheckAdapter(mContext,bean.getPicLists());
        itemCheckAdapter.setOnAddPicClickListener(new ItemCheckAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onPicAddListener(position);
                }
            }
        });
        vh.mItemJudgeRecy.setAdapter(itemCheckAdapter);
        itemCheckAdapter.setOnItemClickListener(new ItemCheckAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                List<LocalMedia> selectList = itemCheckAdapter.getData();
                shwoLook(selectList, pos);
            }
        });

        int type = bean.getType();
        if (type == 0) {
            int typeone = bean.getTypeone();
            boolean ok = (boolean) bean.isOk();
            vh.mItemJudgeCheck.setText(typeone == 0 ? "?????????" : (typeone == 1 ? "??????" : "?????????"));
            vh.mItemJudgeCheck.setTextColor(mContext.getResources().getColor(typeone == 0 ? R.color.picture_color_light_grey : R.color.white));
            vh.mItemJudgeCheck.setBackgroundResource(typeone == 0 ? R.drawable.bg_circle_gray : (typeone == 1 ? R.drawable.bg_appcolor : R.drawable.bg_circle_red));
            vh.mItemJudgeGroup.setVisibility(typeone==2?View.VISIBLE:View.GONE);
//            vh.mItemJudgeCheck.setText(ok ? "??????" : "?????????");
//            vh.mItemJudgeCheck.setTextColor(mContext.getResources().getColor(ok ? R.color.white : R.color.picture_color_light_grey));
//            vh.mItemJudgeCheck.setBackgroundResource(ok ? R.drawable.bg_appcolor : R.drawable.bg_circle_gray);
//            vh.mItemJudgeGroup.setVisibility(ok ? View.GONE : View.VISIBLE);
        } else if (type == 1) {
            int typetwo = bean.getTypetwo();
            vh.mItemJudgeCheck.setText(typetwo == 0 ? "?????????" : (typetwo == 1 ? "??????" : "?????????"));
            vh.mItemJudgeCheck.setTextColor(mContext.getResources().getColor(typetwo == 0 ? R.color.picture_color_light_grey : R.color.white));
            vh.mItemJudgeCheck.setBackgroundResource(typetwo == 0 ? R.drawable.bg_circle_gray : (typetwo == 1 ? R.drawable.bg_appcolor : R.drawable.bg_circle_red));
            vh.mItemJudgeGroup.setVisibility(typetwo==2?View.VISIBLE:View.GONE);
//            boolean nogz = (boolean) bean.isNogz();
//            vh.mItemJudgeCheck.setText(nogz ? "??????" : "?????????");
//            vh.mItemJudgeCheck.setTextColor(mContext.getResources().getColor(nogz ? R.color.white : R.color.picture_color_light_grey));
//            vh.mItemJudgeCheck.setBackgroundResource(nogz ? R.drawable.bg_appcolor : R.drawable.bg_circle_gray);
//            vh.mItemJudgeGroup.setVisibility(nogz ? View.GONE : View.VISIBLE);
        }
        vh.mItemJudgeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClickListener(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos);

        void onPicAddListener(int pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_judge_pos)
        TextView mItemJudgePos;
        @BindView(R.id.item_judge_title)
        TextView mItemJudgeTitle;
        @BindView(R.id.item_judge_content)
        TextView mItemJudgeContent;
        @BindView(R.id.item_judge_check)
        TextView mItemJudgeCheck;
        @BindView(R.id.item_judge_edtrea)
        EditText mItemJudgeEdtRea;
        @BindView(R.id.item_judge_recy)
        RecyclerView mItemJudgeRecy;
        @BindView(R.id.item_judge_group)
        Group mItemJudgeGroup;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void shwoLook(List<LocalMedia> selectList, int position) {
        if (selectList.size() > 0) {
            LocalMedia media = selectList.get(position);
            String mimeType = media.getMimeType();
            int mediaType = PictureMimeType.getMimeType(mimeType);
            PictureSelector.create((Activity) mContext)
                    .themeStyle(R.style.picture_default_style) // xml????????????
                    //.setPictureWindowAnimationStyle(animationStyle)// ???????????????????????????
                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// ????????????Activity????????????????????????????????????
                    .isNotPreviewDownload(true)// ????????????????????????????????????
                    //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// ???????????????????????????????????????????????????????????????????????????
                    .imageEngine(GlideEngine.createGlideEngine())// ??????????????????????????????????????????
                    .openExternalPreview(position, selectList);
        }
    }


}
