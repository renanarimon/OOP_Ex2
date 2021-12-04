package api;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DW_graph_algo implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph graph;

    public DW_graph_algo() {
        this.graph = new DW_graph();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        HashMap<Integer, NodeData> nodes = new HashMap<>();
        HashMap<Integer, HashMap<Integer, EdgeData>> children = new HashMap<>();
        HashMap<Integer, HashMap<Integer, EdgeData>> parents = new HashMap<>();
        int MC = graph.getMC();

        Iterator<NodeData> iterNodes = graph.nodeIter();
        while (iterNodes.hasNext()) {
            NodeData tmpNode = iterNodes.next();
            NodeData node = new Node(tmpNode.getLocation(), tmpNode.getKey());
            nodes.put(node.getKey(), node);
            HashMap<Integer, EdgeData> goTo = new HashMap<>();
            HashMap<Integer, EdgeData> comeFrom = new HashMap<>();
            children.put(node.getKey(), goTo);
            parents.put(node.getKey(), comeFrom);
        }

        Iterator<EdgeData> iterEdges = graph.edgeIter();

        while (iterEdges.hasNext()) {
            EdgeData tmp = iterEdges.next();
            EdgeData edge = new Edge(tmp.getSrc(), tmp.getDest(), tmp.getWeight());
            children.get(edge.getSrc()).put(edge.getDest(), edge);
            parents.get(edge.getDest()).put(edge.getSrc(), edge);
        }

        DirectedWeightedGraph g = new DW_graph(nodes, children, parents, MC);

        return g;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {

        return false;
    }


}
