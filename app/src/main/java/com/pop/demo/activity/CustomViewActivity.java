package com.pop.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.pop.demo.R;
import com.pop.demo.activity.customView.MovedViewActivity;

/**
 * Created by pengfu on 12/04/2018.
 */

public class CustomViewActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_custom_view);

        findViewById(R.id.tv_round_image_view).setOnClickListener(this);
        findViewById(R.id.tv_moved_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_round_image_view:
                Intent toMultiRoundImage = new Intent();
                toMultiRoundImage.setClass(this, MultiRoundImageAct.class);
                startActivity(toMultiRoundImage);
                break ;
            case R.id.tv_moved_view:
                Intent toMovedView = new Intent();
                toMovedView.setClass(this, MovedViewActivity.class);
                startActivity(toMovedView);
                break ;
        }
    }
}
