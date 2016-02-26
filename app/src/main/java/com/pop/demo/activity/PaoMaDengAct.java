package com.pop.demo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.pop.demo.R;
import com.pop.demo.fragment.PaoMaFrag;

/**
 * Created by pengfu on 16/2/23.
 */
public class PaoMaDengAct extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pao_ma_deng);
        PaoMaFrag frag = new PaoMaFrag() ;
        getSupportFragmentManager().beginTransaction().add(R.id.container ,frag).commit();
    }

}
