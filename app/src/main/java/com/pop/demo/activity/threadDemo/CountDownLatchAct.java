package com.pop.demo.activity.threadDemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pop.demo.R;

import java.util.concurrent.CountDownLatch;

/**
 * Created by pengfu on 29/04/2018.
 */

public class CountDownLatchAct extends Activity implements View.OnClickListener {

    private static final String TAG = CountDownLatchAct.class.getSimpleName();

    private SeekBar mSeekBarA, mSeekBarB, mSeekBarC, mSeekBarD;

    private TextView mFlagA, mFlagB, mFlagC, mFlagD;

    private CountDownLatch mCountDownLatch;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_count_down_latch);

        // 3个计数，表示B ,C ,D线程；
        mCountDownLatch = new CountDownLatch(3);

        mSeekBarA = findViewById(R.id.sb_count_a);
        mSeekBarB = findViewById(R.id.sb_count_b);
        mSeekBarC = findViewById(R.id.sb_count_c);
        mSeekBarD = findViewById(R.id.sb_count_d);

        // 初始化状态;
        mSeekBarA.setMax(100);
        mSeekBarA.setProgress(0);
        mSeekBarB.setMax(100);
        mSeekBarB.setProgress(0);
        mSeekBarC.setMax(100);
        mSeekBarC.setProgress(0);
        mSeekBarD.setMax(100);
        mSeekBarD.setProgress(0);

        mFlagA = findViewById(R.id.tv_flag_a);
        mFlagB = findViewById(R.id.tv_flag_b);
        mFlagC = findViewById(R.id.tv_flag_c);
        mFlagD = findViewById(R.id.tv_flag_d);

        mFlagA.setText("3");
        mFlagB.setText("");
        mFlagC.setText("");
        mFlagD.setText("");

        findViewById(R.id.tv_start_count_down).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_count_down:
                // 启动A线程；
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 开始A的进度，执行到30的时候，再执行B,C,D等子任务.
                        int progress = 0;
                        while (progress < 30) {
                            progress++;
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                Log.e(TAG ,"休眠被中断:"+e);
                            }
                            countDownHandler.sendMessage(countDownHandler.obtainMessage(MSG_PROGRESS_A, progress));
                        }
                        // 30之后就等待B,C,D 去执行。
                        try {
                            countDownHandler.sendEmptyMessage(MSG_START_CHILD_WORK);
                            mCountDownLatch.await();
                        } catch (InterruptedException e) {
                            Log.d(TAG, "任务被中断了：" + e);
                        }
                        // 继续执行A任务;
                        while (progress < 100) {
                            progress++;
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                Log.e(TAG ,"休眠被中断:"+e);
                            }
                            countDownHandler.sendMessage(countDownHandler.obtainMessage(MSG_PROGRESS_A, progress));
                        }
                    }
                }).start();
                break;
        }
    }

    /**
     * 开始B,C,D等子任务;
     */
    private static final int MSG_START_CHILD_WORK = 1;
    private static final int MSG_PROGRESS_A = 2;
    private static final int MSG_PROGRESS_B = 3;
    private static final int MSG_PROGRESS_C = 4;
    private static final int MSG_PROGRESS_D = 5;
    private static final int MSG_COUNT_DOWN = 6;


    private Handler countDownHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_CHILD_WORK:
                    startChildWork(1);
                    startChildWork(2);
                    startChildWork(3);
                    break;
                case MSG_PROGRESS_A:
                    int progressA = Integer.parseInt(msg.obj.toString());
                    mSeekBarA.setProgress(progressA);
                    if (progressA == 100) {
                        mFlagA.setText("V");
                    }
                    break;
                case MSG_PROGRESS_B:
                    int progressB = Integer.parseInt(msg.obj.toString());
                    mSeekBarB.setProgress(progressB);
                    if (progressB == 100) {
                        mFlagB.setText("V");
                    }
                    break;
                case MSG_PROGRESS_C:
                    int progressC = Integer.parseInt(msg.obj.toString());
                    mSeekBarC.setProgress(progressC);
                    if (progressC == 100) {
                        mFlagC.setText("V");
                    }
                    break;
                case MSG_PROGRESS_D:
                    int progressD = Integer.parseInt(msg.obj.toString());
                    mSeekBarD.setProgress(progressD);
                    if(progressD == 100){
                        mFlagD.setText("V");
                    }
                    break ;
                case MSG_COUNT_DOWN:
                    mFlagA.setText(String.valueOf(mCountDownLatch.getCount()));
                    break ;
            }

        }
    };

    private void startChildWork(final int index) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (progress < 100) {
                    progress++;
                    long sleepMillis = 0 ;
                    try {
                        switch (index){
                            case 1:
                                sleepMillis = 100 ;
                                break ;
                            case 2:
                                sleepMillis = 80 ;
                                break;
                            case 3:
                                sleepMillis = 50 ;
                                break ;
                        }
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e) {
                        Log.e(TAG ,"休眠被中断:"+e);
                    }
                    switch (index){
                        case 1:
                            countDownHandler.sendMessage(countDownHandler.obtainMessage(MSG_PROGRESS_B, progress));
                            break ;
                        case 2:
                            countDownHandler.sendMessage(countDownHandler.obtainMessage(MSG_PROGRESS_C, progress));
                            break ;
                        case 3:
                            countDownHandler.sendMessage(countDownHandler.obtainMessage(MSG_PROGRESS_D, progress));
                            break ;
                    }
                }
                switch (index){
                    case 1:
                    case 2:
                    case 3:
                        // 倒记数。
                        mCountDownLatch.countDown();
                        countDownHandler.sendEmptyMessage(MSG_COUNT_DOWN);
                        break ;
                }
            }
        }).start();
    }
}
