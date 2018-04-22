package com.pop.demo.activity.listDemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pop.demo.R;
import com.pop.demo.util.ToastUtils;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by pengfu on 27/02/2018.
 */

public class GridViewDemoAct extends Activity {

    private static final String TAG = GridViewDemoAct.class.getSimpleName();

    private LoadMoreRecyclerView mGridListView;

    private ListAdapter mListAdapter;

    /**
     * 当前页码
     */
    private int mPageNo;

    /**
     * 每页条目数目
     */
    private static final int PAGE_SIZE = 15;

    private PtrClassicFrameLayout mPtrFrame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_grid_view_demo);

        mGridListView = (LoadMoreRecyclerView) findViewById(R.id.grid_list_demo);

        mListAdapter = new ListAdapter(this, MockListData.getData(mPageNo, PAGE_SIZE));

        mGridListView.setLayoutManager(new GridLayoutManager(this, 4));

        mGridListView.setAdapter(mListAdapter);

        mGridListView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPageNo++ ;
                mListAdapter.addMoreData(MockListData.getData(mPageNo ,PAGE_SIZE));
                mGridListView.setLoadMore(false);
            }
        });

        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_grid_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPageNo = 0;
                mListAdapter.addNewData(MockListData.getData(mPageNo, PAGE_SIZE));
                // 调用刷新完成.
                mPtrFrame.refreshComplete();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }


    static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {


        private List<String> mDataList;
        private Context mContext;

        public ListAdapter(Context context, List<String> data) {
            mContext = context;
            mDataList = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_demo_list, parent, false);
            return new ViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextView.setText(mDataList.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
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

        static class ViewHolder extends RecyclerView.ViewHolder {

            private TextView mTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }


}
