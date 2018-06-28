package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.Chat;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 李浩 on 2018/6/28.
 */

public class Recycler_MmbOnlineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int RIGHT_CODE = 1;
    public static final int LEFT_CODE = 2;
    public List<Chat.DataBean.ListBean> datalist;
    public Context context;
    public String userId;
    public Recycler_MmbOnlineAdapter(List<Chat.DataBean.ListBean> datalist, Context context, String userId) {
        this.datalist = datalist;
        this.context = context;
        this.userId = userId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case RIGHT_CODE:
                View v = inflater.inflate(R.layout.msg_item_right, parent, false);
                holder = new RightViewHolder(v);
                break;
            case LEFT_CODE:
                View view = inflater.inflate(R.layout.msg_item_left, parent, false);
                holder = new LeftViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case RIGHT_CODE:
                RightViewHolder rightViewHolder= (RightViewHolder) holder;
                Picasso.get().load(datalist.get(position).getHeadUrl())
                        .error(R.mipmap.home_touxiang)
                        .into(rightViewHolder.meIcon);
                rightViewHolder.tvMe.setText(datalist.get(position).getContent());
                rightViewHolder.tvTime.setText(datalist.get(position).getSendTime());
                break;
            case LEFT_CODE:
                LeftViewHolder leftViewHolder= (LeftViewHolder) holder;
                Picasso.get().load(datalist.get(position).getHeadUrl())
                        .error(R.mipmap.home_touxiang)
                        .into(leftViewHolder.ivOther);
                leftViewHolder.tv2Content.setText(datalist.get(position).getContent());
                leftViewHolder.tv2Time.setText(datalist.get(position).getSendTime());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int SendUserId = datalist.get(position).getSendUserId();
        if (userId.equals(SendUserId + "")) {
            return RIGHT_CODE;
        } else {
            return LEFT_CODE;
        }
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class RightViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.me_icon)
        CircleImageView meIcon;
        @BindView(R.id.tv_me)
        TextView tvMe;
        @BindView(R.id.tv_time)
        TextView tvTime;
        public RightViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class LeftViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_other)
        CircleImageView ivOther;
        @BindView(R.id.tv2_content)
        TextView tv2Content;
        @BindView(R.id.tv2_time)
        TextView tv2Time;
        public LeftViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
