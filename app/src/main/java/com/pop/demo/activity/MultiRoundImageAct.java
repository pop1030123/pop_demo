package com.pop.demo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.pop.demo.R;
import com.pop.demo.util.BitmapUtil;
import com.pop.demo.view.MultiRoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengfu on 15/12/29.
 */
public class MultiRoundImageAct extends Activity {

    private MultiRoundImageView mMultiView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_multi_round_image);

        mMultiView = (MultiRoundImageView)findViewById(R.id.multi_round_view) ;
        List<Bitmap> imageList = new ArrayList<>() ;
        imageList.add(BitmapUtil.getFitBitmapFromResource(getResources() ,R.drawable.img1 ,100 ,100)) ;
        imageList.add(BitmapUtil.getFitBitmapFromResource(getResources() ,R.drawable.img2 ,100 ,100)) ;
        imageList.add(BitmapUtil.getFitBitmapFromResource(getResources() ,R.drawable.img3 ,100 ,100)) ;
        mMultiView.setImages(imageList);
    }



}
