package com.pop.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pop.demo.R;
import com.pop.demo.util.FileUtils;
import com.pop.demo.util.L;

import java.io.File;

/**
 * Created by pengfu on 02/04/2018.
 */

public class TakeOrPickSystemAct extends Activity implements View.OnClickListener {


    TextView mResultView;

    private static final int REQ_CODE_IMPORT_PHOTO = 1111;
    private static final int REQ_CODE_TAKE_PHOTO = 1112;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_take_or_pick_system);

        findViewById(R.id.tv_take).setOnClickListener(this);
        findViewById(R.id.tv_import).setOnClickListener(this);
        mResultView = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_take:
                break;
            case R.id.tv_import:
                String path = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/" + "popdemo";

                L.d("popdemo", "导入路径：" + path);
                File f = new File(path);
                if (!f.exists()) {
                    FileUtils.createDir(path);
                }

                File photoFile = new File(f, System.currentTimeMillis() + ".jpg");
                Log.d("popdemo", "is directory");
                Uri uri = Uri.fromFile(photoFile);
                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                intent.setType("image/*");
                startActivityForResult(intent, REQ_CODE_IMPORT_PHOTO);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_CODE_IMPORT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri imageUri= data.getData();
                    Log.d("popdemo", imageUri.getPath());
                    mResultView.setText(imageUri.getPath());
                }
                break ;
        }
    }
}
