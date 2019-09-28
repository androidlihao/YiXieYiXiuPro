package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * @author : fangqi
 * @date: 2019-02-14 12:16
 * Description:动画工具类
 */
public class AnimationUtil {

    /**
     * 缩小或放大ViewGroup
     * @param viewGroup 需缩放的视图容器
     * @param width 宽
     * @param height 高
     * @param duration 动画时长
     */
    public static void zoomViewGroup(final ViewGroup viewGroup, int width, int height, long duration) {
        ValueAnimator widthAnimator = ValueAnimator.ofInt(viewGroup.getLayoutParams().width, width);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                viewGroup.getLayoutParams().width = (int) animation.getAnimatedValue();
                viewGroup.requestLayout();
            }
        });

        ValueAnimator heightAnimator = ValueAnimator.ofInt(viewGroup.getLayoutParams().height, height);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                viewGroup.getLayoutParams().height = (int) animation.getAnimatedValue();
                viewGroup.requestLayout();
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(widthAnimator,heightAnimator);
        animatorSet.setDuration(duration);
        animatorSet.start();
    }

    /**
     * 闪烁效果
     * @param view 需要添加闪烁效果的View
     */
    public static void twinkleView(View view) {
        Animation textAnimation = new AlphaAnimation(1, 0);
        textAnimation.setDuration(500);
        textAnimation.setInterpolator(new LinearInterpolator());
        textAnimation.setRepeatCount(Animation.INFINITE);
        textAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(textAnimation);
    }

}
