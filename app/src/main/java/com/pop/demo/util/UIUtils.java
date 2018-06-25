package com.pop.demo.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.pop.demo.App;

/**
 * Created by lenovo on 2016/11/21.
 */
public class UIUtils {


    private static final String TAG = "UIUtils" ;

    public static String print(){
        String info = "ScreenSize:"+getScreenWidth()+"x"+getScreenHeight()+"::density:"+App.getInstance().getResources().getDisplayMetrics().density ;
        L.d(TAG ,info);
        return info ;
    }

    /**
     * dp/dip转换为px.
     * @param dpValue
     * @return
     */
    public static int dp2px(int dpValue) {
        float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取屏幕的宽度.
     * @return
     */
    public static int getScreenWidth() {
        return  App.getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 另外一种方式：获取屏幕的宽度，待验证.
     * @return
     */
    public static int getScreenWidth2() {
        return  ((WindowManager)(App.getInstance().getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕的高度.
     * @return
     */
    public static int getScreenHeight() {
        return  App.getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    public static void hideActionBar(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = window.getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }
}
