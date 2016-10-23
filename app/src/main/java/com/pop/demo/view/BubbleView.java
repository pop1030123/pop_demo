package com.pop.demo.view;

import android.animation.ValueAnimator;
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
 * Created by fupeng on 15/12/18.
 */
public class BubbleView extends View {

    private static final String TAG = "BubbleView";
    //变化因子，用于设置拖动距离与半径变化的关系
    private final int CHANGE_FACTOR = 8;

    // 变化因子 ,可以改变对话框的宽度，数值越大，宽度越宽
    private static final int BUBBLE_FACTOR = 150 ;
    private static final int BUBBLE_SIZE =250 ;

    private int density;
    private int displayWidth;
    private int displayHeight;

    //中心参考坐标
    private float mCenterX;
    private float mCenterY;
    //移动的点坐标
    private float mMovingX;
    private float mMovingY;

    //限制拖动范围
    private float mLimit;
    //标记最后ACTION_UP的坐标
    private float mEndX, mEndY;

    private Path mPath;
    private Paint mPaint;

    public BubbleView(Context context) {
        this(context, null);
    }

    public BubbleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mLimit = 7 * BUBBLE_FACTOR;
        updatePath();
    }

    //更新路径参数
    private void updatePath() {
        if (mMovingY == mCenterY || mMovingX == mCenterX)
            return;
        float m = vectorToPoint(mMovingX ,mMovingY ,mCenterX ,mCenterY) ;
        float x = Math.abs(mMovingX - mCenterX) ;
        float y = Math.abs(mMovingY - mCenterY) ;
        float dis = BUBBLE_FACTOR/2 ;

        float diff_x = dis*y / m ;
        float diff_y = dis*x / m ;

        // 计算x1 ,y1 ;
        float x1 = mCenterX - diff_x ;
        float y1 = mCenterY - diff_y ;
        // 计算x2 ,y2 ;
        float x2 = mCenterX + diff_x ;
        float y2 = mCenterY + diff_y ;
        Log.d(TAG ,"x1,y1:"+x1+","+y1) ;
        Log.d(TAG ,"x2,y2:"+x2+","+y2) ;
        Log.d(TAG ,"Cx,Cy:"+mCenterX+","+mCenterY) ;
        Log.d(TAG ,"Mx,My:"+mMovingX+","+mMovingY) ;
//        Log.d(TAG ,"x:"+x+":y:"+y) ;

        // 画出这个三角形
        mPath.reset();
        mPath.moveTo(x1, y1);
        mPath.lineTo(x2, y2);
        mPath.lineTo(mMovingX , mMovingY);
        mPath.close();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        float temp = 0;
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
//                if (x < mCenterX - mCenterRadius || x > mCenterX + mCenterRadius || y < mCenterY - mCenterRadius || y > mCenterY + mCenterRadius) {
//                    return false;
//                }
            case MotionEvent.ACTION_MOVE:
                mMovingX = x;
                mMovingY = y;
//                temp = vectorToPoint(mCenterX, mCenterY, mMovingX, mMovingY);
//                if (temp > mLimit) {//限制拖动长度。
//                    float multiple = mLimit / temp;
//                    mMovingX = (mMovingX - mCenterX) * multiple + mCenterX;
//                    mMovingY = (mMovingY - mCenterY) * multiple + mCenterY;
//                    temp = mLimit;
//                }
                updatePath();
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                //限制拖动长度。
                mEndX = mMovingX;
                mEndY = mMovingY;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画圆标记中心
        canvas.drawCircle(mCenterX ,mCenterY ,5,mPaint);
        canvas.drawRect(mCenterX-BUBBLE_SIZE, mCenterY-BUBBLE_SIZE, mCenterX+BUBBLE_SIZE,mCenterY+BUBBLE_SIZE, mPaint);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 计算两点之间的距离
     * @return 两点之间的距离
     */
    private float vectorToPoint(float X1, float Y1, float X2, float Y2) {
        return (float) Math.sqrt(Math.pow(Math.abs(X2 - X1), 2) + Math.pow(Math.abs(Y2 - Y1), 2));
    }
}