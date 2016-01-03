package com.pop.demo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by pengfu on 15/12/29.
 */
public class MultiRoundImageView extends View {

    private List<Bitmap> mImages ;

    public MultiRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int mPerWidth ;
    public void setPerWidth(int width){
        mPerWidth = width ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() ;
        int height = getHeight() ;
        if(mImages != null){
            for (int i =0 ;i<mImages.size() ;i++){
                canvas.drawBitmap(genBitmap(mImages.get(i) ,mPerWidth) ,i * 100, getTop() ,null);
            }
        }
    }

    private Bitmap genBitmap(Bitmap bitmapSrc ,int width){
        Bitmap oral = makeDst(width ,width) ;
        Canvas canvas = new Canvas(oral) ;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        int radius = width /2 ;
        paint.setColor(Color.WHITE);
        canvas.drawCircle(radius,radius,radius,paint);
        int src_width = bitmapSrc.getWidth() ;
        int src_height = bitmapSrc.getHeight() ;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapSrc, (width-src_width)/2.0f, (width-src_height)/2.0f, paint);
        return oral ;
    }

    public void setImages(List<Bitmap> images){
        mImages = images ;
        invalidate();
    }

    // create a bitmap with a circle, used for the "dst" image
    static Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.WHITE);
        c.drawOval(new RectF(0, 0, w, h), p);
        return bm;
    }
}
