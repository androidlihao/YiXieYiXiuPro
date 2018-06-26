package com.jiankangli.knowledge.jiankang_yixiupro.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.Recycler_UpKeepAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SharePreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by 李浩 on 2018/6/26.
 */

@SuppressLint("ValidFragment")
public class UpKeepOrderFragment extends RepairOrderFragment{

    private Recycler_UpKeepAdapter adapter;

    public UpKeepOrderFragment(String string, int pos) {
        super(string, pos);
    }

    //重写initview，修改适配器
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        pullLoadMoreRecyclerView.setLinearLayout();
        //准备适配器
        adapter = new Recycler_UpKeepAdapter(R.layout.list_item, data);
        //填充适配器
        pullLoadMoreRecyclerView.setAdapter(adapter);

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);

        pullLoadMoreRecyclerView.setFooterViewText("加载中..");

        pullLoadMoreRecyclerView.refresh();
    }
    //重写jsString方法，修改数据源
    @Override
    protected void JsonStrings(int code) {
        currentPage++;
        jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SharePreferenceUtils.get(getHolding(), "userId", -1 + ""));
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("pageNum", currentPage);//默认为第一页
            jsonObject.put("page", jsonObject1);
            jsonObject.put("Test","SDDD");
            int electronicStatus = getArguments().getInt("pos");
            jsonObject.put("electronicStatus", 1+electronicStatus+"");
            Log.i(TAG, "JsonStrings: "+jsonObject);
            getData(code,jsonObject);//执行请求操作
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
    }
}
