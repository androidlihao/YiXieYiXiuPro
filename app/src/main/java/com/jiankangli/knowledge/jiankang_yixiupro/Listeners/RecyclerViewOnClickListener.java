package com.jiankangli.knowledge.jiankang_yixiupro.Listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by 李浩 on 2018/6/11.
 */

public class RecyclerViewOnClickListener implements RecyclerView.OnItemTouchListener{
    private GestureDetector mGestureDetector;
    private OnItemClickListener onItemClickListener;
    //自定义接口，用来实现回调
    public static interface OnItemClickListener {
        void onItemClick(View view,int postion);
        void onItemLongClick(View view,int postion);
    }
    //构造方法
    public RecyclerViewOnClickListener(Context context, final RecyclerView recyclerView, final OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
        mGestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){//手势处理
            @Override
            public boolean onSingleTapUp(MotionEvent e) {//手抬起的时候，触发这个动作
                View childView=recyclerView.findChildViewUnder(e.getX(),e.getY());//得到触摸的子控件
                if (childView!=null&&onItemClickListener!=null){
                   onItemClickListener.onItemClick(childView,recyclerView.getChildLayoutPosition(childView));
                   return true;
                }
                return super.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View childView=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (childView!=null&&onItemClickListener!=null){
                    onItemClickListener.onItemLongClick(childView,recyclerView.getChildAdapterPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        //当触发触摸手势的时候，将这个事件拦截给mGestureDetector来处理
        if (mGestureDetector.onTouchEvent(e)){
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
