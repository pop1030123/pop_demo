package com.pop.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.pop.demo.R;

/**
 * Created by pengfu on 16/11/15.
 */

public class MarqueeTextView extends TextView {

    private static final String TAG = "MarqueeTextView";
    private String mText ;
    private float mTextWidth ;
    private float mTextY ;

    private Paint mTextPaint ;


    public MarqueeTextView(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a =  context.obtainStyledAttributes(attrs, R.styleable.MarqueeTextView);
        float textSize = a.getDimensionPixelSize(R.styleable.MarqueeTextView_textSize ,22) ;
        Log.d(TAG ,"textSize:"+textSize) ;
        mTextPaint = new Paint() ;
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(textSize);
    }

    public void setText(String text){
        mText = text ;
        mTextWidth = mTextPaint.measureText(text) ;
        Paint.FontMetrics fm = mTextPaint.getFontMetrics() ;
//        mTextY = fm.leading - fm.top;
        mTextY = getLayoutParams().height - fm.descent;
        mTextX = -mTextWidth ;

        Log.d(TAG ,"setText:"+fm.leading+":top:"+fm.top+":descent:"+fm.descent+":mTextY:"+mTextY+"::"+getLayoutParams().height) ;
        invalidate();
    }

    float mTextX;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.d(TAG ,"onDraw:"+getWidth()+"x"+getHeight()) ;
        if(!TextUtils.isEmpty(mText)){
            if(mTextX <= -mTextWidth ){
                mTextX = getWidth() ;
            }
            canvas.drawText(mText , mTextX--,mTextY,mTextPaint);
            invalidate();
        }
    }
}
