package com.pop.demo.activity.listDemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pop.demo.R;
import com.pop.demo.util.ToastUtils;
import com.pop.demo.util.UIUtils;
import com.pop.demo.view.SimpleSpacingItemDecoration;

import java.util.List;

/**
 * Created by pengfu on 27/02/2018.
 */

public class PullUpDownRecyclerViewAct extends Activity {

    private static final String TAG = PullUpDownRecyclerViewAct.class.getSimpleName();

    private LoadMoreRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ListAdapter mListAdapter;

    /**
     * 当前页码
     */
    private int mPageNo;

    /**
     * 每页条目数目
     */
    private static final int PAGE_SIZE = 15;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_pull_updown_recyclerview);

        mRecyclerView = findViewById(R.id.rv_updown_view);

        mSwipeRefreshLayout = findViewById(R.id.srl_updown_layout);
// 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 下拉加载最新数据.
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPageNo = 0;
                        mListAdapter.addNewData(MockListData.getData(mPageNo, PAGE_SIZE));
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        List<String> data = MockListData.getData(mPageNo, PAGE_SIZE);

        mListAdapter = new ListAdapter(this, data);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new SimpleSpacingItemDecoration(LinearLayoutManager.VERTICAL, UIUtils.dp2px(5)));
        mRecyclerView.setAdapter(mListAdapter);

        mRecyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPageNo++ ;
                        // 请求数据
                        mListAdapter.addMoreData(MockListData.getData(mPageNo ,PAGE_SIZE));
                        // 数据加载完成后，将loadMore的状态重置;
                        mRecyclerView.setLoadMore(false);
                    }
                },1000);
            }
        });
    }


    static class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        private static final int ITEM_TYPE_NORMAL = 1;
        private static final int ITEM_TYPE_FOOTER = 2;

        private List<String> mDataList;
        private Context mContext;

        public ListAdapter(Context context, List<String> data) {
            mContext = context;
            mDataList = data;
        }

        public void addMoreData(List<String> data) {
            if (data != null && !data.isEmpty()) {
                mDataList.addAll(data);
            } else {
                ToastUtils.showToast("没有更多数据了.");
            }
            notifyDataSetChanged();
        }

        public void addNewData(List<String> data) {
            mDataList.clear();
            if (data != null && !data.isEmpty()) {
                mDataList.addAll(data);
            } else {
                ToastUtils.showToast("没有数据.");
                // todo 显示空界面？
            }
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            if (position < mDataList.size()) {
                return ITEM_TYPE_NORMAL;
            } else {
                return ITEM_TYPE_FOOTER;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case ITEM_TYPE_FOOTER:
                    View footerView = LayoutInflater.from(mContext).inflate(R.layout.view_footer, parent, false);
                    return new MyFooterHolder(footerView);
                default:
                    // 默认的都是普通数据类型的.
                    View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_list, null, false);
                    return new MyViewHolder(rootView);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHolder) {
                ((MyViewHolder) holder).mTextView.setText(mDataList.get(position));
            } else {
                // footer view默认不显示，在recycler view滑动后判断是否显示;
                holder.itemView.setVisibility(View.INVISIBLE);
                Log.d(TAG ,"onBindViewHolder footer view:"+holder.itemView);
            }
        }

        @Override
        public int getItemCount() {
            // +1是因为给footer item.
            return mDataList.size() + 1;
        }

        static class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView mTextView;

            public MyViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.text);
            }
        }

        static class MyFooterHolder extends RecyclerView.ViewHolder {

            private TextView mTextView;

            public MyFooterHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.tv_footer_name);
            }
        }
    }


}
