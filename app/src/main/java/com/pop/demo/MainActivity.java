package com.pop.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pop.demo.activity.BezierCurveAct;
import com.pop.demo.activity.MultiRoundImageAct;
import com.pop.demo.activity.BarrageAct;
import com.pop.demo.activity.TimeTickAct;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.show_mriv).setOnClickListener(this);
        findViewById(R.id.barrage).setOnClickListener(this);
        findViewById(R.id.bezier_curve).setOnClickListener(this);
        findViewById(R.id.time_tick).setOnClickListener(this);
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
        }
    }
}
