package com.pop.demo.activity.customView;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.pop.demo.R;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

/**
 * Created by pengfu on 20/07/2018.
 */

public class TreeViewActivity extends Activity {

    private static final String NAME = "Very long name for folder";
    private AndroidTreeView mTreeView ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_tree_view);

        ViewGroup containerView = (ViewGroup) findViewById(R.id.container);

        TreeNode root = TreeNode.root();

        TreeNode s1 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Folder with very long name ")).setViewHolder(new SelectableHeaderHolder(this));
        TreeNode s2 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Another folder with very long name")).setViewHolder(new SelectableHeaderHolder(this));

        fillFolder(s1);
        fillFolder(s2);

        root.addChildren(s1, s2);

        mTreeView = new AndroidTreeView(this, root);
        mTreeView.setDefaultAnimation(true);
        mTreeView.setUse2dScroll(true);
        mTreeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        containerView.addView(mTreeView.getView());

        mTreeView.expandAll();

    }


    private void fillFolder(TreeNode folder) {
        TreeNode currentNode = folder;
        for (int i = 0; i < 10; i++) {
            TreeNode file = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, NAME)).setViewHolder(new SelectableHeaderHolder(this));
            currentNode.addChild(file);
            currentNode = file;
        }
    }
}
