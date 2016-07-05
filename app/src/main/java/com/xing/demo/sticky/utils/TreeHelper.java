package com.xing.demo.sticky.utils;



import com.xing.demo.sticky.annotation.TreeNodeId;
import com.xing.demo.sticky.annotation.TreeNodeLabel;
import com.xing.demo.sticky.annotation.TreeNodePid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jun on 2016/6/3 0003.
 */
public class TreeHelper {


    /**
     * 将用户数据转化为节点数据
     *
     * @param datas
     * @param <T>
     * @return
     */
    private static <T> List<Node> convertDatas2Nodes(List<T> datas) throws IllegalAccessException {
        ArrayList<Node> nodes = new ArrayList<Node>();
        Node node = null;
        for (T t : datas) {
            int id = -1;
            int pid = -1;
            String label = null;

            Class clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(TreeNodeId.class) != null) {
                    field.setAccessible(true);
                    id = field.getInt(t);
                }
                if (field.getAnnotation(TreeNodePid.class) != null) {
                    field.setAccessible(true);
                    pid = field.getInt(t);
                }
                if (field.getAnnotation(TreeNodeLabel.class) != null) {
                    field.setAccessible(true);
                    label = (String) field.get(t);
                }
            }
            node = new Node(id, pid, label);
            nodes.add(node);
        }

        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node m = nodes.get(j);
                if (m.getpId() == n.getId()) {
                    n.getChildren().add(m);
                    m.setParent(n);
                } else if (n.getpId() == m.getId()) {
                    m.getChildren().add(n);
                    n.setParent(m);
                }
            }
        }

        return nodes;
    }

    public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel) throws IllegalAccessException {
        List<Node> result = new ArrayList<Node>();
        List<Node> nodes = convertDatas2Nodes(datas);

        //获得树的根节点
        List<Node> rootNodes = getRootNodes(nodes);

        for (Node node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }

        return result;
    }

    private static void addNode(List<Node> result, Node node, int defaultExpandLevel, int currentLevel) {
        result.add(node);

        if (defaultExpandLevel >= currentLevel) {
            node.setExpand(true);
        }
        if (node.isLeaf()) {
            return;
        }

        for (int i = 0; i < node.getChildren().size(); i++) {
            addNode(result, node.getChildren().get(i), defaultExpandLevel, currentLevel);
        }
    }

    /**
     * 过滤出可见的节点
     * @param nodes
     * @return
     */
    public static List<Node> filterVisibleNodes(List<Node> nodes) {
        List<Node> result = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node.isRoot() || node.isParentExpand()) {
//                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }

    /**
     * 从所有节点中过滤根节点
     *
     * @param nodes
     * @return
     */
    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> root = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node.isRoot()) {
                root.add(node);
            }
        }
        return root;
    }

}
