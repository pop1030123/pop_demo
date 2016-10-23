package com.pop.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.pop.demo.R;

/**
 * Created by pengfu on 16/10/22.
 *
 * 这里参考了别人的博客实现的一个气泡效果
 * http://blog.csdn.net/u011035622/article/details/50448567
 */

public class BezierCurveAct extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bezier_curve);

        findViewById(R.id.btn_bezier).setOnClickListener(this);
        findViewById(R.id.btn_triangle).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bezier:
                findViewById(R.id.view_bezier).setVisibility(View.VISIBLE);
                findViewById(R.id.view_triangle).setVisibility(View.GONE);
                break ;
            case R.id.btn_triangle:
                findViewById(R.id.view_bezier).setVisibility(View.GONE);
                findViewById(R.id.view_triangle).setVisibility(View.VISIBLE);
                break ;
        }
    }
}
