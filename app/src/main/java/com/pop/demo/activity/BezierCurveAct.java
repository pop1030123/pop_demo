package com.pop.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.pop.demo.R;
import com.pop.demo.view.BubbleView;
import com.pop.demo.view.ElasticRoundView;

/**
 * Created by pengfu on 16/10/22.
 *
 * 这里参考了别人的博客实现的一个气泡效果
 * http://blog.csdn.net/u011035622/article/details/50448567
 */

public class BezierCurveAct extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private BubbleView mBubbleView ;
    private ElasticRoundView mElasticRoundView ;
    private CheckBox mCheckBox ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bezier_curve);

        findViewById(R.id.btn_bezier).setOnClickListener(this);
        findViewById(R.id.btn_triangle).setOnClickListener(this);

        mElasticRoundView = (ElasticRoundView)findViewById(R.id.view_bezier) ;
        mBubbleView = (BubbleView)findViewById(R.id.view_triangle) ;

        mCheckBox = (CheckBox)findViewById(R.id.checkbox) ;
        mCheckBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bezier:
                mElasticRoundView.setVisibility(View.VISIBLE);
                mBubbleView.setVisibility(View.GONE);
                break ;
            case R.id.btn_triangle:
                mElasticRoundView.setVisibility(View.GONE);
                mBubbleView.setVisibility(View.VISIBLE);
                break ;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mElasticRoundView.isFill(isChecked);
        mBubbleView.isFill(isChecked);
    }
}
