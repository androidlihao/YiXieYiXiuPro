package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.RepairOrderFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.UpKeepOrderFragment;

/**
 * Created by 李浩 on 2018/6/26.
 */

public class UpKeepAdapter extends FragmentPagerAdapter {
    private  String[] titles;
    public UpKeepAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        UpKeepOrderFragment fragment=new UpKeepOrderFragment(titles[position],position);
        Bundle bundle=new Bundle();
        bundle.putInt("pos",position);
        fragment.setArguments(bundle);
        //position为当前位置
        return fragment;

    }

    public UpKeepAdapter(FragmentManager fm, String[] titles){
        super(fm);
        this.titles=titles;//标题
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
