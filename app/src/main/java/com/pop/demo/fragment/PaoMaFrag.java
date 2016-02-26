package com.pop.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

        View rootView = inflater.inflate(R.layout.frag_pao_ma_deng ,container,false) ;
        paoMaDengView = (PaoMaDengView) rootView.findViewById(R.id.paoma_view) ;
        paoMaDengView.getHolder().addCallback(this);
        paoMaDengView.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        paoMaDengView.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        paoMaDengView.stop();
    }

    @Override
    public void onClick(View v) {
        String text = paoMaDengView.getText() ;
        Toast.makeText(getContext() ,text ,Toast.LENGTH_SHORT).show();
    }
}
