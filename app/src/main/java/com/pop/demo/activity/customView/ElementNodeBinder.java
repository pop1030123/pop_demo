package com.pop.demo.activity.customView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pop.demo.R;

import tellh.com.recyclertreeview_lib.TreeNode;
import tellh.com.recyclertreeview_lib.TreeViewBinder;

/**
 * Created by pengfu on 20/07/2018.
 */

public class ElementNodeBinder extends TreeViewBinder<ElementNodeBinder.ViewHolder>  {


    @Override
    public int getLayoutId() {
        return R.layout.item_tree;
    }

    @Override
    public String getDesc() {
        return "ElementNodeBinder";
    }

    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        TreeElement treeElement = (TreeElement) node.getContent();
        holder.tvName.setText(treeElement.getName());
    }

    public class ViewHolder extends TreeViewBinder.ViewHolder {
        private ImageView ivArrow;
        private TextView tvName;


        public ViewHolder(View rootView) {
            super(rootView);
            this.ivArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
        }

        public ImageView getIvArrow() {
            return ivArrow;
        }
    }
}
