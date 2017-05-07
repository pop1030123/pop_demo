package com.pop.demo.view;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.pop.demo.App;

/**
 * Created by pengfu on 07/05/2017.
 */

public class RotateFanPageTransformer implements ViewPager.PageTransformer {


    private static final float ROT_MAX = 20.0f;
    private float mRot;

    @Override
    public void transformPage(View view, float position) {
        Log.e(App.TAG, view + ":position:" + position);
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setRotation(0);

        } else if (position <= 1) // a页滑动至b页 ； a页从 0.0 ~ -1 ；b页从1 ~ 0.0
        { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            mRot = (ROT_MAX * position);
            view.setPivotX(view.getMeasuredWidth() * 0.5f);
            view.setPivotY(view.getMeasuredHeight());
            view.setRotation(mRot);
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setRotation(0);
        }
    }
}
