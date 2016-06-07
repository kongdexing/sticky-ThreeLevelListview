package com.ceiec.android.bustrack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.ceiec.android.bustrack.utils.Node;
import com.ceiec.android.bustrack.utils.TreeHelper;

import java.util.List;

/**
 * Created by jun on 2016/6/6 0006.
 */
public abstract class TreeListViewAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<Node> mAllNodes;
    protected List<Node> mVisibleNode;
    protected LayoutInflater mInflater;

    protected ListView mTree;

    public void setOnTreeNodeClickListener(OnTreeNodeClickListener mOnTreeNodeClickListener) {
        this.mOnTreeNodeClickListener = mOnTreeNodeClickListener;
    }

    private OnTreeNodeClickListener mOnTreeNodeClickListener;

    public interface OnTreeNodeClickListener {
        void onClick(Node node, int position);
    }

    public TreeListViewAdapter(ListView tree, Context context, List<T> datas, int defaultExpandLevel) throws IllegalAccessException {
        mContext = context;
        mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
        mVisibleNode = TreeHelper.filterVisibleNodes(mAllNodes);
        mInflater = LayoutInflater.from(context);

        mTree = tree;
        mTree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                expendOrCollapse(position);
                if (mOnTreeNodeClickListener != null) {
                    mOnTreeNodeClickListener.onClick(mVisibleNode.get(position), position);
                }
            }
        });
    }

    /**
     * 点击收缩或展开
     *
     * @param position
     */
    private void expendOrCollapse(int position) {
        Node n = mVisibleNode.get(position);
        expendOrCollapse(n);
    }

    public void expendOrCollapse(Node node) {
        if (node != null) {
            if (node.isLeaf())
                return;
            node.setExpand(!node.isExpand());
            mVisibleNode = TreeHelper.filterVisibleNodes(mAllNodes);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mVisibleNode.size();
    }

    @Override
    public Object getItem(int position) {
        return mVisibleNode.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Node node = mVisibleNode.get(position);
        convertView = getConvertView(node, position, convertView, parent);
        return convertView;
    }

    public abstract View getConvertView(Node node, int position, View convertView, ViewGroup parent);

}
