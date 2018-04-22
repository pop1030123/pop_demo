package com.pop.demo.activity.listDemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.pop.demo.util.ToastUtils;

/**
 * Created by pengfu on 22/04/2018.
 */

public class LoadMoreRecyclerView extends RecyclerView {


    private static final String TAG = LoadMoreRecyclerView.class.getSimpleName();
    /**
     * 加载更多的响应回调
     */
    private OnLoadMoreListener mOnLoadMoreListener;

    /**
     * 是否正在加载更多
     */
    private boolean isLoadingMore;

    /**
     * 加载更多的view。
     */
    private View mLastLoadMoreView;

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (SCROLL_STATE_IDLE == newState) {
                    // 滑动停止了，需要判断是否显示footer,加载更多数据
                    // 如果recycler view显示的是最后一条数据，则需要显示
                    int lastVisiblePosition = 0;
                    int itemCount = 0;
                    if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                        GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                        lastVisiblePosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                        itemCount = recyclerView.getAdapter().getItemCount();
                        if ((itemCount - 1) == lastVisiblePosition && !isLoadingMore) {
                            setLoadMore(true);
                            if (mOnLoadMoreListener != null) {
                                mOnLoadMoreListener.onLoadMore();
                            }
                        }
                    }else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
                        itemCount = recyclerView.getAdapter().getItemCount();
                        if ((itemCount - 1) == lastVisiblePosition && !isLoadingMore) {
                            // footer view 显示出来
                            mLastLoadMoreView = linearLayoutManager.findViewByPosition(lastVisiblePosition);
                            setLoadMore(true);
                            if (mOnLoadMoreListener != null) {
                                mOnLoadMoreListener.onLoadMore();
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * 加载更多完成后，让加载更多的view隐藏
     *
     * @param loadMore
     */
    public void setLoadMore(boolean loadMore) {
        if (loadMore) {
            if (mLastLoadMoreView != null) {
                mLastLoadMoreView.setVisibility(VISIBLE);
            }
            isLoadingMore = true;
        } else {
            if (mLastLoadMoreView != null) {
                mLastLoadMoreView.setVisibility(INVISIBLE);
            }
            isLoadingMore = false;
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener callback) {
        this.mOnLoadMoreListener = callback;
    }


    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
