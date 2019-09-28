package com.jiankangli.knowledge.jiankang_yixiupro.view;

/**
 * @author lihao
 * @date 2019-09-26 17:03
 * @description :
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jiankangli.knowledge.jiankang_yixiupro.utils.FileUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 可以签名的view
 * Created by 李浩 on 2019/9/14.
 */
public class SignatureView extends View {
    private static final String TAG = "SignatureView";
    private Paint mPathPaint;
    private Paint mBitmapPaint;
    private Canvas mCanvas;
    private Path mPath;
    private Bitmap mBitmap;
    /**
     * 画图区域的最大位置
     */
    private float mSmallX = 0, mSmallY = 0, mBigX = 0, mBigY = 0;
    private float mPreX, mPreY;

    public SignatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setDither(true);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(10);
        mPathPaint.setColor(Color.parseColor("#ff3f4a57"));
        mPathPaint.setStrokeJoin(Paint.Join.ROUND);//线段结束处的形状
        mPathPaint.setStrokeCap(Paint.Cap.ROUND);//线段开始结束处的形状
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        resetSign();
    }

    private void resetSign() {
        mSmallX = 0;
        mSmallY = 0;
        mBigX = 0;
        mBigY = 0;
        mPath = new Path();
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mBitmap.eraseColor(Color.WHITE);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath(mPath, mPathPaint);
    }

    /**
     * 计算画图区域的位置
     *
     * @param x
     * @param y
     */
    private void computeDrawMaxRang(float x, float y) {
        if (mSmallX == 0 && mSmallY == 0 && mBigX == 0 && mBigY == 0) {
            mSmallX = mBigX = x;
            mBigY = mSmallY = y;
        } else {
            mSmallX = Math.min(mSmallX, x);
            mSmallY = Math.min(mSmallY, y);
            mBigX = Math.max(mBigX, x);
            mBigY = Math.max(mBigY, y);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPreX = event.getX();
                mPreY = event.getY();
                computeDrawMaxRang(mPreX, mPreY);
                mPath.reset();
                mPath.moveTo(mPreX, mPreY);
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                computeDrawMaxRang(x, y);
                //绘制圆滑曲线（贝塞尔曲线）
                mPath.quadTo(mPreX, mPreY, x, y);
                mPreX = x;
                mPreY = y;
                break;
            case MotionEvent.ACTION_UP:
                computeDrawMaxRang(event.getX(), event.getY());
                mPath.lineTo(event.getX(), event.getY());
                mCanvas.drawPath(mPath, mPathPaint);
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 保存bitmap到文件
     *
     * @param
     * @return 成功返回true，反之false
     */
    @SuppressLint("WrongThread")
    public boolean SaveBitmapToFile(File file) {
        float signAreaWidth = mBigX - mSmallX;
        float signAreaHeight = mBigY - mSmallY;
        if (signAreaWidth <= 0 || signAreaHeight <= 0) {
            ToastUtil.showShortSafe("请签字", getContext());
            return false;
        }
        FileUtil.createOrExistsFile(file);
        if (file.exists()) {
            Bitmap bitmap = Bitmap.createBitmap(mBitmap, (int) mSmallX, (int) mSmallY, (int) signAreaWidth, (int) signAreaHeight, new Matrix(), true);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return false;
        }
    }

    public void reset() {
        resetSign();
        invalidate();
    }
}
