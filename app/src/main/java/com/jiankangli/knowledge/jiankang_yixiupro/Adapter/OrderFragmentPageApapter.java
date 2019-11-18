package com.jiankangli.knowledge.jiankang_yixiupro.Adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.polling.PollingOrderFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.repair_order.RepairOrderFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.spare_parts.SparePartsFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.up_keep_oreder.UpKeepOrderFragment;


/**
 * Created by 李浩 on 2018/6/21.
 */

public class OrderFragmentPageApapter extends FragmentPagerAdapter{
    String[] titles;
    String type;

    public OrderFragmentPageApapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("TAG", "getItem: "+titles[position]);
        Fragment fragment=null;
        switch (type){
            case "维修":
                fragment=new RepairOrderFragment();
                break;
            case "保养":
                fragment=new UpKeepOrderFragment();
                break;
            case "巡检":
                fragment=new PollingOrderFragment();
                break;
            case "备件":
                fragment=new SparePartsFragment();
                break;
        }
        Bundle bundle=new Bundle();
        bundle.putInt("pos",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public OrderFragmentPageApapter(FragmentManager fm, String[] titles,String type){
        super(fm);
        //标题
        this.titles=titles;
        this.type=type;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
