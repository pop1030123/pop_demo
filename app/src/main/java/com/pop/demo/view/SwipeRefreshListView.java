package com.pop.demo.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import com.pop.demo.R;

/**
 * 自定义View继承SwipeRefreshLayout，添加上拉加载更多的布局属性
 */

public class SwipeRefreshListView extends SwipeRefreshLayout {

    private static final String TAG = "SwipeRefreshView";

    private final int mScaledTouchSlop;
    private final View mFooterView;
    private ListView mListView;
    private OnLoadListener mOnLoadListener;
    /**
     * 正在加载状态
     */
    private boolean isLoading;

    public SwipeRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 填充底部加载布局
        mFooterView = LayoutInflater.from(context).inflate(R.layout.view_footer, null,false);
        // 表示控件移动的最小距离，手移动的距离大于这个距离才能拖动控件
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 获取ListView,设置ListView的布局位置
        if (mListView == null) {
            // 判断容器有多少个孩子
            if (getChildCount() > 0) {
                // 判断第一个孩子是不是ListView（所以这个SwipeRefreshView容器只能包裹ListView）
                if (getChildAt(0) instanceof ListView) {
                    // 创建ListView对象
                    mListView = (ListView) getChildAt(0);
                    //添加Footer view.
                    mListView.addFooterView(mFooterView);
                    mListView.setFooterDividersEnabled(false);
                    //默认不显示footer view.
                    mFooterView.setVisibility(INVISIBLE);
                    // 设置ListView的滑动监听
                    setListViewOnScroll();
                }
            }
        }
    }

    /**
     * 在分发事件的时候处理子控件的触摸事件
     *
     * @param ev
     * @return
     */
    private float mDownY, mUpY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 移动的起点
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 移动的终点
                mUpY = ev.getY();
                // 移动过程中判断时候能下拉加载更多
                if (canLoadMore()) {
                    // 加载数据
                    loadData();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断是否满足加载更多条件
     *
     * @return
     */
    private boolean canLoadMore() {
        // 1. 是上拉状态
        boolean condition1 = (mDownY - mUpY) >= mScaledTouchSlop;
        // 2. 当前页面可见的item是最后一个条目
        boolean condition2 = false;
        // 3. 正在加载状态
        boolean condition3 = !isLoading;

        if (condition1) {
//            Log.d(TAG ,"是上拉状态:" + mScaledTouchSlop);
            if (mListView != null && mListView.getAdapter() != null) {
                condition2 = mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
            }
            if (condition2) {
                Log.d(TAG, "是最后一个条目");
                if (condition3) {
                    Log.d(TAG, "不是正在加载状态");
                } else {
                    Log.d(TAG, "是正在加载状态");
                }
            }
        }

        return condition1 && condition2 && condition3;
    }

    /**
     * 处理加载数据的逻辑
     */
    private void loadData() {
        if (mOnLoadListener != null) {
            // 设置加载状态，让布局显示出来
            setLoading(true);
            mOnLoadListener.onLoad();
        }

    }

    /**
     * 设置加载状态，是否加载传入boolean值进行判断
     *
     * @param loading
     */
    public void setLoading(boolean loading) {
        // 修改当前的状态
        isLoading = loading;
        if (isLoading) {
            // 显示布局
            mFooterView.setVisibility(VISIBLE);
//            mListView.post(new Runnable() {
//                @Override
//                public void run() {
//                    int count = mListView.getCount();
//                    int footerPosition = mListView.getPositionForView(mFooterView);
//                    if (footerPosition == -1) {
//                        // 让footer显示到界面上.
//                        mListView.smoothScrollToPosition(count - 1);
//                    }
//                }
//            });
        } else {
            // 隐藏布局
            mFooterView.setVisibility(INVISIBLE);
//            mListView.removeFooterView(mFooterView);
            // 重置滑动的坐标
            mDownY = 0;
            mUpY = 0;
        }
    }


    /**
     * 设置ListView的滑动监听
     */
    private void setListViewOnScroll() {
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (SCROLL_STATE_IDLE == scrollState) {
                    // 滑动结束判断时候能下拉加载更多
                    if (canLoadMore()) {
                        // 加载数据
                        loadData();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    /**
     * 上拉加载的接口回调
     */

    public interface OnLoadListener {
        void onLoad();
    }

    public void setOnLoadListener(OnLoadListener listener) {
        this.mOnLoadListener = listener;
    }
}