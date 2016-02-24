package com.pop.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pop.demo.activity.MultiRoundImageAct;
import com.pop.demo.activity.PaoMaDengAct;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.show_mriv).setOnClickListener(this);
        findViewById(R.id.pao_ma_d).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_mriv:
                Intent toMultiRoundImage = new Intent() ;
                toMultiRoundImage.setClass(this , MultiRoundImageAct.class) ;
                startActivity(toMultiRoundImage);
                break ;
            case R.id.pao_ma_d:
                Intent toPMD = new Intent() ;
                toPMD.setClass(this , PaoMaDengAct.class) ;
                startActivity(toPMD);
                break ;
        }
    }
}
