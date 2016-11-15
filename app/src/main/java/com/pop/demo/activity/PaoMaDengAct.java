package com.pop.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.pop.demo.App;
import com.pop.demo.R;
import com.pop.demo.fragment.PaoMaFrag;
import com.pop.demo.view.MarqueeTextView;

/**
 * Created by pengfu on 16/2/23.
 */
public class PaoMaDengAct extends Activity {


    private MarqueeTextView mMarqueeTextView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(App.TAG ,"onCreate");
        setContentView(R.layout.act_pao_ma_deng);

        mMarqueeTextView = (MarqueeTextView)findViewById(R.id.marquee_view) ;
        mMarqueeTextView.setText("abcdefg");
    }

}
