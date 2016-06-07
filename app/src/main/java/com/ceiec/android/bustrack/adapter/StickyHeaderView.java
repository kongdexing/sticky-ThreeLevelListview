package com.ceiec.android.bustrack.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ceiec.android.bustrack.R;
import com.ceiec.android.bustrack.utils.CommonUtil;
import com.ceiec.android.bustrack.utils.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jun on 2016/6/6 0006.
 */
public class StickyHeaderView extends LinearLayout {

    private String TAG = "TreeNode";
    private List<Node> stickNodes = new ArrayList<Node>();
    private Node rootNode;
    private View stickyHeader;
    private OnStickyNodeClickListener nodeClickListener;

    public StickyHeaderView(Context context) {
        this(context, null);
    }

    public StickyHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(LinearLayout.VERTICAL);
    }

    public void setNodeClickListener(OnStickyNodeClickListener nodeClickListener) {
        this.nodeClickListener = nodeClickListener;
    }

    public void changeView(List<Node> visibleNodes, int Position) {
        final Node VisibleNode = visibleNodes.get(Position);
        if (VisibleNode == null) {
            return;
        }

        if (VisibleNode.isRoot()) {
            if (VisibleNode.isExpand() && VisibleNode.getChildren().size() > 0) {
                if (rootNode != null) {
                    this.removeAllViews();
                    this.setVisibility(View.GONE);
                }
                rootNode = VisibleNode;
                stickNodes.add(VisibleNode);
            } else {
                rootNode = null;
                stickNodes.clear();
                stickNodes = new ArrayList<Node>();
                this.removeAllViews();
                this.setVisibility(View.GONE);
            }
        }

        stickyHeader = LayoutInflater.from(this.getContext()).inflate(R.layout.sticky_treeitem, null, false);
        RelativeLayout rlStickyItem = (RelativeLayout) stickyHeader.findViewById(R.id.rlStickyItem);
        TextView txtStickyName = (TextView) stickyHeader.findViewById(R.id.txt_stickyName);
        stickyHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nodeClickListener != null) {
                    nodeClickListener.onStickyNodeClick(VisibleNode);
                }
            }
        });

        if (VisibleNode.isRoot()) {
//            txtStickyName.setPadding(25, 3, 3, 3);
            rlStickyItem.setBackgroundColor(this.getContext().getResources().getColor(R.color.color_list_group));
        } else {
//            rlStickyItem.setPadding(100, 3, 3, 3);
            rlStickyItem.setBackgroundColor(this.getContext().getResources().getColor(R.color.color_list_item));
        }

        //判断当前节点与根节点关系
        if (rootNode != null) {
            Log.i(TAG, "changeView: rootNode is not null");
            //VisibleNode做为根节点，判断是否添加根节点
            if (VisibleNode.getChildren().size() > 0 && VisibleNode.isExpand()
                    && this.getVisibility() == View.GONE) {
                this.setVisibility(View.VISIBLE);
                this.addView(stickyHeader);
                txtStickyName.setText(VisibleNode.getName());
            }
            //VisibleNode 做为第二节点
            if (VisibleNode.getParent() == rootNode && VisibleNode.getChildren().size() > 0
                    && VisibleNode.isExpand()) {
                this.setVisibility(View.VISIBLE);

                //判断ll_stickyheaders 子控件个数
                if (this.getChildCount() > 1) {
                    if (stickNodes.contains(VisibleNode)) {
                        this.removeViewAt(1);
                        stickNodes.remove(VisibleNode);
                        return;
                    } else {
                        this.removeViewAt(1);
                        stickNodes.remove(VisibleNode);
                    }
                }
                stickNodes.add(VisibleNode);
                this.addView(stickyHeader);
                txtStickyName.setText(VisibleNode.getName());
            }
        } else {
            Log.i(TAG, "changeView: rootNode is null");
        }

        Log.i(TAG, "changeView: stickyHeader height " + stickyHeader.getHeight());
        Log.i(TAG, "changeView: stickyHeaderView height " + this.getHeight());

        Log.i(TAG, "changeView: node name " + VisibleNode.getName());
        Log.i(TAG, "changeView: stickNodes size " + stickNodes.size());
    }

    public interface OnStickyNodeClickListener {
        void onStickyNodeClick(Node node);
    }


}
