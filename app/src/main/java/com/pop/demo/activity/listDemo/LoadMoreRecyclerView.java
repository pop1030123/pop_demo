package com.pop.demo.activity.listDemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by pengfu on 22/04/2018.
 */

public class LoadMoreRecyclerView extends RecyclerView {


    private static final String TAG = LoadMoreRecyclerView.class.getSimpleName();
    /**
     * 加载更多的响应回调
     */
    private LoadMoreCallback mLoadMoreCallback;

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
                    if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
                        int itemCount = recyclerView.getAdapter().getItemCount();
                        if ((itemCount - 1) == lastVisiblePosition && !isLoadingMore) {
                            // footer view 显示出来
                            mLastLoadMoreView = linearLayoutManager.findViewByPosition(lastVisiblePosition);
                            setLoadMore(true);
                            if (mLoadMoreCallback != null) {
                                mLoadMoreCallback.onLoadMore();
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
        if (mLastLoadMoreView != null) {
            if (loadMore) {
                mLastLoadMoreView.setVisibility(VISIBLE);
                isLoadingMore = true;
            } else {
                mLastLoadMoreView.setVisibility(INVISIBLE);
                isLoadingMore = false;
            }
        }
    }

    public void setLoadMoreListener(LoadMoreCallback callback) {
        this.mLoadMoreCallback = callback;
    }


    public interface LoadMoreCallback {
        void onLoadMore();
    }
}
