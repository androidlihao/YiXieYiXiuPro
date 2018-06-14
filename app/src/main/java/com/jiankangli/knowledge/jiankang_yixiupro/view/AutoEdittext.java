package com.jiankangli.knowledge.jiankang_yixiupro.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;


/**
 * Created by 李浩 on 2018/6/12.
 */

public class AutoEdittext extends LinearLayout {
    EditText etFeedBack;
    TextView textSize;
    private Context context;
    private TypedArray typedArray;
    private boolean ignoreCnOrEn;
    private Integer maxCount;
    private String hintText;
    private String contentText;
    private int hintTextColor;
    private int contentTextColor;
    private float contentViewHeight;
    private int contentTextSize;

    public AutoEdittext(Context context) {
        this(context,null);
    }

    public AutoEdittext(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoEdittext);//获取attrs中的属性值
        maxCount = typedArray.getInteger(R.styleable.AutoEdittext_classic_maxCount, 500);
        ignoreCnOrEn = typedArray.getBoolean(R.styleable.AutoEdittext_classic_ignoreCnOrEn, true);
        hintText = typedArray.getString(R.styleable.AutoEdittext_class_hintText);
        contentText = typedArray.getString(R.styleable.AutoEdittext_classic_contentText);
        hintTextColor = typedArray.getColor(R.styleable.AutoEdittext_classic_hintTextColor, getResources().getColor(R.color.colorgray));
        contentTextColor = typedArray.getColor(R.styleable.AutoEdittext_classic_contentTextColor, Color.parseColor("#333333"));
        contentViewHeight = typedArray.getDimensionPixelSize(R.styleable.AutoEdittext_classic_contentViewHeight,dp2px(context,140));
        contentTextSize = typedArray.getDimensionPixelSize(R.styleable.AutoEdittext_classic_contentTextSize, dp2px(context,14));
        typedArray.recycle();
        init();
    }

    private void init() {
        //获取布局填充器
        View rootview = LayoutInflater.from(context).inflate(R.layout.auto_edittext,this);
        etFeedBack=rootview.findViewById(R.id.et_feed_back);
        textSize=rootview.findViewById(R.id.text_size);
        if (this.getBackground()==null){
            this.setBackgroundResource(R.drawable.auto_edittext_backgroup);
        }
        etFeedBack.requestFocus();
        etFeedBack.setHint(hintText);//设置提醒
        etFeedBack.setHintTextColor(hintTextColor);
        etFeedBack.setText(contentText);
        etFeedBack.setTextColor(contentTextColor);
        etFeedBack.setTextSize(TypedValue.COMPLEX_UNIT_PX,contentTextSize);
        etFeedBack.setHeight((int) contentViewHeight);
        etFeedBack.addTextChangedListener(textWatcher);
        configCount();//动态更改下面的限制书
        etFeedBack.setSelection(etFeedBack.length());//光标在最后
        textSize.requestFocus();
        etFeedBack.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                AutoEdittext.this.setSelected(hasFocus);
            }
        });
    }

    //监控器
    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
             int editStart=etFeedBack.getSelectionStart();
             int editEnd=etFeedBack.getSelectionEnd();
            // 先去掉监听器，否则会出现栈溢出
            etFeedBack.removeTextChangedListener(textWatcher);
            if (ignoreCnOrEn) {
                //当输入字符个数超过限制的大小时，进行截断操作
                while (calculateLengthIgnoreCnOrEn(editable.toString()) > maxCount) {
                    editable.delete(editStart - 1, editEnd);
                    editStart--;
                    editEnd--;
                }
            } else {
                // 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
                while (calculateLength(editable.toString()) > maxCount) { // 当输入字符个数超过限制的大小时，进行截断操作
                    editable.delete(editStart - 1, editEnd);
                    editStart--;
                    editEnd--;
                }
            }
            etFeedBack.setSelection(editStart);
            // 恢复监听器
            etFeedBack.addTextChangedListener(textWatcher);
            //更新限制数字
            configCount();

        }
    };
    private static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    private long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    private int calculateLengthIgnoreCnOrEn(CharSequence c) {
        int len = 0;
        for (int i = 0; i < c.length(); i++) {
            len++;
        }
        return len;
    }

    private void configCount() {
        long nowCount;
        if (ignoreCnOrEn) {
             nowCount = calculateLengthIgnoreCnOrEn(etFeedBack.getText().toString());
            textSize.setText(String.valueOf((maxCount - nowCount)) + "/" + maxCount);
        } else {
             nowCount = calculateLength(etFeedBack.getText().toString());
            textSize.setText(String.valueOf((maxCount - nowCount)) + "/" + maxCount);
        }
        if (maxCount == nowCount) {
            ToastUtils.showToast(context, "最多输入" + maxCount  + "个字符");
        }
    }
    public void setContentText(String content) {
        contentText = content;
        if (etFeedBack == null) {
            return;
        }
        etFeedBack.setText(contentText);
    }

    public String getContentText() {
        if (etFeedBack != null) {
            contentText = etFeedBack.getText() == null ? "" : etFeedBack.getText().toString();
        }
        return contentText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
        if (etFeedBack == null) {
            return;
        }
        etFeedBack.setHint(hintText);
    }

    public void setContentTextSize(int size) {
        if (etFeedBack == null) {
            return;
        }
        etFeedBack.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setContentTextColor(int color) {
        if (etFeedBack == null) {
            return;
        }
        etFeedBack.setTextColor(color);
    }

    public void setHintColor(int color) {
        if (etFeedBack == null) {
            return;
        }
        etFeedBack.setHintTextColor(color);
    }

    public String getHintText() {
        if (etFeedBack != null) {
            hintText = etFeedBack.getHint() == null ? "" : etFeedBack.getHint().toString();
        }
        return hintText;
    }

    public void setMaxCount(int max_count) {
        this.maxCount = max_count;
        configCount();
    }

    public void setIgnoreCnOrEn(boolean ignoreCnOrEn) {
        this.ignoreCnOrEn = ignoreCnOrEn;
        configCount();
    }
}
