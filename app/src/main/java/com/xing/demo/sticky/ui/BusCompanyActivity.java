package com.xing.demo.sticky.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.xing.demo.sticky.R;
import com.xing.demo.sticky.adapter.SimpleTreeListViewAdapter;
import com.xing.demo.sticky.adapter.StickyHeaderView;
import com.xing.demo.sticky.bean.FileBean;
import com.xing.demo.sticky.utils.Node;

import java.util.ArrayList;
import java.util.List;

public class BusCompanyActivity extends AppCompatActivity {

    private String TAG = "TreeNode";
    private ListView listTreeView;
    private SimpleTreeListViewAdapter<FileBean> mAdapter;
    private List<FileBean> mDatas;
    private StickyHeaderView tickyHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_company);

        listTreeView = (ListView) findViewById(R.id.list_treeview);
        tickyHeaderView = (StickyHeaderView) findViewById(R.id.stickyHeaderView);
        tickyHeaderView.setVisibility(View.GONE);
        tickyHeaderView.setNodeClickListener(new StickyHeaderView.OnStickyNodeClickListener() {
            @Override
            public void onStickyNodeClick(Node node) {
                if (mAdapter != null)
                    mAdapter.expandBySticky(node);
            }
        });

        initDatas();

        try {
            mAdapter = new SimpleTreeListViewAdapter<>(listTreeView, this, mDatas, 1);
            listTreeView.setAdapter(mAdapter);

            mAdapter.setScrollListener(new SimpleTreeListViewAdapter.OnTreeListScrollListener() {
                @Override
                public void onTreeScroll(List<Node> visibleNodes, int visiblePosition) {
                    if (tickyHeaderView == null) {
                        return;
                    }

                    if (tickyHeaderView.getChildCount() == 0) {
                        Log.i(TAG, "onTreeScroll: tickyView count 0");
                        tickyHeaderView.changeView(visibleNodes, visiblePosition);
                    }

                    if (tickyHeaderView.getChildCount() == 1) {
                        Log.i(TAG, "onTreeScroll: tickyView count 1");
                        if (visibleNodes.size() > visiblePosition + 1) {
                            tickyHeaderView.changeView(visibleNodes, visiblePosition + 1);
                        }
                    }

                    if (tickyHeaderView.getChildCount() == 2) {
                        Log.i(TAG, "onTreeScroll: tickyView count 2");
                        if (visibleNodes.size() > visiblePosition + 2) {
                            tickyHeaderView.changeView(visibleNodes, visiblePosition + 2);
                        }
                    }
                }
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void initDatas() {
        mDatas = new ArrayList<FileBean>();
        FileBean bean = new FileBean(1, 0, "根目录1");
        mDatas.add(bean);
        bean = new FileBean(2, 0, "根目录2");
        mDatas.add(bean);

        bean = new FileBean(21, 2, "根目录2-1");
        mDatas.add(bean);
        bean = new FileBean(211, 21, "根目录2-1-1");
        mDatas.add(bean);
        bean = new FileBean(212, 21, "根目录2-1-2");
        mDatas.add(bean);
        bean = new FileBean(213, 21, "根目录2-1-3");
        mDatas.add(bean);
        bean = new FileBean(214, 21, "根目录2-1-4");
        mDatas.add(bean);

        bean = new FileBean(3, 0, "根目录3");
        mDatas.add(bean);
        bean = new FileBean(4, 1, "根目录1-1");
        mDatas.add(bean);
        bean = new FileBean(111, 4, "根目录1-1-1");
        mDatas.add(bean);
        bean = new FileBean(112, 4, "根目录1-1-2");
        mDatas.add(bean);
        bean = new FileBean(113, 4, "根目录1-1-3");
        mDatas.add(bean);
        bean = new FileBean(114, 4, "根目录1-1-4");
        mDatas.add(bean);

        bean = new FileBean(5, 1, "根目录1-2");
        mDatas.add(bean);
        bean = new FileBean(6, 5, "根目录1-2-1");
        mDatas.add(bean);
        bean = new FileBean(7, 3, "根目录3-1");
        mDatas.add(bean);
        bean = new FileBean(311, 7, "根目录3-1-1");
        mDatas.add(bean);
        bean = new FileBean(312, 7, "根目录3-1-2");
        mDatas.add(bean);
        bean = new FileBean(313, 7, "根目录3-1-3");
        mDatas.add(bean);
        bean = new FileBean(314, 7, "根目录3-1-4");
        mDatas.add(bean);
        bean = new FileBean(315, 7, "根目录3-1-5");
        mDatas.add(bean);
        bean = new FileBean(316, 7, "根目录3-1-6");
        mDatas.add(bean);
        bean = new FileBean(317, 7, "根目录3-1-7");
        mDatas.add(bean);

        bean = new FileBean(8, 3, "根目录3-2");
        mDatas.add(bean);
        bean = new FileBean(9, 8, "根目录3-2-1");
        mDatas.add(bean);

        bean = new FileBean(10, 8, "根目录3-2-2");
        mDatas.add(bean);
        bean = new FileBean(11, 8, "根目录3-2-3");
        mDatas.add(bean);
        bean = new FileBean(12, 8, "根目录3-2-4");
        mDatas.add(bean);
        bean = new FileBean(13, 8, "根目录3-2-5");
        mDatas.add(bean);
        bean = new FileBean(14, 8, "根目录3-2-6");
        mDatas.add(bean);
        bean = new FileBean(15, 8, "根目录3-2-7");
        mDatas.add(bean);
        bean = new FileBean(16, 8, "根目录3-2-8");
        mDatas.add(bean);
        bean = new FileBean(17, 8, "根目录3-2-9");
        mDatas.add(bean);
        bean = new FileBean(18, 8, "根目录3-2-10");
        mDatas.add(bean);
    }

}
