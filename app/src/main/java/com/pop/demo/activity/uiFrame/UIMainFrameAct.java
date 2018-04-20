package com.pop.demo.activity.uiFrame;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.pop.demo.R;
import com.pop.demo.bean.BannerVO;
import com.pop.demo.util.DisplayUtil;
import com.pop.demo.util.L;
import com.pop.demo.util.ToastUtils;
import com.pop.demo.view.pageIndicator.MyTabPageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by pengfu on 20/04/2018.
 */

public class UIMainFrameAct extends FragmentActivity {

    private ConvenientBanner mConvenientBanner;

    private MyTabPageIndicator mMyTabPageIndicator;

    private ViewPager mViewPager;
    private MyPagerAdapter mMyPagerAdapter;

    private String[] categories = new String[]{"推荐", "流行", "个性", "摇滚"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ui_main_frame);

        initView();

        initData();
    }


    private void initView() {

        mConvenientBanner = findViewById(R.id.convenientBanner);

        mMyTabPageIndicator = findViewById(R.id.tpi_page_indicator);

        mViewPager = findViewById(R.id.vp_pager);
    }


    private void initData() {

        List<BannerVO> bannerVOS = new ArrayList<>();
        bannerVOS.add(new BannerVO(R.drawable.img1));
        bannerVOS.add(new BannerVO(R.drawable.img3));

        mConvenientBanner.setPages(
                new CBViewHolderCreator<BannerHolder>() {
                    @Override
                    public BannerHolder createHolder() {
                        return new BannerHolder();
                    }
                }, bannerVOS)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.banner_indicator_select, R.drawable.banner_indicator_unselect})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .startTurning(4000).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.showToast("select :" + position);
            }
        });

        mMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), Arrays.asList(categories));

        mViewPager.setAdapter(mMyPagerAdapter);

        mMyTabPageIndicator.setViewPager(mViewPager);
    }

    public class BannerHolder implements Holder<BannerVO> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerVO data) {
            DisplayUtil.displayImageDrawable(data.getItemRes(), imageView);
        }
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;
        private List<String> categoryList;

        public MyPagerAdapter(FragmentManager fm, List<String> categories) {
            super(fm);
            categoryList = categories;
            fragmentList = new ArrayList<>();
            for (String category : categories) {
                fragmentList.add(CategoryFrag.getInstance(category));
            }
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (object instanceof CategoryFrag) {
                ((CategoryFrag) object).destory();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categoryList.get(position);
        }
    }

}
