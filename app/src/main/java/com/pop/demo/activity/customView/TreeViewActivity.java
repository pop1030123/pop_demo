package com.pop.demo.activity.customView;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.pop.demo.R;

/**
 * Created by pengfu on 20/07/2018.
 */

public class TreeViewActivity extends Activity {

    private RecyclerView mTreeView ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_tree_view);

    }
}
