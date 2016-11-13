package com.pop.demo.activity;

import android.app.Activity;
import android.os.Bundle;

import com.pop.demo.App;
import com.pop.demo.R;
import com.pop.demo.view.TimeTickView;

/**
 * Created by pengfu on 16/11/13.
 */

public class TimeTickAct extends Activity {


    private TimeTickView mTimeTickView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_time_tick);

        mTimeTickView = (TimeTickView)findViewById(R.id.view_time_tick) ;

        // 圆心
        int x0 = App.SCREEN_WIDTH/2 ;
        int y0 = App.SCREEN_HEIGHT/2 ;
        int[] center = new int[2] ;
        center[0] = x0 ;
        center[1] = y0 ;
        // 半径
        int radius  = 200 ;
        mTimeTickView.init(center ,radius);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTimeTickView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTimeTickView.cancel();
    }
}
