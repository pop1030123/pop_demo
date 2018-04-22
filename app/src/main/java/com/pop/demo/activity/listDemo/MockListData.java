package com.pop.demo.activity.listDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengfu on 21/04/2018.
 */

public class MockListData {


    private static final int MAX_PAGE = 5;


    public static List<String> getData(int pageNo, int pageSize) {
        return getData(pageNo, pageSize, "DEMO数据");
    }


    public static List<String> getData(int pageNo, int pageSize, String tag) {

        List<String> dataList = new ArrayList<>();

        if (pageNo < MAX_PAGE) {

            int itemStart = pageNo * pageSize;

            for (int i = itemStart; i < itemStart + pageSize; i++) {
                dataList.add(tag + i);
            }
        }

        return dataList;

    }
}
