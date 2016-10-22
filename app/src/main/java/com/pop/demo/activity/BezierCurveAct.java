package com.pop.demo.activity;

import android.app.Activity;
import android.os.Bundle;

import com.pop.demo.R;

/**
 * Created by pengfu on 16/10/22.
 *
 * 这里参考了别人的博客实现的一个气泡效果
 * http://blog.csdn.net/u011035622/article/details/50448567
 */

public class BezierCurveAct extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bezier_curve);
    }
}
