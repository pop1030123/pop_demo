package com.pop.demo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by pengfu on 16/11/13.
 */

public class TimeTickView extends View {

    private static final String TAG = "TimeTickView" ;
    /**
     * 圆心
     */
    private int [] mCircleCenter ;

    /**
     * 圆的半径
     */
    private int mRadius ;

    /**
     * 当前秒点的位置
     */
    private float [] mMovePosition = new float[2];

    /**
     * 转圈动画器
     */
    private ValueAnimator mAnimator ;

    /**
     * 秒点画笔
     */
    private Paint mPaint ;
    /**
     * 圆框画笔
     */
    private Paint mBorderPaint ;

    /**
     * 上一次绘制角度，避免相同角度重绘；
     */
    private float mLastRate ;

    public TimeTickView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeTickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(int[] center  ,int radius){
        mCircleCenter = center ;
        mRadius = radius ;

        mAnimator = ValueAnimator.ofFloat(180,-180) ;// 从正上方开始，顺时针画圈，最后回到起点；
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(1000) ; // 1秒绘制一次;
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rate = Float.parseFloat(animation.getAnimatedValue().toString()) ;
                if(rate == mLastRate){
                    return;
                }
                mLastRate = rate ;
                double x_a = mRadius*Math.sin(Math.toRadians(rate)) ;
                double y_b = mRadius*Math.cos(Math.toRadians(rate)) ;
                Log.d(TAG ,"rate:"+rate+"::["+x_a+","+y_b+"]") ;
                mMovePosition[0] = (float) (mCircleCenter[0] + x_a) ;
                mMovePosition[1] = (float) (mCircleCenter[1] + y_b) ;
                invalidate();
            }
        });

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        mPaint.setColor(Color.RED);
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        mBorderPaint.setColor(Color.RED);
        mBorderPaint.setStrokeWidth(2);
        mBorderPaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 开始转圈，一般在activity的onresume里面调用
     */
    public void start(){
        mAnimator.start();
    }

    /**
     * 结束转圈，一般在activity的onPause里面调用，避免内存泄露
     */
    public void cancel(){
        mAnimator.cancel();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCircleCenter[0] ,mCircleCenter[1] ,mRadius ,mBorderPaint);
        canvas.drawCircle(mMovePosition[0] ,mMovePosition[1] ,20 ,mPaint);
    }
}
