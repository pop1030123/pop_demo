package com.pop.demo.activity.uiFrame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pop.demo.R;
import com.pop.demo.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengfu on 20/04/2018.
 */

public class CategoryFrag extends Fragment {


    private RecyclerView mGridView;
    private MyGridAdapter mMyGridAdapter;

    public static final String KEY_TITLE = "KEY_TITLE";

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
        View rootView = inflater.inflate(R.layout.frag_category, null, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        mTitle = bundle.getString(KEY_TITLE);
        L.d("onViewCreated." + mTitle);
    }

    private void initView(View rootView) {
        mGridView = rootView.findViewById(R.id.list);

        Bundle bundle = getArguments();
        List<String> data = new ArrayList<String>();
        if (bundle != null) {
            String title = bundle.getString(KEY_TITLE);
            for (int i = 0; i < 10; i++) {
                data.add(title + i);
            }
        }
        mMyGridAdapter = new MyGridAdapter(R.layout.item_grid_list, data);
        mGridView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        mGridView.setAdapter(mMyGridAdapter);
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
