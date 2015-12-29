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


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() ;
        int height = getHeight() ;
        if(mImages != null){
            int per_width =  100 ;
            for (int i =0 ;i<mImages.size() ;i++){
                canvas.drawBitmap(genBitmap(mImages.get(i)) ,i * 100, getTop() ,null);
            }
        }
    }

    private Bitmap genBitmap(Bitmap bitmapSrc){
        Bitmap oral = makeDst(100 ,100) ;
        Canvas canvas = new Canvas(oral) ;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        canvas.drawCircle(50,50,50,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapSrc, 0, 0, paint);
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
        p.setColor(Color.TRANSPARENT);
        c.drawOval(new RectF(0, 0, w, h), p);
        return bm;
    }
}
