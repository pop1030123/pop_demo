package com.pop.demo.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by pengfu on 24/11/2017.
 */

public class SimpleGridSpacingItemDecoration extends RecyclerView.ItemDecoration {


    private int mSpacing ;

    public SimpleGridSpacingItemDecoration(int spacing){
        this.mSpacing = spacing ;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        /*
     *  outRect.set(left, top, right, bottom);
     *  在Item的四周设定距离
     *  所以当Orientation为垂直时，我们只需要在每个Item的下方预留出分割线的高度就可以了
     *  同理当Orientation为水平时，我们只需要在每个Item的右方预留出分割线的宽度就可以了
     *  但通常我们使用分割线的style都是统一的，这样我们在attrs中只需要定义一个即可，即共同使用Height
     */
        outRect.set(mSpacing, mSpacing, mSpacing, mSpacing);
    }
}
