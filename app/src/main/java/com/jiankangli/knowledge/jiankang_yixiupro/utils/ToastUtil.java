package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * @author : lihao
 * @date: 2019-01-17 17:45
 * @description : 吐司管理工具类
 */
public class ToastUtil {

    private static final int DEFAULT_COLOR = 0x12000000;
    private static Toast sToast;
    /**
     * 默认toast位置（底部居中）
     */
    private static int gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private static int xOffset = 0;
    private static int yOffset;
    private static int backgroundColor = DEFAULT_COLOR;
    private static int bgResource = -1;
    private static int messageColor = DEFAULT_COLOR;
    private static WeakReference<View> sViewWeakReference;
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    private ToastUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 设置吐司显示位置
     *
     * @param gravity 显示位置
     * @param xOffset x方向偏移
     * @param yOffset y方向偏移
     * @param context 上下文对象
     */
    public static void setGravity(int gravity, int xOffset, int yOffset, Context context) {
        ToastUtil.gravity = gravity;
        ToastUtil.xOffset = xOffset;
        ToastUtil.yOffset = yOffset;
    }

    /**
     * 设置toast view布局
     *
     * @param layoutId 视图资源id
     * @param context  上下文对象
     */
    public static void setView(@LayoutRes int layoutId, Context context) {
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sViewWeakReference = new WeakReference<>(inflate.inflate(layoutId, null));
    }

    /**
     * 设置toast view
     *
     * @param view 视图对象
     */
    public static void setView(@Nullable View view) {
        sViewWeakReference = view == null ? null : new WeakReference<>(view);
    }

    /**
     * 获取toast view
     *
     * @return toast view (maybe null)
     */
    public static View getView() {
        if (sViewWeakReference != null) {
            final View view = sViewWeakReference.get();
            if (view != null) {
                return view;
            }
        }
        if (sToast != null) {
            return sToast.getView();
        }
        return null;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor 背景颜色
     */
    public static void setBackgroundColor(@ColorInt int backgroundColor) {
        ToastUtil.backgroundColor = backgroundColor;
    }

    /**
     * 设置背景资源
     *
     * @param bgResource 背景资源
     */
    public static void setBgResource(@DrawableRes int bgResource) {
        ToastUtil.bgResource = bgResource;
    }

    /**
     * 设置内容文本颜色
     *
     * @param messageColor 字体颜色值
     */
    public static void setMessageColor(@ColorInt int messageColor) {
        ToastUtil.messageColor = messageColor;
    }

    /**
     * 短时间显示toast
     *
     * @param text    显示文本
     * @param context 上下文对象
     */
    public static void showShortSafe(final CharSequence text, final Context context) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(text, Toast.LENGTH_SHORT, context);
            }
        });
    }

    /**
     * 短时间显示toast
     *
     * @param resId   string类型资源id
     * @param context
     */
    public static void showShortSafe(final @StringRes int resId, final Context context) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_SHORT, context);
            }
        });
    }

    /**
     * 短时间显示toast
     *
     * @param resId   资源Id
     * @param context 上下文对象
     * @param args    附加参数
     */
    public static void showShortSafe(final @StringRes int resId, final Context context, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(resId, context, Toast.LENGTH_SHORT, args);
            }
        });
    }
    
    /**
     * 长时间显示toast
     *
     * @param text    内容文本
     * @param context 上下文对象
     */
    public static void showLongSafe(final CharSequence text, final Context context) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(text, Toast.LENGTH_LONG, context);
            }
        });
    }

    /**
     * 长时间显示toast
     *
     * @param resId   资源id
     * @param context 上下文对象
     */
    public static void showLongSafe(final @StringRes int resId, final Context context) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_LONG, context);
            }
        });
    }

    /**
     * 长时间显示toast
     *
     * @param resId   资源id
     * @param context 上下文对象
     * @param args    附加参数
     */
    public static void showLongSafe(final @StringRes int resId, final Context context, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(resId, context, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 长时间显示toast
     *
     * @param format 格式化格式
     * @param args   附加参数
     */
    public static void showLongSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 短时间显示toast
     *
     * @param text    文本内容
     * @param context 上下文对象
     */
    public static void showShort(CharSequence text, Context context) {
        show(text, Toast.LENGTH_SHORT, context);
    }

    /**
     * 短时间显示toast
     *
     * @param resId   资源id
     * @param context 上下文对象
     */
    public static void showShort(@StringRes int resId, Context context) {
        show(resId, Toast.LENGTH_SHORT, context);
    }

    /**
     * 短时间显示toast
     *
     * @param resId   资源id
     * @param context 上下文对象
     * @param args    附加参数
     */
    public static void showShort(@StringRes int resId, Context context, Object... args) {
        show(resId, context, Toast.LENGTH_SHORT, args);
    }

    /**
     * 短时间显示toast
     *
     * @param format 文本格式化名称
     * @param args
     */
    public static void showShort(String format, Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }


    /**
     * 长时间显示toast
     *
     * @param text    内容文本
     * @param context 上下文对象
     */
    public static void showLong(CharSequence text, Context context) {
        show(text, Toast.LENGTH_LONG, context);
    }

    /**
     * 长时间显示toast
     *
     * @param resId   资源id
     * @param context 上下文对象
     */
    public static void showLong(@StringRes int resId, Context context) {
        show(resId, Toast.LENGTH_LONG, context);
    }

    /**
     * 长时间显示toast
     *
     * @param resId   资源id
     * @param context 上下文对象
     * @param args    附加参数
     */
    public static void showLong(@StringRes int resId, Context context, Object... args) {
        show(resId, context, Toast.LENGTH_LONG, args);
    }

    /**
     * 长时间显示toast
     *
     * @param format 格式化方式
     * @param args   附加参数
     */
    public static void showLong(String format, Object... args) {
        show(format, Toast.LENGTH_LONG, args);
    }


    /**
     * 短时间显示自定义toast
     */
    public static void showCustomShortSafe() {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show("", Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 长时间显示自定义toast
     */
    public static void showCustomLongSafe() {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show("", Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 短时间显示自定义toast
     */
    public static void showCustomShort() {
        show("", Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示自定义toast
     */
    public static void showCustomLong() {
        show("", Toast.LENGTH_LONG);
    }

    /**
     * 显示toast
     *
     * @param resId    资源id
     * @param duration 显示时长
     * @param context  上下文对象
     */
    private static void show(@StringRes int resId, int duration, Context context) {
        show(context.getResources().getText(resId).toString(), duration);
    }

    /**
     * 显示toast
     *
     * @param resId    资源id
     * @param context  上下文对象
     * @param duration 显示时长
     * @param args     附加参数
     */
    private static void show(@StringRes int resId, Context context, int duration, Object... args) {
        show(String.format(context.getResources().getString(resId), args), duration);
    }

    /**
     * 显示toast
     *
     * @param format   格式化方式
     * @param duration 显示时长
     * @param args     附加参数
     */
    private static void show(String format, int duration, Object... args) {
        show(String.format(format, args), duration);
    }

    /**
     * 显示toast
     *
     * @param text     内容文本
     * @param duration 显示时长
     * @param context  上下文对象
     */
    private static void show(CharSequence text, int duration, Context context) {
        cancel();
        boolean isCustom = false;
        if (sViewWeakReference != null) {
            final View view = sViewWeakReference.get();
            if (view != null) {
                sToast = new Toast(context);
                sToast.setView(view);
                sToast.setDuration(duration);
                isCustom = true;
            }
        }
        if (!isCustom) {
            if (messageColor != DEFAULT_COLOR) {
                SpannableString spannableString = new SpannableString(text);
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(messageColor);
                spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sToast = Toast.makeText(context, spannableString, duration);
            } else {
                sToast = Toast.makeText(context, text, duration);
            }
        }
        View view = sToast.getView();
        if (bgResource != -1) {
            view.setBackgroundResource(bgResource);
        } else if (backgroundColor != DEFAULT_COLOR) {
            view.setBackgroundColor(backgroundColor);
        }
        yOffset = (int) (64 * context.getResources().getDisplayMetrics().density + 0.5);
        sToast.setGravity(gravity, xOffset, yOffset);
        sToast.show();
    }

    /**
     * 取消吐司显示
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
