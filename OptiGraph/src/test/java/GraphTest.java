import org.lovethefrogs.optigraph.model.Graph;
import junit.framework.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphTest extends TestCase {
    protected Graph graph;
    protected Graph big;

    @Override
    protected void setUp() throws Exception {
        this.graph = new Graph();
        graph.addNode("a", 0, 0);
        graph.addNode("b", 4, 0);
        graph.addNode("c", -2, -2);
        graph.addNode("d", 2, 3);
    }

    public void testAddNode() {
        assertEquals(graph.getNodeCount(), 4);
    }

    public void testDeleteNode() {
        graph.removeNode(graph.getNodes().get(1));
        assertEquals(graph.getNodeCount(), 3);
    }

    public void testKruskal() {
        ArrayList<List<Integer>> result = graph.kruskal();
        System.out.println(result);
    }

    public void testPrim() {
        ArrayList<List<Integer>> result = graph.prim(null);
        System.out.println(result);
    }

    public void testDijkstra() {
        ArrayList<List<Integer>> result = graph.dijkstra(null);
        System.out.println(result);
    }

    public void testAlgoSpeedComparison() {
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            this.graph = new Graph();
            for (int j = 0; j < 100; j++) {
                int x = random.nextInt(201) - 100;
                int y = random.nextInt(201) - 100;
                graph.addNode(String.valueOf((char) ('a' + j)), x, y);
            }

            this.big = new Graph();
            for (int j = 0; j < 1000  ; j++) {
                int x = random.nextInt(201) - 100;
                int y = random.nextInt(201) - 100;
                big.addNode(String.valueOf((char) ('a' + j)), x, y);
            }

            long startTime = System.nanoTime();
            graph.prim(null);
            long endTime = System.nanoTime();
            long executionTimePrim = (endTime - startTime) / 1000000;
            startTime = System.nanoTime();
            graph.kruskal();
            endTime = System.nanoTime();
            long executionTimeKruskal = (endTime - startTime) / 1000000;
            startTime = System.nanoTime();
            graph.dijkstra(null);
            endTime = System.nanoTime();
            long executionTimeDijkstra = (endTime - startTime) / 1000000;
            System.out.println("Short Prim takes " + executionTimePrim + "ms");
            System.out.println("Short Kruskal takes " + executionTimeKruskal + "ms");
            System.out.println("Short Dijkstra takes " + executionTimeKruskal + "ms");

            startTime = System.nanoTime();
            big.prim(null);
            endTime = System.nanoTime();
            executionTimePrim = (endTime - startTime) / 1000000;
            startTime = System.nanoTime();
            big.kruskal();
            endTime = System.nanoTime();
            executionTimeKruskal = (endTime - startTime) / 1000000;
            startTime = System.nanoTime();
            graph.dijkstra(null);
            endTime = System.nanoTime();
            executionTimeDijkstra = (endTime - startTime) / 1000000;
            System.out.println("Long Prim takes " + executionTimePrim + "ms");
            System.out.println("Long Kruskal takes " + executionTimeKruskal + "ms");
            System.out.println("Long Dijkstra takes " + executionTimeKruskal + "ms");
            System.out.println(" ");
        }
    }
}
