package com.pop.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pop.demo.R;
import com.pop.demo.view.floatingeditor.EditorCallback;
import com.pop.demo.view.floatingeditor.EditorHolder;
import com.pop.demo.view.floatingeditor.FloatEditorActivity;

/**
 * Created by pengfu on 07/04/2018.
 */

public class InputEditTextCursorAct extends Activity implements View.OnClickListener {

    private TextView mTVResult ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_input_edit_text_cursor);

        findViewById(R.id.tv_submit).setOnClickListener(this);

        mTVResult = findViewById(R.id.tv_input_result);
    }

    @Override
    public void onClick(View v) {
        FloatEditorActivity.openEditor(InputEditTextCursorAct.this, editorCallback,
                new EditorHolder(R.layout.fast_reply_floating_layout,
                        0, R.id.tv_submit, R.id.et_content));
    }


    private EditorCallback editorCallback = new EditorCallback(){
        @Override
        public void onCancel() {

        }

        @Override
        public void onSubmit(String content) {
            mTVResult.setText(content);
        }

        @Override
        public void onAttached(ViewGroup rootView) {

        }
    } ;
}
