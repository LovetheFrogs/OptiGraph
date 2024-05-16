package org.lovethefrogs.optigraph.model;

import org.lovethefrogs.optigraph.utils.Coords;
import org.lovethefrogs.optigraph.utils.Sort;

import java.util.*;

public class Graph {
    private HashMap<Integer, Node> nodes;
    private ArrayList<Node> nodeList;
    private int nodeCount;
    private ArrayList<List<Double>> graph;
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

    private double findParent(double[] parent, double node) {
        if (parent[(int) node] != node) {
            parent[(int) node] = findParent(parent, parent[(int) node]);
        }
        return parent[(int) node];
    }

    private void union(double[] parent, double[] rank, double x, double y) {
        double xRoot = findParent(parent, x);
        double yRoot = findParent(parent, y);

        if (rank[(int) xRoot] < rank[(int) yRoot]) {
            parent[(int) xRoot] = yRoot;
        } else if (rank[(int) xRoot] > rank[(int) yRoot]) {
            parent[(int) yRoot] = xRoot;
        } else {
            parent[(int) yRoot] = xRoot;
            rank[(int) xRoot]++;
        }
    }

    private double[][] populateMatrix() {
        double[][] mat = new double[nodeCount][nodeCount];

        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) mat[i][j] = Double.POSITIVE_INFINITY;
        }

        for (List<Double> edge : graph) {
            mat[edge.get(0).intValue()][edge.get(1).intValue()] = edge.get(2);
        }

        return mat;
    }

    private int minKey(double[] key, boolean[] visited) {
        double min = Double.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < key.length; v++) {
            if (!visited[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }

        return minIndex;
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

    public ArrayList<List<Integer>> kruskal() {
        Sort sorter = new Sort();
        ArrayList<List<Double>> result = new ArrayList<>();
        if (this.graph.size() >= 1000000) sorter.quickSort(graph);
        else sorter.bucketSort(graph);

        double[] parent = new double[nodeCount];
        double[] rank = new double[nodeCount];

        for (int i = 0; i < nodeCount; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        int e = 0;
        ArrayList<List<Integer>> edgeList = new ArrayList<>();

        for (List<Double> edge : graph) {
            double src = edge.get(0);
            double dest = edge.get(1);
            double weight = edge.get(2);

            double x = findParent(parent, src);
            double y = findParent(parent, dest);

            if (x != y) {
                List<Double> edgeInfo = new ArrayList<>();
                edgeInfo.add(src);
                edgeInfo.add(dest);
                edgeInfo.add(weight);
                result.add(edgeInfo);

                // Add edge to edgeList
                List<Integer> edgeIndices = new ArrayList<>();
                edgeIndices.add((int) src);
                edgeIndices.add((int) dest);
                edgeList.add(edgeIndices);

                union(parent, rank, x, y);
                e++;
            }

            if (e >= nodeCount - 1) break;
        }

        return edgeList;
    }

    public ArrayList<List<Integer>> prim() {
        double[][] g = populateMatrix();
        int n = nodeCount;
        ArrayList<List<Integer>> edgeList = new ArrayList<>();
        boolean[] visited = new boolean[n];
        double[] key = new double[n];
        int[] parent = new int[n];

        for (int i = 0; i < n; i++) {
            key[i] = Double.MAX_VALUE;
            visited[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int count = 0; count < n - 1; count++) {
            int u = minKey(key, visited);
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (g[u][v] != Double.POSITIVE_INFINITY && !visited[v] && g[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = g[u][v];
                }
            }
        }

        for (int i = 1; i < n; i++) {
            List<Integer> edge = new ArrayList<>();
            edge.add(parent[i]);
            edge.add(i);
            edgeList.add(edge);
        }

        return edgeList;
    }


    public ArrayList<List<Integer>> dijkstra() {
        int src = center.getId();
        double[][] graphMatrix = populateMatrix();
        int n = nodeCount;
        double[] distances = new double[n];
        int[] parent = new int[n];
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            parent[i] = -1;
            visited[i] = false;
        }

        distances[src] = 0.0;

        for (int count = 0; count < n - 1; count++) {
            int u = minKey(distances, visited);

            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && graphMatrix[u][v] != Double.POSITIVE_INFINITY && distances[u] + graphMatrix[u][v] < distances[v]) {
                    distances[v] = distances[u] + graphMatrix[u][v];
                    parent[v] = u;
                }
            }
        }

        ArrayList<List<Integer>> edgeList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (parent[i] != -1) {
                List<Integer> edge = new ArrayList<>();
                edge.add(parent[i]);
                edge.add(i);
                edgeList.add(edge);
            }
        }

        return edgeList;
    }
}
