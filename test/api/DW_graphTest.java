package api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/*
 *
 * @project Ex2
 * @auther Renana Rimon
 */class DW_graphTest {
    static DirectedWeightedGraph dw = new DW_graph();

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

    }

    @Test
    void getEdge() {
    }

    @Test
    void addNode() {
        DW_graph graph = (DW_graph) dw;
        assertEquals(graph.getNodes().get(0), dw.getNode(0));


    }

    @Test
    void connect() {


    }

    @Test
    void nodeIter() {
    }

    @Test
    void edgeIter() {
    }

    @Test
    void testEdgeIter() {
    }

    @Test
    void removeNode() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
        assertEquals(4, dw.nodeSize());
    }

    @Test
    void edgeSize() {
        assertEquals(dw.edgeSize(), 4);
    }

    @Test
    void getMC() {
    }
}