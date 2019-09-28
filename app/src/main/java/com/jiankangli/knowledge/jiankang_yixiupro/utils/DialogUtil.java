package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


/**
 * @author : linjian
 * @date: 2019-01-17 9:21
 * @description : 对话框工具类
 */
public class DialogUtil {

    private static Calendar calendar = Calendar.getInstance();

    private static ProgressDialog progressDialog;

    public static ProgressDialog getProgressDialogInstance(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        return progressDialog;
    }

    /**
     * 显示一个进度条的对话框
     *
     * @param context 窗口的上下文对象
     * @param title   对话框提示的标题
     * @param message 对话框提示的内容
     * @return
     */
    public static ProgressDialog showProgressDialog(Context context, String title, String message) {
        ProgressDialog dialog = ProgressDialog.show(context, title, message);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.show();
        return dialog;
    }

    /**
     * 显示一个"提示" 标题确认的对话框
     *
     * @param context 窗口的上下文对象
     * @param message 对话框提示的内容
     */
    public static void showConfirmDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    /**
     * 显示一个新的弹窗
     *
     * @param context             窗口的上下文对象
     * @param title               对话框提示的标题
     * @param message             对话框提示的内容
     * @param positiveBtnListener 确定按钮的监听操作
     */
    public static void showPositiveDialog(Context context, String title, CharSequence message,
                                          DialogInterface.OnClickListener positiveBtnListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", positiveBtnListener);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    /**
     * 显示一个新的弹窗
     *
     * @param context             窗口的上下文对象
     * @param title               对话框提示的标题
     * @param message             对话框提示的内容
     * @param positiveBtnListener 确定按钮的监听操作
     * @param negativeBtnListener 取消按钮的监听操作
     */
    public static void showPositiveDialog(Context context, int title, int message,
                                          DialogInterface.OnClickListener positiveBtnListener, DialogInterface.OnClickListener negativeBtnListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", positiveBtnListener);
        builder.setNegativeButton("取消", negativeBtnListener);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    /**
     * 显示一个新的弹窗
     *
     * @param context             窗口的上下文对象
     * @param title               对话框提示的标题
     * @param message             对话框提示的内容
     * @param positiveBtnListener 确定按钮的监听操作
     * @param negativeBtnListener 取消按钮的监听操作
     */
    public static void showPositiveDialog(Context context, String title, String message,
                                          DialogInterface.OnClickListener positiveBtnListener, DialogInterface.OnClickListener negativeBtnListener) {
        String positiveText = "确定";
        String negativeText = "取消";
        showPositiveDialog(context, title, message, positiveText, negativeText, positiveBtnListener, negativeBtnListener);
    }

    /**
     * 显示一个新的弹窗
     *
     * @param context             窗口的上下文对象
     * @param title               对话框提示的标题
     * @param message             对话框提示的内容
     * @param positiveText        确定按钮显示的文本
     * @param negativeText        取消按钮显示的文本
     * @param positiveBtnListener 确定按钮的监听操作
     * @param negativeBtnListener 取消按钮的监听操作
     */
    public static void showPositiveDialog(Context context, String title, String message, String positiveText, String negativeText,
                                          DialogInterface.OnClickListener positiveBtnListener, DialogInterface.OnClickListener negativeBtnListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, positiveBtnListener);
        builder.setNegativeButton(negativeText, negativeBtnListener);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    /**
     * 显示一个日期选择的对话框
     *
     * @param context  窗口的上下文对象
     * @param listener 日期选择后的监听
     */
    public static void showDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener listener) {
        new DatePickerDialog(
                context,
                DatePickerDialog.THEME_HOLO_LIGHT,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 显示一个日期选择的对话框，并且定位到输入的时间
     */
    public static void showDatePickerDialog(Context context, String date, DatePickerDialog.OnDateSetListener listener) {
        if (!TextUtils.isEmpty(date)) {
            String[] strs = date.split("-");
            calendar.set(Integer.valueOf(strs[0]), Integer.valueOf(strs[1]) - 1, Integer.valueOf(strs[2]));
        }
        new DatePickerDialog(
                context,
                DatePickerDialog.THEME_HOLO_LIGHT,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 显示一个日期选择的对话框，并且定位到输入的时间
     */
    public static void showDatePicker(Context context, String date, DatePickerDialog.OnDateSetListener listener) {
        if (!TextUtils.isEmpty(date)) {
            try {
                Date dateByString = TimeUtil.getDateByString(date);
                calendar.setTime(dateByString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        new DatePickerDialog(
                context,
                DatePickerDialog.THEME_HOLO_LIGHT,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 显示一个自定义对话框
     *
     * @param context
     * @param
     */
    public static AlertDialog showCustomDialog(Context context, int ResId) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        View inflate = LayoutInflater.from(context).inflate(ResId, null);
        alertDialog.setView(inflate);
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = alertDialog.getWindow();
        WindowManager m = dialogWindow.getWindowManager();
        // 获取屏幕宽、高用
        Display d = m.getDefaultDisplay();
        // 获取对话框当前的参数值
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        // 设置高度和宽度
        p.height = (int) (d.getHeight() * 0.6);
        p.width = (int) (d.getWidth() * 1);
        //设置位置
        p.gravity = Gravity.CENTER;
        //设置透明度
        p.alpha = 0.9f;
        dialogWindow.setAttributes(p);
        return alertDialog;
    }

    /**
     * 设置一个自定义的Dialog
     */
    public static AlertDialog showCustomSetDialog(Context context, int ResId, String title, String message,
                                                  String positiveText, String negativeText,
                                                  DialogInterface.OnClickListener positiveBtnListener,
                                                  DialogInterface.OnClickListener negativeBtnListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = LayoutInflater.from(context).inflate(ResId, null);
        builder.setView(inflate);
        builder.setTitle(title);
        builder.setPositiveButton(positiveText, positiveBtnListener);
        builder.setNegativeButton(negativeText, negativeBtnListener);
        AlertDialog dialog = builder.create();
        return dialog;
    }


}
