package com.pop.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.pop.demo.activity.BezierCurveAct;
import com.pop.demo.activity.CustomViewPagerAct_;
import com.pop.demo.activity.EditTextAct;
import com.pop.demo.activity.ListDemoAct;
import com.pop.demo.activity.MultiRoundImageAct;
import com.pop.demo.activity.BarrageAct;
import com.pop.demo.activity.RecordVideoActivity;
import com.pop.demo.activity.TimeTickAct;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity implements View.OnClickListener {

    @AfterViews
    void afterViews(){
        findViewById(R.id.show_mriv).setOnClickListener(this);
        findViewById(R.id.barrage).setOnClickListener(this);
        findViewById(R.id.bezier_curve).setOnClickListener(this);
        findViewById(R.id.time_tick).setOnClickListener(this);
        findViewById(R.id.edit_text_test).setOnClickListener(this);
        findViewById(R.id.custom_view_pager).setOnClickListener(this);
        findViewById(R.id.wechat_video).setOnClickListener(this);
        findViewById(R.id.list_demo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_mriv:
                Intent toMultiRoundImage = new Intent() ;
                toMultiRoundImage.setClass(this , MultiRoundImageAct.class) ;
                startActivity(toMultiRoundImage);
                break ;
            case R.id.barrage:
                Intent toPMD = new Intent() ;
                toPMD.setClass(this , BarrageAct.class) ;
                startActivity(toPMD);
                break ;
            case R.id.bezier_curve:
                Intent toBezier = new Intent() ;
                toBezier.setClass(this , BezierCurveAct.class) ;
                startActivity(toBezier);
                break ;
            case R.id.time_tick:
                Intent toTimeTick = new Intent() ;
                toTimeTick.setClass(this , TimeTickAct.class) ;
                startActivity(toTimeTick);
                break ;
            case R.id.edit_text_test:
                Intent toEditTest = new Intent() ;
                toEditTest.setClass(this , EditTextAct.class) ;
                startActivity(toEditTest);
                break ;
            case R.id.custom_view_pager:
                CustomViewPagerAct_.intent(this).start() ;
                break ;
            case R.id.wechat_video:
                checkCameraPermission();
                break ;
            case R.id.list_demo:
                Intent toListDemo = new Intent() ;
                toListDemo.setClass(this , ListDemoAct.class) ;
                startActivity(toListDemo);
                break ;
        }
    }


    //视频录制需要的权限(相机，录音，外部存储)
    private String[] VIDEO_PERMISSION = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private List<String> NO_VIDEO_PERMISSION = new ArrayList<String>();

    /**
     * 检测摄像头权限，具备相关权限才能继续
     */
    private void checkCameraPermission() {
        NO_VIDEO_PERMISSION.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < VIDEO_PERMISSION.length; i++) {
                if (ActivityCompat.checkSelfPermission(this, VIDEO_PERMISSION[i]) != PackageManager.PERMISSION_GRANTED) {
                    NO_VIDEO_PERMISSION.add(VIDEO_PERMISSION[i]);
                }
            }
            if (NO_VIDEO_PERMISSION.size() == 0) {
                Intent intent = new Intent(this, RecordVideoActivity.class);
                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(this, NO_VIDEO_PERMISSION.toArray(new String[NO_VIDEO_PERMISSION.size()]), REQUEST_CAMERA);
            }
        } else {
            Intent intent = new Intent(this, RecordVideoActivity.class);
            startActivity(intent);
        }
    }

    private static final int REQUEST_CAMERA = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            boolean flag = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                Toast.makeText(this, "已授权", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RecordVideoActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
