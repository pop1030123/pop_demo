package com.pop.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pop.demo.App;
import com.pop.demo.R;
import com.pop.demo.view.PaoMaDengView;

/**
 * Created by pengfu on 16/2/26.
 */
public class PaoMaFrag extends Fragment implements SurfaceHolder.Callback, View.OnClickListener {

    private PaoMaDengView paoMaDengView ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(App.TAG ,"onCreateView");
        View rootView = inflater.inflate(R.layout.frag_pao_ma_deng ,container,false) ;
        paoMaDengView = (PaoMaDengView) rootView.findViewById(R.id.paoma_view) ;
        paoMaDengView.getHolder().addCallback(this);
        paoMaDengView.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(App.TAG ,"surfaceCreated");
        paoMaDengView.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(App.TAG ,"surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(App.TAG ,"surfaceDestroyed");
    }

    @Override
    public void onDetach() {
        Log.d(App.TAG ,"onDetach");
        super.onDetach();
        paoMaDengView.stop();
    }

    @Override
    public void onClick(View v) {
        String text = paoMaDengView.getText() ;
        Toast.makeText(getContext() ,text ,Toast.LENGTH_SHORT).show();
    }

    public void updateStatus(int event){
        switch (event){
            case MotionEvent.ACTION_DOWN:
                paoMaDengView.pause();
                break ;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                paoMaDengView.resume();
                break ;
        }
    }
}
