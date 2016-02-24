package com.pop.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import com.pop.demo.R;
import com.pop.demo.view.PaoMaDengView;

/**
 * Created by pengfu on 16/2/23.
 */
public class PaoMaDengAct extends Activity implements SurfaceHolder.Callback, View.OnClickListener {

    private PaoMaDengView paomaView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pao_ma_deng);
        paomaView = (PaoMaDengView)findViewById(R.id.panma_view) ;
        paomaView.getHolder().addCallback(this);

        paomaView.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        paomaView.stop();
        super.onBackPressed();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
         paomaView.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onClick(View v) {
        String text = paomaView.getText() ;
        Toast.makeText(this ,text ,Toast.LENGTH_SHORT).show();
    }
}
