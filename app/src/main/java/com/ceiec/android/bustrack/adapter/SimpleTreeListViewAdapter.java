package com.ceiec.android.bustrack.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ceiec.android.bustrack.R;
import com.ceiec.android.bustrack.utils.CommonUtil;
import com.ceiec.android.bustrack.utils.Node;

import java.util.List;

/**
 * Created by jun on 2016/6/6 0006.
 */
public class SimpleTreeListViewAdapter<T> extends TreeListViewAdapter<T> {

    private String TAG = SimpleTreeListViewAdapter.class.getSimpleName();
    private int padding = 0;

    public void setScrollListener(OnTreeListScrollListener mScrollListener) {
        this.mScrollListener = mScrollListener;
    }

    private OnTreeListScrollListener mScrollListener;

    public interface OnTreeListScrollListener {
        void onTreeScroll(List<Node> visibleNodes, int visiblePosition);
    }

    public SimpleTreeListViewAdapter(final ListView tree, Context context, List datas, int defaultExpandLevel) throws IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
        padding = CommonUtil.getDimensSize(mContext, R.dimen.tree_item_padding);
        tree.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.i(TAG, "onScrollStateChanged: scrollState " + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i(TAG, "onScroll: firstVisibleItem:" + firstVisibleItem + " visibleItemCount " + visibleItemCount
                        + " totalItemCount " + totalItemCount);
                Log.i(TAG, "onScroll: firstVisible Name " + mVisibleNode.get(firstVisibleItem).getName());

                if (mScrollListener != null) {
                    mScrollListener.onTreeScroll(mVisibleNode, firstVisibleItem);
                }
            }
        });
    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
//        if (convertView == null) {
//
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }

        convertView = mInflater.inflate(R.layout.layout_treeitem, parent, false);
        holder = new ViewHolder();
        holder.rlTreeItem = (RelativeLayout) convertView.findViewById(R.id.rlTreeItem);
        holder.txtTreeName = (TextView) convertView.findViewById(R.id.txt_treeName);

        if (node != null) {
            if (node.isRoot()) {
                holder.rlTreeItem.setBackgroundColor(mContext.getResources().getColor(R.color.color_list_group));
            } else {
                holder.rlTreeItem.setBackgroundColor(mContext.getResources().getColor(R.color.color_list_item));
            }
            holder.txtTreeName.setText(node.getName());
        }

        Log.i(TAG, "getConvertView: node level "+node.getLevel()+" name "+node.getName());

        if (node.getLevel() == 0) {
            holder.txtTreeName.setPadding(25, 3, 3, 3);
        } else if (node.getLevel() == 1) {
            convertView.setPadding(100, 3, 3, 3);
        } else if (node.getLevel() == 2) {
            convertView.setPadding(190, 3, 3, 3);
        } else {
//            convertView.setPadding(node.getLevel() * 30, 3, 3, 3);
        }
        return convertView;
    }

    private class ViewHolder {
        RelativeLayout rlTreeItem;
        TextView txtTreeName;
    }

}
