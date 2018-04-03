package com.pop.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pop.demo.R;
import com.pop.demo.bean.PopMedia;
import com.pop.demo.util.FileUtils;
import com.pop.demo.util.L;
import com.pop.demo.util.TakePictureManager;
import com.pop.demo.util.UIUtils;
import com.pop.demo.view.SimpleGridSpacingItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengfu on 02/04/2018.
 */

public class TakeOrPickSystemAct extends Activity implements View.OnClickListener, TakePictureManager.takePictureCallBackListener {


    private TakePictureManager mTakePictureManager ;

    private RecyclerView rv_photos ;

    private GridAdapter mGridAdapter ;

    private List<PopMedia> mediaList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_take_or_pick_system);

        mTakePictureManager = new TakePictureManager(this) ;

        findViewById(R.id.tv_take).setOnClickListener(this);
        findViewById(R.id.tv_import).setOnClickListener(this);

        mGridAdapter = new GridAdapter(mediaList) ;

        rv_photos = findViewById(R.id.rv_photos) ;

        rv_photos.setLayoutManager(new GridLayoutManager(this ,4));
        rv_photos.addItemDecoration(new SimpleGridSpacingItemDecoration(UIUtils.dp2px(5)));
        rv_photos.setAdapter(mGridAdapter);

        mTakePictureManager.setTakePictureCallBackListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_take:
                mTakePictureManager.startTakeWayByCamera();
                break;
            case R.id.tv_import:
                mTakePictureManager.startTakeWayByAlbum();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTakePictureManager.attachToActivityForResult(requestCode ,resultCode ,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mTakePictureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void successful(boolean isTailor, File outFile, Uri fileUri) {
        L.d("successful:outFile:"+outFile+":fileUri:"+fileUri);
        PopMedia popMedia = new PopMedia() ;
        popMedia.setFileName(outFile.getName());
        popMedia.setPath(outFile.getPath());
        mGridAdapter.addData(popMedia);
    }

    @Override
    public void failed(int errorCode, List<String> deniedPermissions) {

    }


    class GridAdapter extends BaseQuickAdapter<PopMedia, BaseViewHolder> {


        public GridAdapter(List<PopMedia> data) {
            super(R.layout.item_grid_media ,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PopMedia item) {

            ImageLoader.getInstance().displayImage("file://"+item.getPath() ,(ImageView) helper.getView(R.id.iv_photo));
        }

    }
}
