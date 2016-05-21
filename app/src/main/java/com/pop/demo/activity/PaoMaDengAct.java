package com.pop.demo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.pop.demo.App;
import com.pop.demo.R;
import com.pop.demo.fragment.PaoMaFrag;

/**
 * Created by pengfu on 16/2/23.
 */
public class PaoMaDengAct extends FragmentActivity implements View.OnTouchListener {

    private PaoMaFrag frag ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(App.TAG ,"onCreate");
        setContentView(R.layout.act_pao_ma_deng);

        ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
        sv.setOnTouchListener(this);

        frag = new PaoMaFrag() ;
        getSupportFragmentManager().beginTransaction().add(R.id.container ,frag).commit();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(App.TAG ,"onTouch:"+event.getAction());
        frag.updateStatus(event.getAction());
        return false;
    }
}
