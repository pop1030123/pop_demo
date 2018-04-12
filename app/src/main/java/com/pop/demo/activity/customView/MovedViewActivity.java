package com.pop.demo.activity.customView;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.pop.demo.R;
import com.pop.demo.util.L;
import com.pop.demo.util.ToastUtils;

/**
 * Created by pengfu on 12/04/2018.
 */

public class MovedViewActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_moved_view);
        findViewById(R.id.mv_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        L.d("fupeng" ,"点击事件触发.");
        ToastUtils.showSingleToast("我在：" + v.getX() + "," + v.getY());
    }
}
