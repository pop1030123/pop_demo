package com.pop.demo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Yellow5A5 on 15/12/18.
 */
public class ElasticRoundView extends View {

    //变化因子，用于设置拖动距离与半径变化的关系
    private final int CHANGE_FACTOR = 8;

    private int density;
    private int displayWidth;
    private int displayHeight;

    //中心坐标
    private float mCenterX;
    private float mCenterY;
    //移动的圆中心坐标
    private float mMovingX;
    private float mMovingY;

    //初始半径记录
    private float mStartRadius;
    //中心的圆半径
    private float mCenterRadius;
    //移动的圆半径
    private float mMovingRadius;
    //限制拖动范围
    private float mLimit;
    //标记最后ACTION_UP的坐标
    private float mEndX, mEndY;

    private Path mPath;
    private Paint mPaint;
    private ValueAnimator animator;

    public ElasticRoundView(Context context) {
        this(context, null);
    }

    public ElasticRoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElasticRoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        density = (int) getResources().getDisplayMetrics().density;
        displayWidth = getResources().getDisplayMetrics().widthPixels;
        displayHeight = getResources().getDisplayMetrics().heightPixels;

        mCenterX = displayWidth / 2;
        mCenterY = displayHeight / 2;
        mCenterRadius = density * 25;
        mStartRadius = mCenterRadius;

        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#ff5777"));
        mPaint.setAntiAlias(true);//去除锯齿
        mPaint.setStyle(Paint.Style.FILL);

        mMovingX = mCenterX;
        mMovingY = mCenterY;
        mMovingRadius = mCenterRadius;
        mLimit = 7 * mCenterRadius;
        initAnim();
        updatePath();
    }

    //设置回归动画
    private void initAnim() {
        animator = ValueAnimator.ofFloat(1f, 0f).setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMovingX = mCenterX + ((mEndX - mCenterX) * (float) animation.getAnimatedValue());
                mMovingY = mCenterY + ((mEndY - mCenterY) * (float) animation.getAnimatedValue());
                mCenterRadius = mStartRadius - vectorToPoint(mCenterX, mCenterY, mMovingX, mMovingY) / CHANGE_FACTOR;
                mMovingRadius = mStartRadius - mCenterRadius;
                updatePath();
                invalidate();
            }
        });
    }

    //更新路径参数
    private void updatePath() {
        if (mMovingY == mCenterY || mMovingX == mCenterX)
            return;
        double corners = Math.atan((mMovingY - mCenterY) / (mMovingX - mCenterX));

        float offsetX1 = (float) (mCenterRadius * Math.sin(corners));
        float offsetY1 = (float) (mCenterRadius * Math.cos(corners));

        float offsetX2 = (float) (mMovingRadius * Math.sin(corners));
        float offsetY2 = (float) (mMovingRadius * Math.cos(corners));

        float x1 = mCenterX - offsetX1;
        float y1 = mCenterY + offsetY1;

        float x2 = mMovingX - offsetX2;
        float y2 = mMovingY + offsetY2;

        float x3 = mMovingX + offsetX2;
        float y3 = mMovingY - offsetY2;

        float x4 = mCenterX + offsetX1;
        float y4 = mCenterY - offsetY1;

        float midpointX = (mCenterX + mMovingX) / 2;
        float midpointY = (mCenterY + mMovingY) / 2;

        mPath.reset();
        mPath.moveTo(x1, y1);
        mPath.quadTo(midpointX, midpointY, x2, y2);
        mPath.lineTo(x3, y3);
        mPath.quadTo(midpointX, midpointY, x4, y4);
        mPath.lineTo(x1, y1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        float temp = 0;
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                if (x < mCenterX - mCenterRadius || x > mCenterX + mCenterRadius || y < mCenterY - mCenterRadius || y > mCenterY + mCenterRadius) {
                    return false;
                }
            case MotionEvent.ACTION_MOVE:
                mMovingX = x;
                mMovingY = y;
                temp = vectorToPoint(mCenterX, mCenterY, mMovingX, mMovingY);
                if (temp > mLimit) {//限制拖动长度。
                    float multiple = mLimit / temp;
                    mMovingX = (mMovingX - mCenterX) * multiple + mCenterX;
                    mMovingY = (mMovingY - mCenterY) * multiple + mCenterY;
                    temp = mLimit;
                }
                mCenterRadius = mStartRadius - temp / CHANGE_FACTOR;
                mMovingRadius = mStartRadius - mCenterRadius;
                updatePath();
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                //限制拖动长度。
                mEndX = mMovingX;
                mEndY = mMovingY;
                animator.start();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCenterX, mCenterY, mCenterRadius, mPaint);
        canvas.drawCircle(mMovingX, mMovingY, mMovingRadius, mPaint);
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