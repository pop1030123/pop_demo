package com.pop.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.pop.demo.App;
import com.pop.demo.R;
import com.pop.demo.view.MarqueeTextView;

import static android.view.View.GONE;

/**
 * Created by pengfu on 16/11/16.
 */
public class EditTextAct extends Activity implements View.OnClickListener {


    private View mInputLayout ;
    private EditText mEditView ;
    private Button mButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(App.TAG ,"onCreate");
        setContentView(R.layout.act_edit_text);
        mInputLayout = findViewById(R.id.input_layout) ;
        mInputLayout.setOnClickListener(this);
        mEditView = (EditText)findViewById(R.id.edit_view) ;
        mButton = (Button)findViewById(R.id.btn_view) ;
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.input_layout:
                mInputLayout.setVisibility(GONE);
                hideSoftKeyboard(this ,mEditView) ;
                break ;
            case R.id.btn_view:
                mInputLayout.setVisibility(View.VISIBLE);
                mEditView.setFocusable(true);
                mEditView.requestFocus();
                showSoftKeyboard(mEditView ,this) ;
                break ;
        }
    }

    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //显示软键盘
    public static void showSoftKeyboard(EditText editText, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }
}
