package com.pop.demo.activity.customView;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.pop.demo.R;
import com.pop.demo.util.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tellh.com.recyclertreeview_lib.TreeNode;
import tellh.com.recyclertreeview_lib.TreeViewAdapter;

/**
 * Created by pengfu on 20/07/2018.
 */

public class TreeViewActivity extends Activity {

    private RecyclerView mTreeView ;
    private TreeViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_tree_view);

        mTreeView = findViewById(R.id.rv_tree_view);

        List<TreeNode> nodes = new ArrayList<>();
        TreeNode<TreeElement> root = new TreeNode<>(new TreeElement("Root"));

        root.addChild(new TreeNode(new TreeElement("世界")).
                addChild(new TreeNode(new TreeElement("亚洲")).
                        addChild(new TreeNode(new TreeElement("中国")).
                                addChild(new TreeNode(new TreeElement("河北省")).
                                        addChild(new TreeNode(new TreeElement("北京市")).
                                                addChild(new TreeNode(new TreeElement("朝阳区")).
                                                        addChild(new TreeNode(new TreeElement("北三环中路")).
                                                                addChild(new TreeNode(new TreeElement("鸟巢大剧院")).
                                                                        addChild(new TreeNode(new TreeElement("北门33层567排89座"))))))).
                                        addChild(new TreeNode(new TreeElement("天津市")))).
                                addChild(new TreeNode(new TreeElement("浙江省"))))));

        nodes.add(root);

        mTreeView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TreeViewAdapter(nodes, Arrays.asList(new ElementNodeBinder()));
        adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    //Update and toggle the node.
                    onToggle(!node.isExpand(), holder);
//                    if (!node.isExpand())
//                        adapter.collapseBrotherNode(node);
                }
                ToastUtils.showSingleToast(node.getContent().getDesc());
                return false;
            }

            @Override
            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
                ElementNodeBinder.ViewHolder dirViewHolder = (ElementNodeBinder.ViewHolder) holder;
                final ImageView ivArrow = dirViewHolder.getIvArrow();
                int rotateDegree = isExpand ? 90 : -90;
                ivArrow.animate().rotationBy(rotateDegree)
                        .start();
            }
        });
        mTreeView.setAdapter(adapter);
    }
}
