package com.pop.demo.activity.listDemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pop.demo.R;
import com.pop.demo.util.L;
import com.pop.demo.view.hive.HiveLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by pengfu on 27/02/2018.
 */

public class HiveViewDemoAct extends Activity implements View.OnClickListener {


    private RecyclerView mListView;

    public static final int[] resIds = new int[]{
            R.drawable.img1
            , R.drawable.img2
            , R.drawable.img3
    };


    private HiveLayoutManager layoutManager;

    private HiveAdapter adapter;
    ;

    private Button mAddHive, mRemoveHive;


    int index = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_hive_view_demo);
        BitmapCache.INSTANCE.init(this, 10);

        mAddHive = findViewById(R.id.btn_add_hive);
        mAddHive.setOnClickListener(this);
        mRemoveHive = findViewById(R.id.btn_remove_hive);
        mRemoveHive.setOnClickListener(this);

        layoutManager = new HiveLayoutManager(HiveLayoutManager.HORIZONTAL);

        mListView = (RecyclerView) findViewById(R.id.list);

        adapter = new HiveAdapter();

        mListView.setAdapter(adapter);
        mListView.setLayoutManager(layoutManager);

        // 初始化
        for (int i = 0; i < index; i++) {
            adapter.addData(resIds[i % resIds.length]);
        }
//        layoutManager.setGravity(HiveLayoutManager.CENTER);
        layoutManager.setGravity(HiveLayoutManager.ALIGN_LEFT);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_RIGHT);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_TOP);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_BOTTOM);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_LEFT | HiveLayoutManager.ALIGN_TOP);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_LEFT | HiveLayoutManager.ALIGN_BOTTOM);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_RIGHT | HiveLayoutManager.ALIGN_TOP);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_RIGHT | HiveLayoutManager.ALIGN_BOTTOM);
        layoutManager.setPadding(300, 400, 500, 600);

    }

    @Override
    public void onClick(View v) {

        int r = getRandomPosition();
        switch (v.getId()) {
            case R.id.btn_add_hive:
                L.d("onClick: r" + r);
                adapter.addData(resIds[index % resIds.length], r);
                adapter.notifyItemInserted(r);
                index++;
                break;
            case R.id.btn_remove_hive:
                if (adapter.getItemCount() != 0) {
                    L.d("onClick: r" + r);
                    adapter.remove(r);
                    adapter.notifyItemRemoved(r);
                    index--;
                }
                break;
        }
    }


    private int getRandomPosition() {
        int count = adapter.getItemCount();
        if (count > 0) {
            return new Random().nextInt(count);
        } else {
            return 0;
        }
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
            View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_list, null, false);
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


        static class ViewHolder extends RecyclerView.ViewHolder {

            private TextView mTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }


}
