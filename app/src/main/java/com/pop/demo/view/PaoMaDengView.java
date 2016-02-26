package com.pop.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.pop.demo.App;
import com.pop.demo.activity.PaoMaDengAct;

/**
 * Created by pengfu on 16/2/23.
 */
public class PaoMaDengView extends SurfaceView {


    private PaintFlagsDrawFilter drawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private String[] ads = {"有志者，事竟成。", "猴塞雷", "要什么跑马灯？？"};

    private SurfaceHolder holder;
    private Paint paint;
    private int index;
    private static boolean isRun;

    public PaoMaDengView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public String getText() {
        return ads[index];
    }

    public void start() {
        Log.d(App.TAG, "paomadeng start.");
        isRun = true;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(32);
        new Thread(new Runnable() {
            @Override
            public void run() {
                holder = getHolder();
                int startX = App.SCREEN_WIDTH;
                String text = ads[index];
                while (isRun) {
                    float textLength = paint.measureText(text);
                    if (startX <= -textLength) {
                        index++;
                        index = index % 3;
                        text = ads[index];
                        startX = App.SCREEN_WIDTH;
                    }
                    Canvas canvas = holder.lockCanvas();
                    if (canvas != null) {
                        canvas.setDrawFilter(drawFilter);
                        canvas.drawColor(Color.WHITE);
                        canvas.drawText(text, startX, 50, paint);
                        Log.d("ssss", "draw " + startX);
                        startX -= 5;
                        holder.unlockCanvasAndPost(canvas);
                    }
                    try {
                        Thread.currentThread().sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void stop() {
        Log.d(App.TAG, "paomadeng stop.");
        isRun = false;
    }
}
