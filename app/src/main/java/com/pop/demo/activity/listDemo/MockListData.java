package com.pop.demo.activity.listDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengfu on 21/04/2018.
 */

public class MockListData {


    private static final int MAX_PAGE = 10;


    public static List<String> getData(int pageNo, int pageSize) {

        List<String> dataList = new ArrayList<>();

        if (pageNo < MAX_PAGE) {

            int itemStart = pageNo * pageSize;

            for (int i = itemStart; i < itemStart + pageSize; i++) {
                dataList.add("DEMO数据" + i);
            }
        }

        return dataList;

    }
}
