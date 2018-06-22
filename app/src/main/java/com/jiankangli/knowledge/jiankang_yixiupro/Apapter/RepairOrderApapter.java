package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.RepairOrderFragment;


/**
 * Created by 李浩 on 2018/6/21.
 */

public class RepairOrderApapter extends FragmentPagerAdapter{
    String[] titles;

    public RepairOrderApapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("TAG", "getItem: "+position);
        RepairOrderFragment fragment=new RepairOrderFragment(titles[position],position);
        Bundle bundle=new Bundle();
        bundle.putString("pos",titles[position]);
        fragment.setArguments(bundle);
        //position为当前位置
        return fragment;
    }

    public RepairOrderApapter(FragmentManager fm, String[] titles){
        super(fm);
        this.titles=titles;//标题
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
