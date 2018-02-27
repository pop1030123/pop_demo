package com.pop.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pop.demo.activity.BezierCurveAct;
import com.pop.demo.activity.CustomViewPagerAct;
import com.pop.demo.activity.CustomViewPagerAct_;
import com.pop.demo.activity.EditTextAct;
import com.pop.demo.activity.ListDemoAct;
import com.pop.demo.activity.MultiRoundImageAct;
import com.pop.demo.activity.BarrageAct;
import com.pop.demo.activity.TimeTickAct;
import com.pop.demo.wechat.fyy.shortvideo.FirstActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity implements View.OnClickListener {

    @AfterViews
    void afterViews(){
        findViewById(R.id.show_mriv).setOnClickListener(this);
        findViewById(R.id.barrage).setOnClickListener(this);
        findViewById(R.id.bezier_curve).setOnClickListener(this);
        findViewById(R.id.time_tick).setOnClickListener(this);
        findViewById(R.id.edit_text_test).setOnClickListener(this);
        findViewById(R.id.custom_view_pager).setOnClickListener(this);
        findViewById(R.id.wechat_video).setOnClickListener(this);
        findViewById(R.id.list_demo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_mriv:
                Intent toMultiRoundImage = new Intent() ;
                toMultiRoundImage.setClass(this , MultiRoundImageAct.class) ;
                startActivity(toMultiRoundImage);
                break ;
            case R.id.barrage:
                Intent toPMD = new Intent() ;
                toPMD.setClass(this , BarrageAct.class) ;
                startActivity(toPMD);
                break ;
            case R.id.bezier_curve:
                Intent toBezier = new Intent() ;
                toBezier.setClass(this , BezierCurveAct.class) ;
                startActivity(toBezier);
                break ;
            case R.id.time_tick:
                Intent toTimeTick = new Intent() ;
                toTimeTick.setClass(this , TimeTickAct.class) ;
                startActivity(toTimeTick);
                break ;
            case R.id.edit_text_test:
                Intent toEditTest = new Intent() ;
                toEditTest.setClass(this , EditTextAct.class) ;
                startActivity(toEditTest);
                break ;
            case R.id.custom_view_pager:
                CustomViewPagerAct_.intent(this).start() ;
                break ;
            case R.id.wechat_video:
                Intent toWechatTest = new Intent() ;
                toWechatTest.setClass(this , FirstActivity.class) ;
                startActivity(toWechatTest);
                break ;
            case R.id.list_demo:
                Intent toListDemo = new Intent() ;
                toListDemo.setClass(this , ListDemoAct.class) ;
                startActivity(toListDemo);
                break ;
        }
    }
}
