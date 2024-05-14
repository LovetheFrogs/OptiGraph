import org.lovethefrogs.optigraph.model.Graph;
import junit.framework.*;

public class GraphTest extends TestCase {
    protected Graph graph;

    @Override
    protected void setUp() throws Exception {
        this.graph = new Graph();
        graph.addNode("a", 0, 0);
        graph.addNode("b", 1, 2);
        graph.addNode("c", -1, -3);
    }

    public void testAddNode() {
        assertEquals(graph.getNodeCount(), 3);
    }

    public void testDeleteNode() {
        graph.removeNode(graph.getNodes().get(1));
        assertEquals(graph.getNodeCount(), 2);
    }
}
