package com.pop.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pop.demo.App;
import com.pop.demo.R;
import com.pop.demo.bean.PopMedia;
import com.pop.demo.util.FileUtils;
import com.pop.demo.util.L;
import com.pop.demo.util.TakePictureManager;
import com.pop.demo.util.ToastUtils;
import com.pop.demo.util.UIUtils;
import com.pop.demo.view.SimpleGridSpacingItemDecoration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pengfu on 02/04/2018.
 */

public class TakeOrPickSystemAct extends Activity implements View.OnClickListener, TakePictureManager.TakePictureCallbackListener {


    private TakePictureManager mTakePictureManager;

    private RecyclerView rv_photos;

    private GridAdapter mGridAdapter;

    private List<PopMedia> mediaList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_take_or_pick_system);

        mTakePictureManager = new TakePictureManager(this);

        findViewById(R.id.tv_take).setOnClickListener(this);
        findViewById(R.id.tv_import).setOnClickListener(this);

        mGridAdapter = new GridAdapter(mediaList);
        mGridAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete:
                        PopMedia popMedia = mediaList.get(position);
                        try {
                            boolean deleteResult = FileUtils.deleteFile(new File(popMedia.getPath()));
                            L.d("文件delete:" + deleteResult + "::" + popMedia.getPath());
                        } catch (IOException e) {
                            L.e("删除文件异常：" + e);
                        }
                        mGridAdapter.remove(position);
                        break;
                }
            }
        });

        rv_photos = findViewById(R.id.rv_photos);

        rv_photos.setLayoutManager(new GridLayoutManager(this, 4));
        rv_photos.addItemDecoration(new SimpleGridSpacingItemDecoration(UIUtils.dp2px(5)));
        rv_photos.setAdapter(mGridAdapter);

        mTakePictureManager.setTakePictureCallbackListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_take:
                mTakePictureManager.startTakeWayByCamera(getOutputMediaFile());
                break;
            case R.id.tv_import:
                mTakePictureManager.startTakeWayByAlbum(getOutputMediaFile());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTakePictureManager.attachToActivityForResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mTakePictureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void successful(boolean isTailor, File outFile, Uri fileUri) {
        L.d("outFile:" + outFile);
        L.d("fileUri:" + fileUri);
        PopMedia popMedia = new PopMedia();
        popMedia.setFileName(outFile.getName());
        popMedia.setPath(outFile.getPath());
        mGridAdapter.addData(popMedia);
    }

    @Override
    public void failed(int errorCode, List<String> deniedPermissions) {
        switch (errorCode){
            case TakePictureManager.CODE_ERR_BITMAP:
                ToastUtils.showToast("获取图片失败。");
                break ;
            case TakePictureManager.CODE_ERR_PERMISSION:
                ToastUtils.showToast("获取权限失败。");
                break ;
        }
    }


    class GridAdapter extends BaseQuickAdapter<PopMedia, BaseViewHolder> {


        public GridAdapter(List<PopMedia> data) {
            super(R.layout.item_grid_media, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PopMedia item) {
            ImageLoader.getInstance().displayImage("file://" + item.getPath(), (ImageView) helper.getView(R.id.iv_photo));
            helper.addOnClickListener(R.id.iv_delete);
        }

    }


    private File getOutputMediaFile() {
        if (!FileUtils.isSdCardAvailable()) {
            ToastUtils.showToast("请检查SDCard！");
            return null;
        }

        String appRootPath = FileUtils.createRootPath(App.getInstance());

        File mediaStorageDir = new File(appRootPath, "PopDemo");

        FileUtils.createDir(mediaStorageDir);
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "PIC_" + timeStamp + ".jpg");

        return mediaFile;
    }

}
