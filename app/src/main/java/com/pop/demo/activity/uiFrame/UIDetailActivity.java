package com.pop.demo.activity.uiFrame;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pop.demo.R;
import com.pop.demo.view.CustomPullZoomScrollView;

/**
 * Created by pengfu on 25/06/2018.
 */

public class UIDetailActivity extends Activity {



    private CustomPullZoomScrollView pzs_scroll_view ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ui_detail);

        pzs_scroll_view = findViewById(R.id.pzs_scroll_view);

        pzs_scroll_view.mImageView = findViewById(R.id.top_image_container);
    }
}
