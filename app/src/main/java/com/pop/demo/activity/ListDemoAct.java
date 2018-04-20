package com.pop.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.pop.demo.R;
import com.pop.demo.activity.listDemo.channelManage.ChannelManageActivity;
import com.pop.demo.activity.listDemo.GridViewDemoAct;
import com.pop.demo.activity.listDemo.hive.HiveViewDemoAct;

/**
 * Created by pengfu on 27/02/2018.
 */

public class ListDemoAct extends Activity implements View.OnClickListener {


    private TextView tv_grid_view_demo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_list_demo);

        tv_grid_view_demo = (TextView) findViewById(R.id.tv_grid_view_demo);
        tv_grid_view_demo.setOnClickListener(this);

        findViewById(R.id.tv_hive_view_demo).setOnClickListener(this);
        findViewById(R.id.tv_channel_mgr).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_grid_view_demo:
                startActivity(new Intent(this , GridViewDemoAct.class));
                break ;
            case R.id.tv_hive_view_demo:
                startActivity(new Intent(this , HiveViewDemoAct.class));
                break ;
            case R.id.tv_channel_mgr:
                startActivity(new Intent(this , ChannelManageActivity.class));
                break ;
        }
    }
}
