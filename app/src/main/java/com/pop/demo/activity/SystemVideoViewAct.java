package com.pop.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.VideoView;

import com.pop.demo.R;

/**
 * Created by pengfu on 03/03/2018.
 */

public class SystemVideoViewAct extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_system_video_view);

        VideoView videoView = (VideoView) findViewById(R.id.vv_video);

        String path = getIntent().getStringExtra("path") ;

        videoView.setVideoPath(path);

        videoView.start();
    }
}
