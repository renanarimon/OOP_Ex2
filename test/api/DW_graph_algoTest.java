package api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @project Ex2
 * @auther Renana Rimon
 */class DW_graph_algoTest {
    private static DW_graph_algo algo;

    @BeforeAll
    static void beforeAll() {
        algo = new DW_graph_algo();
        //algo.load("data/G1.json");
     // DirectedWeightedGraph graph= getGrapg("data/G1.json");
    }


    @Test
    void init() {
//     algo.init();
    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
    }

    @Test
    void isConnected() {
        algo.isConnected();
    }

    @Test
    void BFS() {
    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {
    }

    @Test
    void center() {

    }

    @Test
    void tsp() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}