package com.pop.demo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.pop.demo.util.L;

/**
 * Created by pengfu on 12/04/2018.
 */

public class MovedView extends View {
    public MovedView(Context context) {
        super(context);
    }

    public MovedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MovedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float mLastX, mLastY;
    private float mX, mY;
    private long downTime =0  ;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        L.d("fupeng", "onTouchEvent:" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                mX = event.getX();
                mY = event.getY();

                if (mLastX != 0 && mLastY != 0) {
                    float moveX = mX - mLastX;
                    float moveY = mY - mLastY;

                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
                    lp.leftMargin += moveX;
                    lp.topMargin += moveY;
//                    L.d("fupeng" ,"移动了:"+moveX+","+moveY);
                    // 触发布局修改.
                    requestLayout();
                } else {
                    mLastX = mX;
                    mLastY = mY;
                }
                break;
            case MotionEvent.ACTION_UP:
                long upTime = System.currentTimeMillis() ;
                long delta = upTime - downTime ;
//                L.d("fupeng" ,"mLastXY:"+mLastX+","+mLastY +":delta:"+delta);
                // 300毫秒之内的就算做是点击.
                if(delta < 300){
//                    L.d("fupeng" ,"perform click event");
                    performClick();
                }
                mLastX = 0;
                mLastY = 0;
                break;
        }
        return true;
    }
}
