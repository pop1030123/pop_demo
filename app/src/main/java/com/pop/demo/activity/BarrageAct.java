package com.pop.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.pop.demo.App;
import com.pop.demo.R;
import com.pop.demo.view.MarqueeTextView;

/**
 * Created by pengfu on 16/2/23.
 */
public class BarrageAct extends Activity {


    private MarqueeTextView mMarqueeTextView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(App.TAG ,"onCreate");
        setContentView(R.layout.act_barrage);

        mMarqueeTextView = (MarqueeTextView)findViewById(R.id.marquee_view) ;
        mMarqueeTextView.setText("abcdefg");
    }

}
