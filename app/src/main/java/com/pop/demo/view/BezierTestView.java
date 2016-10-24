package com.pop.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by fupeng on 16/10/24.
 */
public class BezierTestView extends View {

    private static final String TAG = "BezierTestView";

    private int density;
    private int displayWidth;
    private int displayHeight;

    //中心参考坐标
    private float mCenterX;
    private float mCenterY;
    //移动的点坐标
    private float mMovingX;
    private float mMovingY;

    //标记最后ACTION_UP的坐标
    private float mEndX, mEndY;

    private Path mPath;
    private Paint mPaint;

    public BezierTestView(Context context) {
        this(context, null);
    }

    public BezierTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        density = (int) getResources().getDisplayMetrics().density;
        displayWidth = getResources().getDisplayMetrics().widthPixels;
        displayHeight = getResources().getDisplayMetrics().heightPixels;

        mCenterX = displayWidth / 2;
        mCenterY = displayHeight / 2;

        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#ff5777"));
        mPaint.setAntiAlias(true);//去除锯齿
        mPaint.setStyle(Paint.Style.STROKE);

        mMovingX = mCenterX;
        mMovingY = mCenterY;
        updatePath();
    }

    //更新路径参数
    private void updatePath() {
        float x1 ,y1 ,x2 ,y2 ;
        int diff = 200 ;
        // 计算x1 ,y1 ;
        x1 = mCenterX + diff ;
        y1 = mCenterY - diff ;
        // 计算x2 ,y2 ;
        x2 = mCenterX - diff ;
        y2 = mCenterY + diff ;

        mPath.reset();
        mPath.moveTo(x1, y1);
        mPath.quadTo(mMovingX ,mMovingY ,x2 ,y2);

        mPath.moveTo(x1, y1);
        mPath.quadTo(mMovingX-100, mMovingY-100,x2 ,y2);
        // 标注点
        mPath.addCircle(mMovingX-100, mMovingY-100 ,5 , Path.Direction.CW);
        mPath.addCircle(x1 ,y1 ,5 , Path.Direction.CW);
        mPath.addCircle(x2 ,y2 ,5 , Path.Direction.CW);
        mPath.addCircle(mMovingX ,mMovingY ,10 , Path.Direction.CW);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mMovingX = x;
                mMovingY = y;
                updatePath();
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                mEndX = mMovingX;
                mEndY = mMovingY;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画圆标记中心
        canvas.drawPath(mPath, mPaint);
    }
}