package com.pop.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pop.demo.R;
import com.pop.demo.bean.PopMedia;
import com.pop.demo.db.DatabaseHelper;
import com.pop.demo.util.L;
import com.pop.demo.util.UIUtils;
import com.pop.demo.view.SimpleSpacingItemDecoration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by pengfu on 03/03/2018.
 */

public class SystemCameraAct extends Activity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {


    public static final int RECORD_SYSTEM_VIDEO = 1;
    private static final String TAG = SystemCameraAct.class.getSimpleName();

    private RecyclerView mMediaList ;
    private ListAdapter mMediaAdapter ;
    private Context mContext ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this ;
        setContentView(R.layout.act_system_camera);

        findViewById(R.id.take_video).setOnClickListener(this);
        findViewById(R.id.take_photo).setOnClickListener(this);

        mMediaList = (RecyclerView) findViewById(R.id.rv_media_list);
        mMediaAdapter = new ListAdapter(DatabaseHelper.getInstance().getPopMediaDao().queryForAll()) ;
        mMediaList.setLayoutManager(new LinearLayoutManager(this ,LinearLayoutManager.VERTICAL ,false));
        mMediaList.setAdapter(mMediaAdapter);
        mMediaList.addItemDecoration(new SimpleSpacingItemDecoration(LinearLayoutManager.VERTICAL , UIUtils.dp2px(1)));
        mMediaAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_video:
                startRecordVideo();
                break;
            case R.id.take_photo:
                startActivity(new Intent(this ,TakeOrPickSystemAct.class));
                break ;
        }
    }

    private void startRecordVideo() {
        File file = getOutputMediaFile();
        if (file != null) {
            Uri fileUri = Uri.fromFile(file);
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10); //限制的录制时长 以秒为单位
            //intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1); //设置拍摄的质量最小是0，最大是1（建议不要设置中间值，不同手机似乎效果不同。。。）
            //intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024 * 1024);//限制视频文件大小 以字节为单位
            startActivityForResult(intent, RECORD_SYSTEM_VIDEO);
        }
    }


    private File getOutputMediaFile() {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(this, "请检查SDCard！", Toast.LENGTH_SHORT).show();
            return null;
        }

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DCIM), "PopDemo");
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        return mediaFile;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case RECORD_SYSTEM_VIDEO:
                //录制的视频：file:///storage/emulated/0/DCIM/PopDemo/VID_20180303_223329.mp4
                Log.d(TAG, "录制的视频：" + data.getData());
                String path = data.getData().toString() ;
                if(!TextUtils.isEmpty(path)){
                    String fileName = path.substring(path.lastIndexOf("/")) ;
                    PopMedia newVideo = new PopMedia(path ,fileName) ;
                    int count = DatabaseHelper.getInstance().getPopMediaDao().create(newVideo) ;
                    L.d("插入popMedia:"+count+"::"+newVideo);
                    if(count>0){
                        mMediaAdapter.setNewData(DatabaseHelper.getInstance().getPopMediaDao().queryForAll());
                    }
                }
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this, SystemVideoViewAct.class);
        intent.putExtra("path", mMediaAdapter.getItem(position).getPath());
        startActivity(intent);
    }


    class ListAdapter extends BaseQuickAdapter<PopMedia, BaseViewHolder> {


        public ListAdapter(List<PopMedia> data) {
            super(R.layout.item_pop_media ,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PopMedia item) {
            helper.setText(R.id.tv_name ,item.getPath()) ;
        }

    }

}
