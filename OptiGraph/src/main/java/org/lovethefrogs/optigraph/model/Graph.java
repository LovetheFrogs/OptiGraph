package org.lovethefrogs.optigraph.model;

import org.lovethefrogs.optigraph.utils.Coords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Graph {
    private HashMap<Integer, Node> nodes;
    private ArrayList<Node> nodeList;
    private int nodeCount;
    private List<List<Double>> graph;
    private Node center;
    public Graph() {
        this.nodes = new HashMap();
        this.nodeList = new ArrayList<>();
        this.nodeCount = 0;
        this.graph = new ArrayList<>();
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public HashMap<Integer, Node> getNodes() {
        return nodes;
    }

    public Node getCenter() {
        return center;
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public boolean isEmpty() {
        return this.nodeCount == 0;
    }

    public Node addNode(String name, int x, int y) {
        Node newNode = new Node(this.nodeCount, name, new Coords(x, y), this.isEmpty());

        if (isEmpty()) this.center= newNode;

        for (Node node : nodeList) {
            double dist = newNode.getCoords().distance(node.getCoords());
            graph.add(Arrays.asList((double) newNode.getId(), (double) node.getId(), dist));
            graph.add(Arrays.asList((double) node.getId(), (double) newNode.getId(), dist));
        }
        nodeList.add(newNode);
        nodes.put(newNode.getId(), newNode);
        this.nodeCount++;

        return newNode;
    }

    public Node removeNode(Node node) {
        if (!nodeList.contains(node) || node.isCenter()) return null;
        for (Node aux : nodeList) {
            double dist = node.getCoords().distance(aux.getCoords());
            graph.remove(Arrays.asList((double) node.getId(), (double) aux.getId(), dist));
            graph.remove(Arrays.asList((double) aux.getId(), (double) node.getId(), dist));
        }
        nodes.remove(node.getId());
        nodeList.remove(node);
        this.nodeCount--;

        return node;
    }
}
