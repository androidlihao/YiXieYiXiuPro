package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.PollingFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.RepairOrderFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.UpKeepOrderFragment;


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
                fragment=new PollingFragment();
                break;
            case "备件":
                break;
        }
        Bundle bundle=new Bundle();
        bundle.putInt("pos",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public OrderFragmentPageApapter(FragmentManager fm, String[] titles,String type){
        super(fm);
        this.titles=titles;//标题
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
