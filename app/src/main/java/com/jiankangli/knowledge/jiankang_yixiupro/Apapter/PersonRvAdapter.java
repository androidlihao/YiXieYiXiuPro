package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PerOptions;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 李浩 on 2018/6/11.
 */

public class PersonRvAdapter extends RecyclerView.Adapter<PersonRvAdapter.PersonViewHolder> {
    private final LayoutInflater myLayoutInflater;
    private Context context;
    private LinkedList<PerOptions> list;

    //构造函数
    public PersonRvAdapter(Context context, LinkedList<PerOptions> list) {
        this.context = context;
        this.list = list;//资源
        //构建插入器
        myLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //构建viewholder
        return new PersonViewHolder(myLayoutInflater.inflate(R.layout.person_card_id, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        //绑定viewholder
         holder.ivIcoId.setImageResource(list.get(position).getIvRes());
         holder.tvTitleId.setText(list.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_ico_id)
        ImageView ivIcoId;
        @BindView(R.id.tv_title_id)
        TextView tvTitleId;
        @BindView(R.id.iv_back_id)
        ImageView ivBackId;
        @BindView(R.id.ll_card)
        LinearLayout llCard;

        PersonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
