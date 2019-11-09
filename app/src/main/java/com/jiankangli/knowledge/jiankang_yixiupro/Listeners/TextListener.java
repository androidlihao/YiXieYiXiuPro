package com.jiankangli.knowledge.jiankang_yixiupro.Listeners;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author lihao
 * @date 2019-10-25 0:13
 * @description :
 */
public abstract class TextListener implements TextWatcher {
    private int layoutId;

    public TextListener(int layoutId) {
        this.layoutId=layoutId;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        onTextChange(layoutId,s);
    }

    public abstract void onTextChange(int layoutId,Editable s);
}
