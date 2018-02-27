package com.pop.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pop.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengfu on 27/02/2018.
 */

public class ListDemoAct extends Activity {


    private RecyclerView mListView;

    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_list_demo);

        mListView = (RecyclerView) findViewById(R.id.list);


        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("" + i);
        }


        mListAdapter = new ListAdapter(data);

        mListView.setLayoutManager(new GridLayoutManager(this, 4));

        mListView.setAdapter(mListAdapter);

    }


    class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {


        private List<String> mDataList;

        public ListAdapter(List<String> data) {
            mDataList = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(ListDemoAct.this).inflate(R.layout.item_list, null, false);
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


        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView mTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }


}
