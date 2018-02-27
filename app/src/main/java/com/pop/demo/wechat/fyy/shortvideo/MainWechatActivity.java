package com.pop.demo.wechat.fyy.shortvideo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

import com.pop.demo.R;

public class MainWechatActivity extends Activity {
    private MovieRecorderView mRecorderView;
    private Button mShootBtn;
    private boolean isFinish = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_main);
        mRecorderView = (MovieRecorderView) findViewById(R.id.movieRecorderView);
        mShootBtn = (Button) findViewById(R.id.shoot_button);

        mShootBtn.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mRecorderView.record(new MovieRecorderView.OnRecordFinishListener() {

                        @Override
                        public void onRecordFinish() {
                            handler.sendEmptyMessage(1);
                        }
                    });
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mRecorderView.getTimeCount() > 1)
                        handler.sendEmptyMessage(1);
                    else {
                        if (mRecorderView.getmRecordFile() != null)
                            mRecorderView.getmRecordFile().delete();
                        mRecorderView.stop();
                        Toast.makeText(MainWechatActivity.this, "视频录制时间太短", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        isFinish = true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isFinish = false;
        mRecorderView.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            finishActivity();
        }
    };

    private void finishActivity() {
        if (isFinish) {
            mRecorderView.stop();
            // 返回到播放页面
            Intent intent = new Intent();
            Log.d("TAG",mRecorderView.getmRecordFile().getAbsolutePath());
            intent.putExtra("path", mRecorderView.getmRecordFile().getAbsolutePath());
            setResult(RESULT_OK,intent);
        }
        // isFinish = false;
        finish();
    }

    /**
     * 录制完成回调
     *
     * @author liuyinjun
     *
     * @date 2015-2-9
     */
    public interface OnShootCompletionListener {
        public void OnShootSuccess(String path, int second);
        public void OnShootFailure();
    }

  /*  //  private Button btnVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       *//* btnVideo = (Button) findViewById(R.id.btn_video);

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                String url = createFilePath("QDbzxt_video");
                File file = new File(url,"mov_"+getCurrentDate()+".mp4");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file)); // 设置存储路径
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // 设置保存质量
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10); // 设置限制时长
                //intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT,100000L); // 设置限制大小
                startActivityForResult(intent, 0x123);

            }
        });*//*
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0x123:
                if(resultCode == RESULT_OK) {
                    Toast.makeText(this, "Video saved to:\n" +
                            data.getData(), Toast.LENGTH_LONG).show();
                    // 获取第一帧图片

                }
                break;
        }
    }

    *//**
     * 创建文件夹路径
     * @param folderName　文件夹名称
     * @return  文件夹路径
     *//*
    public static String createFilePath(String folderName) {
        File file = new File(android.os.Environment.getExternalStorageDirectory()+"/"+folderName);
        file.mkdir();
        String path = file.getPath();
        return path;
    }

    *//**
     * 获取当前时间
     * @return 日期的字符串信息
     *//*
    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String strDate = dateFormat.format(new Date());
        return strDate;
    }*/

}
