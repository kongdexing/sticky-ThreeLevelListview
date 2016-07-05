package com.xing.demo.sticky.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xing.demo.sticky.R;
import com.xing.demo.sticky.utils.Node;

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

    private void clearView() {
        rootNode = null;
        stickNodes.clear();
        stickNodes = new ArrayList<Node>();
        this.removeAllViews();
        this.setVisibility(View.GONE);
    }

    public void changeView(List<Node> visibleNodes, int Position) {
        Node VisibleNode = visibleNodes.get(Position);
        if (VisibleNode == null) {
            return;
        }

        if (VisibleNode.isRoot()) {
            if (VisibleNode.isExpand() && VisibleNode.getChildren().size() > 0) {
                if (rootNode != null) {
                    clearView();
                    return;
                }
                rootNode = VisibleNode;
                stickNodes.add(VisibleNode);
            } else {
                clearView();
            }
        }

        if (rootNode == null) {
            Log.i(TAG, "changeView: rootNode is null visibleNode level " + VisibleNode.getLevel());
            //根据第三级节点，获取当前三级节点的跟节点
            Node secondNode = VisibleNode.getParent();
            if (secondNode != null) {
                Node firstNode = secondNode.getParent();
                if (firstNode != null && firstNode.isRoot()) {
                    rootNode = firstNode;
                    VisibleNode = rootNode;
                    stickNodes.add(VisibleNode);
                }
            }
            if (rootNode == null) {
                return;
            }
        }

        TextView txtStickyName = getTextView(VisibleNode);

        //判断当前节点与根节点关系
        if (rootNode != null) {
            Log.i(TAG, "changeView: rootNode is not null");
            //VisibleNode做为根节点，判断是否添加根节点
            if (VisibleNode.getChildren().size() > 0 && VisibleNode.isExpand()
                    && this.getVisibility() == View.GONE) {
                this.setVisibility(View.VISIBLE);
                this.addView(stickyHeader);
                txtStickyName.setText(VisibleNode.getName());
                Log.i(TAG, "changeView: add RootNode " + VisibleNode.getName());
                return;
            }
            Node currentParentNode = VisibleNode.getParent();
            //VisibleNode 做为第二节点
            if (rootNode.equals(currentParentNode) && VisibleNode.getChildren().size() > 0
                    && VisibleNode.isExpand()) {
                this.setVisibility(View.VISIBLE);
                Log.i(TAG, "changeView: getChildCount " + this.getChildCount());
                //判断ll_stickyheaders 子控件个数
                if (this.getChildCount() > 1) {
                    this.removeViewAt(1);
                    clearStickNodes();
                    return;
                }
                stickNodes.add(VisibleNode);
                this.addView(stickyHeader);
                txtStickyName.setText(VisibleNode.getName());
            } else if (this.getChildCount() == 1 && currentParentNode != null &&
                    rootNode.equals(currentParentNode.getParent()) && currentParentNode.isExpand()) {
                //向上滑动，存在第一级节点，添加第二级节点
                Log.i(TAG, "changeView: 向上滑动 " + currentParentNode.getName());
                stickNodes.add(currentParentNode);
                this.addView(stickyHeader);
                txtStickyName.setText(currentParentNode.getName());
            }
        }

        Log.i(TAG, "changeView: node name " + VisibleNode.getName());
        Log.i(TAG, "changeView: stickNodes size " + stickNodes.size());
    }

    private void clearStickNodes() {
        for (int i = stickNodes.size() - 1; i >= 0; i--) {
            if (!stickNodes.get(i).equals(rootNode)) {
                Log.i(TAG, "clearStickNodes: Node name " + stickNodes.get(i).getName());
                stickNodes.remove(i);
            }
        }
    }

    private TextView getTextView(final Node visibleNode) {
        stickyHeader = LayoutInflater.from(this.getContext()).inflate(R.layout.sticky_treeitem, null, false);
        LinearLayout llStickyItem = (LinearLayout) stickyHeader.findViewById(R.id.llStickyItem);
        TextView txtStickyName = (TextView) stickyHeader.findViewById(R.id.txt_stickyName);
        stickyHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nodeClickListener != null) {
                    nodeClickListener.onStickyNodeClick(visibleNode);
                    Log.i(TAG, "onClick: before remove views " + StickyHeaderView.this.getChildCount() + " stickNodes " + stickNodes.size());
                    if (visibleNode.getLevel() == 0) {
                        StickyHeaderView.this.removeAllViews();
                    } else {
                        StickyHeaderView.this.removeViewAt(1);
                    }
                    stickNodes.remove(visibleNode);
                    Log.i(TAG, "onClick: after remove views " + StickyHeaderView.this.getChildCount() + " stickNodes " + stickNodes.size());
                }
            }
        });

        if (visibleNode.isRoot()) {
            txtStickyName.setPadding(25, 3, 3, 3);
            llStickyItem.setBackgroundColor(this.getContext().getResources().getColor(R.color.color_list_group));
        } else {
            llStickyItem.setPadding(100, 3, 3, 3);
            llStickyItem.setBackgroundColor(this.getContext().getResources().getColor(R.color.color_list_item));
        }
        return txtStickyName;
    }

    public interface OnStickyNodeClickListener {
        void onStickyNodeClick(Node node);
    }


}
