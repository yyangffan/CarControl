package com.hncd.carcontrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hncd.carcontrol.R;

import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class JudgeAdapter extends RecyclerView.Adapter<JudgeAdapter.ViewHolder> {
    private Context mContext;
    private List<Map<String, Object>> mLists;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public JudgeAdapter(Context context, List<Map<String, Object>> stringList) {
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
        Map<String, Object> bean = mLists.get(position);
        vh.mItemJudgePos.setText(String.valueOf(position + 1));
        vh.mItemJudgeTitle.setText("车辆识别代号" + position);
        vh.mItemJudgeContent.setText("Loweonnwegw");
        int type = (int) bean.get("type");
        if (type == 0) {
            boolean ok = (boolean) bean.get("ok");
            vh.mItemJudgeCheck.setText(ok ? "已判定" : "未判定");
            vh.mItemJudgeCheck.setTextColor(mContext.getResources().getColor(ok ? R.color.white : R.color.picture_color_light_grey));
            vh.mItemJudgeCheck.setBackgroundResource(ok ? R.drawable.bg_appcolor : R.drawable.bg_circle_gray);
        } else if (type == 1) {
            boolean nogz = (boolean) bean.get("nogz");
            vh.mItemJudgeCheck.setText(nogz ? "已判定" : "未判定");
            vh.mItemJudgeCheck.setTextColor(mContext.getResources().getColor(nogz ? R.color.white : R.color.picture_color_light_grey));
            vh.mItemJudgeCheck.setBackgroundResource(nogz ? R.drawable.bg_appcolor : R.drawable.bg_circle_gray);
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
