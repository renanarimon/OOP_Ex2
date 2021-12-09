package api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.Comparator;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/*
 *
 * @project Ex2
 * @auther Renana Rimon
 */class DW_graphTest {
    static DirectedWeightedGraph dw = new DW_graph();
    DW_graph graph = (DW_graph) dw;

    @BeforeAll
    static void beforeAll() {
        Geo_Location g = new Geo_Location(1, 2, 0);
        Geo_Location g1 = new Geo_Location(1.1, 5, 0);
        Geo_Location g2 = new Geo_Location(14, 23, 0);
        Geo_Location g3 = new Geo_Location(1, 8, 0);
        NodeData n = new Node(g, 0);
        NodeData n1 = new Node(g1, 1);
        NodeData n2 = new Node(g2, 2);
        NodeData n3 = new Node(g3, 3);
        dw.addNode(n);
        dw.addNode(n1);
        dw.addNode(n2);
        dw.addNode(n3);
        dw.connect(0, 1, 1);
        dw.connect(1, 2, 1);
        dw.connect(0, 3, 1);
        dw.connect(3, 1, 1);

    }

    @Test
    void getNode() {
        Geo_Location g5 = new Geo_Location(1, 8, 0);
        NodeData n5 = new Node(g5, 5);
        graph.addNode(n5);
        assertEquals(graph.getNode(5), n5);
        assertNull(graph.getNode(8)); //not exist key


    }

    @Test
    void getEdge() {
        EdgeData e = new Edge(1, 2, 1);
        assertEquals(e.getSrc(), dw.getEdge(1,2).getSrc());
        assertEquals(e.getDest(), dw.getEdge(1,2).getDest());
        assertEquals(e.getWeight(), dw.getEdge(1,2).getWeight());


    }

    @Test
    void addNode() {
        Geo_Location g4 = new Geo_Location(3,7,0);
        NodeData n4 = new Node(g4, 4);
        dw.addNode(n4);

        assertEquals(graph.getNodes().get(0), dw.getNode(0));
        assertEquals(graph.getNodes().get(4), n4);



    }

    @Test
    void connect() {
        graph.connect(0,2, 1);
        assertEquals(1, graph.getEdge(0, 2).getWeight());



    }

    @Test
    void nodeIter() {
        Iterator<NodeData> iterN = graph.nodeIter();
        int i=1;
        while (iterN.hasNext()){
            assertEquals(iterN.next(), graph.getNode(i++));
        }
    }

    @Test
    void testEdgeIter() {
        Iterator<EdgeData> iterE = graph.edgeIter(0);
        int sum = 0;
        while (iterE.hasNext()){
            iterE.next();
            sum++;
        }
        int size = graph.getChildren().get(0).size();
        assertEquals(sum, size);

    }

    @Test
    void removeNode() {
        int oldSize = graph.nodeSize();
        graph.removeNode(0);
        assertEquals(graph.nodeSize(), oldSize-1);
    }

    @Test
    void removeEdge() {
        int old = graph.edgeSize();
        graph.removeEdge(1,2);
        assertEquals(graph.edgeSize(), old-1);
    }

    @Test
    void nodeSize() {
        assertEquals(dw.nodeSize(), graph.getNodes().size());
    }

    @Test
    void edgeSize() {
        int sum=0;
        Iterator<EdgeData> iter = dw.edgeIter();
        while (iter.hasNext()){
            iter.next();
            sum++;
        }
        assertEquals(dw.edgeSize(), sum);
    }

    @Test
    void getMC() {
        assertEquals(graph.getMC(), 10);
    }
}