package com.pop.demo.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.pop.demo.App;
import com.pop.demo.R;
import com.pop.demo.view.MarqueeTextView;

/**
 * Created by pengfu on 16/2/23.
 */
public class BarrageAct extends Activity {


    private TextView mFlipTextView ;
    private MarqueeTextView mMarqueeTextView ;

    private String[] texts = new String[]{
            "活着就是为了改变世界；" ,
            "生命的意义就是让生命有意义。" ,
            "意义是“个人行为所带来的正向价值回馈”" ,
            "生命只是一种自然现象，没有意义"} ;

    private Handler mHandler ;
    private int showIndex ;
    private static int sAnimDuration = 500;

    private ObjectAnimator mHideAnim ,mShowAnim ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(App.TAG ,"onCreate");
        setContentView(R.layout.act_barrage);

        mMarqueeTextView = (MarqueeTextView)findViewById(R.id.marquee_view) ;
        mMarqueeTextView.setText("abcdefg");
        mFlipTextView = (TextView) findViewById(R.id.flip_text) ;
        mFlipTextView.setText(texts[showIndex]);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // 显示下一个
                showIndex++ ;
                showIndex = showIndex%texts.length ;
                mHideAnim.start();
            }
        } ;
        waitForNextMessage();

        mHideAnim = ObjectAnimator.ofFloat(mFlipTextView ,"rotationX" ,0,90).setDuration(sAnimDuration);
        mHideAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                showSecond();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mShowAnim = ObjectAnimator.ofFloat(mFlipTextView ,"rotationX" ,90,0).setDuration(sAnimDuration);
        mShowAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                waitForNextMessage() ;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void showSecond(){
        mFlipTextView.setText(texts[showIndex]);
        mShowAnim.start();
    }

    private void waitForNextMessage(){
        mHandler.sendEmptyMessageDelayed(1 ,3000) ;
    }
}
