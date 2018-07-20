package com.pop.demo.activity.customView;

import com.pop.demo.R;

import tellh.com.recyclertreeview_lib.LayoutItemType;

/**
 * Created by pengfu on 20/07/2018.
 */

public class TreeElement implements LayoutItemType {


    private String name ;


    public TreeElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tree;
    }

    @Override
    public String getDesc() {
        return getName();
    }
}
