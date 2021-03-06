package com.pop.demo.activity.uiFrame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.pop.demo.util.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pengfu on 20/04/2018.
 */

public class UIMainFrameAct extends FragmentActivity {

    private ConvenientBanner mConvenientBanner;

    private TabLayout mMyTabLayout;

    private ViewPager mViewPager;
    private MyPagerAdapter mMyPagerAdapter;

    private String[] categories = new String[]{"推荐", "流行", "个性", "摇滚","轻音乐","华彩","英文","八音盒"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ui_main_frame);

        initView();

        initData();
    }


    private void initView() {

        mConvenientBanner = findViewById(R.id.convenientBanner);

        mMyTabLayout = findViewById(R.id.tpi_page_indicator);
        mMyTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

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
                // 跳转到详情页面.
                startActivity(new Intent(UIMainFrameAct.this ,UIDetailActivity.class));
            }
        });

        mMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), Arrays.asList(categories));

        mViewPager.setAdapter(mMyPagerAdapter);

        mMyTabLayout.setupWithViewPager(mViewPager);
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
