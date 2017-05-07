package com.pop.demo.activity;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pop.demo.R;
import com.pop.demo.view.RotateFanPageTransformer;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by pengfu on 07/05/2017.
 */

@EActivity(R.layout.act_custom_view_pager)
public class CustomViewPagerAct extends Activity {



    private static int[] IMAGE_ARRAY = new int[]{R.drawable.img1 ,R.drawable.img2 ,R.drawable.img3} ;
    MyAdapter mAdapter;

    @ViewById(R.id.view_pager)
    ViewPager mViewPager ;

    @AfterViews
    void afterViews(){
        mAdapter = new MyAdapter() ;
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true ,new RotateFanPageTransformer());
    }

    class MyAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView rootView = (ImageView) View.inflate(CustomViewPagerAct.this ,R.layout.pager_item ,null) ;
            rootView.setImageResource(IMAGE_ARRAY[position]);
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public int getCount() {
            return IMAGE_ARRAY.length ;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
