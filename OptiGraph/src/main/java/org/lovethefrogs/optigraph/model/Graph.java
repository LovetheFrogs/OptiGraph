package org.lovethefrogs.optigraph.model;

import javafx.util.Pair;
import org.lovethefrogs.optigraph.utils.Coords;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private ArrayList<Node> nodes;
    private int nodeCount;
    private HashMap<Node, HashMap<Node, Double>> graph;
    private Node center;
    public Graph() {
        this.nodes = new ArrayList<>();
        this.nodeCount = 0;
        this.graph = new HashMap<>();
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Node getCenter() {
        return center;
    }

    public boolean isEmpty() {
        return this.nodeCount == 0;
    }

    public Node addNode(String name, int x, int y) {
        Node newNode = new Node(this.nodeCount, name, new Coords(x, y), this.isEmpty());
        nodes.add(newNode);
        newNode.setDist(computeDistances(newNode));

        if (isEmpty()) this.center= newNode;

        graph.put(newNode, createVertexList(newNode));
        for (Node node : this.nodes) {
            if (!node.equals(newNode)) {
                node.setDist(computeDistances(node));
                HashMap<Node, Double> aux = graph.get(node);
                aux.put(newNode, node.getDist().get(newNode.getId()));
                graph.put(node, aux);
            }
        }

        this.nodeCount++;

        return newNode;
    }

    public Node removeNode(Node node) {
        if (!nodes.contains(node)) return null;
        for (Node aux : nodes) {
            HashMap<Node, Double> connected = graph.get(aux);
            connected.remove(node);
        }
        graph.remove(node);
        nodes.remove(node);
        this.nodeCount--;

        return node;
    }

    private HashMap<Node, Double> createVertexList(Node newNode) {
        HashMap<Node, Double> connections = new HashMap<>();
        for (Node obj : this.nodes) {
            if (!newNode.equals(obj)) connections.put(obj, newNode.getDist().get(obj.getId()));
        }
        return connections;
    }

    public ArrayList<Double> computeDistances(Node node) {
        ArrayList<Double> dist = new ArrayList<>();
        for (Node element : this.nodes) dist.add(node.getCoords().distance(element.getCoords()));
        return dist;
    }
}
