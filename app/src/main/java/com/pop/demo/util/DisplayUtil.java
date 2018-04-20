package com.pop.demo.util;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.pop.demo.R;

/**
 * Created by pengfu on 20/04/2018.
 */

public final class DisplayUtil {


    /**
     * display for from drawables (only images, non-9patch)
     * @param url like resId of R.drawable.image
     * @param view
     */
    public static void displayImageDrawable(int url , ImageView view){
        ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.DRAWABLE.wrap(String.valueOf(url)) ,view);
    }
}
