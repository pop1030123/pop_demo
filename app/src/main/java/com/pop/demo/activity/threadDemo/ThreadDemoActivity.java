package com.pop.demo.activity.threadDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.pop.demo.R;

/**
 * Created by pengfu on 29/04/2018.
 */

public class ThreadDemoActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_thread_demo);

        findViewById(R.id.tv_count_down_latch).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_count_down_latch:
                Intent toCountDownLatch = new Intent(this ,CountDownLatchAct.class);
                startActivity(toCountDownLatch);
                break ;
        }
    }
}
