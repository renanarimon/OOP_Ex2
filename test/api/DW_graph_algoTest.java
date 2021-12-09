package api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @project Ex2
 * @auther Renana Rimon
 */class DW_graph_algoTest {
    private static DW_graph_algo algo;
    private static DirectedWeightedGraph graph;

    @BeforeAll
    static void beforeAll() {
        algo = new DW_graph_algo();
        algo.load("data/10000Nodes.json");
        graph= algo.getGraph();


    }

    @Test
    void load() {
        algo.load("data/G1.json");
        graph=algo.getGraph();
        assertEquals(graph.getNode(0).getLocation().toString(),"35.19589,32.10153,0.0");
        assertEquals(graph.getNode(5).getLocation().toString(),"35.21211,32.10624,0.0");

    }
    @Test
    void init() {
      algo.init(graph);
      assertEquals(graph.edgeSize(),36);
      assertEquals(graph.nodeSize(),17);

    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
        algo.copy();
        assertEquals(graph.getNode(0).getLocation().toString(),"35.19589,32.10153,0.0");
        assertEquals(graph.getNode(5).getLocation().toString(),"35.21211,32.10624,0.0");
        assertEquals(graph.getNode(16).getLocation().toString(),"35.19381,32.10242,0.0");

    }

    @Test
    void isConnected() {
        assertTrue(algo.isConnected());
    }


    @Test
    void shortestPathDist() {
        assertEquals(algo.shortestPathDist(0,1), 1.232037506070033);


    }

    @Test
    void shortestPath() {
        NodeData a= graph.getNode(0);
        NodeData b= graph.getNode(1);
        List<NodeData> tmp= new LinkedList<>();
        tmp.add(a);
        tmp.add(b);
        assertEquals(algo.shortestPath(a.getKey(),b.getKey()),tmp);
        assertEquals(algo.shortestPathDist(2,15),6.321707132241677);

    }

    @Test
    void center() {
        NodeData n = algo.center();
        assertEquals(8,n.getKey());
    }

    @Test
    void tsp() {
        List<NodeData> cities= new LinkedList<>();
        NodeData a= graph.getNode(4);
        NodeData b= graph.getNode(6);
        NodeData c= graph.getNode(8);
        NodeData d= graph.getNode(9);
        NodeData e= graph.getNode(12);
        cities.add(a);
        cities.add(b);
        cities.add(c);
        cities.add(d);
        cities.add(e);
        List<NodeData> ans= new LinkedList<>();
        NodeData add1= graph.getNode(5);
        NodeData add2= graph.getNode(7);
        NodeData add3= graph.getNode(10);
        NodeData add4= graph.getNode(11);
        ans.add(a);
        ans.add(add1);
        ans.add(b);
        ans.add(add2);
        ans.add(c);
        ans.add(d);
        ans.add(add3);
        ans.add(add4);
        ans.add(e);
        algo.load("data/10000Nodes.json");
        algo.tsp(ans);

//        assertEquals(algo.tsp(cities),ans);
//        List<NodeData> list = Arrays.asList(graph.getNode(2),graph.getNode(1),graph.getNode(0),graph.getNode(16),graph.getNode(15));
//        assertEquals(algo.shortestPath(2,15),list);
    }

    @Test
    void save() {

    }


}