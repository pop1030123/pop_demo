package com.pop.demo.activity.listDemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pop.demo.R;
import com.pop.demo.util.ToastUtils;
import com.pop.demo.view.SwipeRefreshListView;

import java.util.List;

/**
 * Created by pengfu on 27/02/2018.
 */

public class PullUpDownListViewAct extends Activity {


    private ListView mListView;
    private SwipeRefreshListView mSwipeRefreshLayout;

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

        setContentView(R.layout.act_pull_updown_listview);

        mListView = (ListView) findViewById(R.id.lv_updown_demo);

        mSwipeRefreshLayout = findViewById(R.id.srl_updown_demo);
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
                        mPageNo = 0 ;
                        mListAdapter.addNewData(MockListData.getData(mPageNo ,PAGE_SIZE));
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        mSwipeRefreshLayout.setOnLoadListener(new SwipeRefreshListView.OnLoadListener() {
            @Override
            public void onLoad() {
                // 上拉加载更多数据。
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPageNo++ ;
                        mListAdapter.addMoreData(MockListData.getData(mPageNo ,PAGE_SIZE));
                        mSwipeRefreshLayout.setLoading(false);
                    }
                }, 2000);
            }
        });


        List<String> data = MockListData.getData(mPageNo, PAGE_SIZE);

        mListAdapter = new ListAdapter(this, data);

        mListView.setAdapter(mListAdapter);

    }


    static class ListAdapter extends BaseAdapter {


        private List<String> mDataList;
        private Context mContext;

        public ListAdapter(Context context, List<String> data) {
            mContext = context;
            mDataList = data;
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        public void addMoreData(List<String> data){
            if(data != null && !data.isEmpty()){
                mDataList.addAll(data);
                notifyDataSetChanged();
            }else{
                ToastUtils.showToast("没有更多数据了.");
            }
        }

        public void addNewData(List<String> data){
            mDataList.clear();
            if(data != null && !data.isEmpty()){
                mDataList.addAll(data);
            }else{
                ToastUtils.showToast("没有数据.");
                // todo 显示空界面？
            }
            notifyDataSetChanged();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_list, null, false);

            ViewHolder viewHolder = (ViewHolder) rootView.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder(rootView);
                rootView.setTag(viewHolder);
            }
            viewHolder.mTextView.setText(mDataList.get(position));

            return rootView;
        }


        static class ViewHolder {

            private TextView mTextView;

            public ViewHolder(View itemView) {
                mTextView = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }


}
