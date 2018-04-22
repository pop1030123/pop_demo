package com.pop.demo.activity.uiFrame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pop.demo.R;
import com.pop.demo.activity.listDemo.LoadMoreRecyclerView;
import com.pop.demo.activity.listDemo.MockListData;
import com.pop.demo.util.L;
import com.pop.demo.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengfu on 20/04/2018.
 */

public class CategoryFrag extends Fragment {

    private static final String TAG = CategoryFrag.class.getSimpleName() ;

    private SwipeRefreshLayout mSwipeRefreshLayout ;
    private LoadMoreRecyclerView mGridView;
    private MyGridAdapter mMyGridAdapter;

    public static final String KEY_TITLE = "KEY_TITLE";


    /**
     * 当前页码
     */
    private int mPageNo;

    /**
     * 每页条目数目
     */
    private static final int PAGE_SIZE = 15;

    private String mTitle;

    public static CategoryFrag getInstance(String title) {
        CategoryFrag categoryFrag = new CategoryFrag();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        categoryFrag.setArguments(bundle);
        return categoryFrag;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.d("onCreateView");
        Bundle bundle = getArguments();
        mTitle = bundle.getString(KEY_TITLE);
        View rootView = inflater.inflate(R.layout.frag_category, null, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        L.d("onViewCreated." + mTitle);
    }

    private void initView(View rootView) {
        mSwipeRefreshLayout = rootView.findViewById(R.id.srl_frame_layout);

        mGridView = rootView.findViewById(R.id.rv_frame_list);

        mMyGridAdapter = new MyGridAdapter(R.layout.item_grid_list, MockListData.getData(mPageNo ,PAGE_SIZE ,mTitle));
        mGridView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        mGridView.setAdapter(mMyGridAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPageNo = 0 ;
                        mMyGridAdapter.setNewData(MockListData.getData(mPageNo ,PAGE_SIZE ,mTitle));
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                } ,1000);
            }
        });

        mGridView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPageNo++ ;
                Log.d(TAG,"请求更多"+mTitle+"第"+mPageNo+"页面");
                List<String> moreData = MockListData.getData(mPageNo ,PAGE_SIZE ,mTitle) ;
                if(moreData.isEmpty()){
                    ToastUtils.showSingleToast("没有更多"+mTitle+"的数据了.");
                    mPageNo--;
                }else{
                    mMyGridAdapter.addData(moreData);
                }
                mGridView.setLoadMore(false);
            }
        });
    }


    static class MyGridAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


        public MyGridAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_title, item);
            helper.setText(R.id.tv_sub_title, "这是" + item + "的子标题");
        }

    }


    public void destory() {
        L.d("destory -->" + mTitle);
    }

}
